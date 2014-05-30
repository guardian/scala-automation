================================
  Scala Automation Core Module
================================

- Use this project as a base for your project
- All project source code have to be under src/test
Tests:    src/test/scala/com/gu/{project}/test
Steps:    src/test/scala/com/gu/{project}/step
PageObj:  src/test/scala/com/gu/{project}/page
Config:   src/test/resources/config.json
- Project dependencies should be declared in:
[project root]/project.sbt

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
