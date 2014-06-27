package controllers

import play.api.mvc._
import service.DbService

object MainController extends Controller {
    
    def index = Action {
      Ok(views.html.index(DbService.getTestSets()))
    }
    
    def set(setName: String, setDate: String) = Action {
      Ok(views.html.set(DbService.getTests(setName, setDate)))
    }

    def test(testName: String, testDate: String, setName: String, setDate: String) = Action {
      Ok(views.html.test(DbService.getTestMessages(testName, testDate, setName, setDate)))
    }

}
