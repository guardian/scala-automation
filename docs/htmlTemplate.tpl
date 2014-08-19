<!doctype html>
<html class="no-js" lang="en">
<head>
    <meta charset="utf-8">
    <title>ChangeLog - {{title}}</title>
    <link rel="stylesheet" type="text/css" href="html5reset-1.6.1.css"/>
    <link rel="stylesheet" type="text/css" href="style.css"/>
</head>
<div id="container">

    <h1>ChangeLog</h1>
    <p>Here is a list of changes for the project.  The latest version is the first one in the list.  Always try to keep up to date.</p>
    {{#title_chars}}={{/title_chars}}
    {{#versions}}
    {{#sections}}

    <div id="commits">

        <h2>Commits</h2>
        {{#commits}}
        <p class="commit">{{subject}}
        <span class="author">[{{author}} - {{dateTime}}]</span></p>
    </div>
</div>

{{#body}}
{{body_indented}}
{{/body}}

{{/commits}}
{{/sections}}
{{/versions}}
<footer>
    <p>&copy;2014 Guardian News and Media Limited or its affiliated companies. All rights reserved.</p>
</footer>
</html>

