# The changelog is output by the build into the docs directory.  This file is run after every build to update it.

cp changelogHeader.html local.changelog.html

log=$(git log --grep='Setting version to [0-9.]*$' --grep='Merge pull request' --format='%cD~%aN~%s~%b')

versionHeader=$(sed 's/^[^~]*~Jenkins Slave~Setting version to \([^~]*\).*$/\
\
<h2>Change Log for version: \1<\/h2>/' <<END
$log
END
)

pullRequests=$(sed 's=^\([^~]*\)~\([^~]*\)~Merge pull request \#\([0-9]*\) from[^~]*~\(.*\)$=<a href\="https://github.com/guardian/scala-automation/pull/\3">\4</a><br/><span class\='credit'>\1 by \2</span>=' <<END
$versionHeader
END
)

sed 's/$/<br\/>/' >>local.changelog.html <<END
$pullRequests
END

cat changelogFooter.html >>local.changelog.html
