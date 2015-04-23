#  Scala Automation Core Module

## Links
- [![Build Status](https://travis-ci.org/guardian/scala-automation.svg?branch=master)](https://travis-ci.org/guardian/scala-automation) on branch master
- [Version History](http://scala-automation.s3-website-eu-west-1.amazonaws.com/scala-automation-changelog.html)
- [Project wiki - background information](https://sites.google.com/a/guardian.co.uk/qa/scala-automation)

## Creating a new Test project
- Create a new folder for your project so you can add it to github
- Create a file named "build.sbt" in your project root with the following content (keep new lines, replace name):
```
name := "My project tests"

version := "1.0"

libraryDependencies ++= Seq(
  "com.gu" %% "web-automation-core-scala" % "x.x.SEE.NOTE"
)
```
NOTE: You can find the latest version by looking [here](http://repo1.maven.org/maven2/com/gu/scala-automation_2.10/)

* All project source code have to be under src/test
* Tests:    src/test/scala/com/gu/{project}/test
* Steps:    src/test/scala/com/gu/{project}/step
* PageObj:  src/test/scala/com/gu/{project}/page

You can see examples of each in this repo under [src/examples/scala/com/gu/example/](src/examples/scala/com/gu/example/)

## Features

### Test configurations:
The default configuration is supplied, by the framework, in: ```src/main/framework.conf```
If you need to override anything for your tests or add new defaults, add: ```src/test/resources/project.conf```, in your test project.
If you need some personal settings add ```local.conf``` at the root of your test project.  This should be ignored by Git, so make sure to add it in the suitable .gitignore, and is ideal for storing sensitive data such as usernames and passwords.
Finally properties defined as a System property overrides all the above property declarations.

Would you want to put your local.conf in a location which you define yourself, you should provide a System property ```local.conf.loc``` with the value for the path of your local.conf. It is important that this is done before the framework is initialized so one way would be to add it as a jvm arg when running the test.

### Logging:
In your resources folder create a file named: logback.xml
Put this content in the file (and edit it for your project needs):
```
<configuration>
    <property name="application-name" value="automation-core" />

    <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
        <layout class="ch.qos.logback.classic.PatternLayout">
            <Pattern>%d [%thread] %level %logger - %m%n</Pattern>
        </layout>
    </appender>
    <appender name="FILE" class="ch.qos.logback.core.FileAppender">
        <File>logs/${application-name}.log</File>
        <layout class="ch.qos.logback.classic.PatternLayout">
            <Pattern>%d %p %t %c - %m%n</Pattern>
        </layout>
    </appender>

    <root level="info">
        <appender-ref ref="CONSOLE"/>
        <appender-ref ref="FILE"/>
    </root>
</configuration>
```

### Before and After methods
If you need to execute arbitrary code before or after your test suite or test method,
add the following trait(s) to your class:
- BeforeAndAfter: this will give you the methods before() and after() to override and they will be executed before and after every test execution.
- BeforeAndAfterAll: this will give you the methods beforeAll() and afterAll() to override and they will be executed before and after your test suite.

## Examples

### Test Environment Configuration
- for a guaranteed up to date list read [the Config reader class](src/main/scala/com/gu/automation/support/Config.scala)
- Simple:
```
"testBaseUrl"           : "http://m.code.dev-theguardian.com/uk"
browserEnvironment: "local" // or sauceLabs or browserStack
"browsers"               : [{name: "chrome"}] // firefox, chrome, ie and you can also add version in the {} braces
loginEmail : "test.user@guardian.co.uk"
loginPassword : "asdf"
idApiRoot: "https://idapi.code.dev-theguardian.com"
```
For more information about the loginEmail, loginPassword and idApiRoot please see [Scala Automation Web SignIn](https://github.com/guardian/scala-automation-web-signin)

- With Environments:
```
"environment": "local"
"local": {
    "browsers"               : [{name:"firefox"}]
    "testBaseUrl"           : "http://localhost:8080"
}
"integration": {
    "browsers"               : [{name:"chrome"}]
    "webDriverRemoteUrl"    : "http://remotehub:4444/wd/hub"
    "testBaseUrl"           : "http://www.theguardian.com"
}
```
What the properties, under the different environment objects do, is to override the properties defined in the root. So for example, in the above case, the browsers in the root will be overridden with firefox.

## Headless web testing with PhantomJS

It is possible to run headless web tests using PhantomJS. In order to do this, you will need to have the phantomjs binary installed in your $PATH.
Once this is done, you just need to add
```
"browsers" : [{"name": "phantomjs"}]
```
to your configuration file.

