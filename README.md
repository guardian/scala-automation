================================
  Scala Automation Core Module
================================

## Creating a new Test project
- Create a new folder for your project so you can add it to github
- Create a file named "project.sbt" in your project root with the following content (keep new lines, replace name):
```
name := "My project tests"

version := "1.0"

libraryDependencies ++= Seq(
  "com.gu" %% "web-automation-core-scala" % "x.x.SEE.NOTE"
)
```
NOTE: You can find the latest version by looking [here](http://repo1.maven.org/maven2/com/gu/web-automation-core-scala_2.10/)
- All project source code have to be under src/test
Tests:    src/test/scala/com/gu/{project}/test
Steps:    src/test/scala/com/gu/{project}/step
PageObj:  src/test/scala/com/gu/{project}/page
You can see examples of each in this repo under (src/test/scala/com/gu/example/)
- Test configurations:
The default configuration is supplied in: src/main/framework.conf
If you need to override anything for your tests or add new defaults, add: src/test/resources/project.conf
If you need some personal settings add local.conf.  This will be ignored by git so ideal for storing usernames and passwords.
System properties override everything.
## Examples
### Simple
```
"testBaseUrl"           : "http://m.code.dev-theguardian.com/uk"
"browser"               : "chrome" // firefox, chrome, ie
loginEmail : "test.user@guardian.co.uk"
loginPassword : "asdf"
idApiRoot: "https://idapi.code.dev-theguardian.com"
```
### Complex
"environment": "local"
"local": {
    "browser"               : "firefox"
    "testBaseUrl"           : "http://localhost:8080"
}
"integration": {
    "browser"               : "chrome"
    "webDriverRemoteUrl"    : "http://remotehub:4444/wd/hub"
    "testBaseUrl"           : "http://www.theguardian.com"
}
```