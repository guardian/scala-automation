================================
  Scala Automation Core Module
================================

- Use this project as a base for your project
- Create a file named "project.sbt" in your project root with the following content (keep new lines, replace name):
name := "My project tests"

version := "1.0"

- Define your dependencies in project.sbt (like in build.sbt)
- All project source code have to be under src/test
Tests:    src/test/scala/com/gu/{project}/test
Steps:    src/test/scala/com/gu/{project}/step
PageObj:  src/test/scala/com/gu/{project}/page
- Test configurations:
A default configuration is suppiled in: src/main/resources.conf
Define your testing configurations in: src/test/resources/applications.conf
Everything you define there overrides the defaults.
In addition, all environment configs are applied over the default configuration.
System properties override everything.
  An example application.conf:
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
================================

EXAMPLES:

- TEST:

class ExampleTests extends BaseTest with ExampleSteps {

  info("Tests for the Example project")

  feature("My example feature") {

    scenarioWeb("My first test") {
      givenIAmLoggedIn
      whenIGoToTheEventsPage
      thenISeeAListOfEvents
    }

  }
}

- STEP:

trait ExampleSteps extends BaseSteps {

  def givenIAmLoggedIn() {
    given(logger, "I am logged in")
    driver.get("http://www.theguardian.com/")
    new LoginPage(driver).login("it_is_me", "let_me_in")
  }

  def whenIGoToTheEventsPage() { }

  def thenISeeAListOfEvents() { }
}

- PAGE:

class LoginPage(driver: WebDriver) extends BasePage(driver) {

  private def userTextbox = driver.findElement(By.id("user"))
  private def passwordTextbox = driver.findElement(By.id("password"))
  private def submitButton = driver.findElement(By.cssSelector(".form-field>button"))

  def login(user: String, password: String): LoginPage = {
    userTextbox.sendKeys(user)
    passwordTextbox.sendKeys(password)
    submitButton.click()
    this
  }
}
