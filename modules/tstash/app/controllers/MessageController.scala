package controllers

import model.{TestInfo, TestResult}
import play.Logger
import play.api.libs.iteratee.{Enumerator, Iteratee}
import play.api.libs.json._
import play.api.mvc._
import service.DbService

import scala.collection.mutable
import scala.concurrent.ExecutionContext.Implicits.global


object MessageController extends Controller {

  def report(testName: String, testDate: String, setName: String, setDate: String) = WebSocket.using[JsValue] { request =>
    val out = Enumerator(Json.parse( """{"message":"OK"}"""))

    val in = Iteratee.foreach[JsValue](json => {
      Logger.info(s"received: ($testName, $testDate, $setName, $setDate) ${json}")
      val message = (json \ "message").toString()
      //val timeStamp = (json \ "timeStamp").toString()
      DbService.insertTestInfo(TestInfo(testName, testDate, setName, setDate, TestResult.PASSED, None, new mutable.MutableList[String]().+=:(message)))
    })

    (in, out)
  }

  //  implicit val testResultReads: Reads[TestResult] = EnumUtils.enumReads(TestResult)
//  val testInfoReads: Reads[TestInfo] = (
//    (JsPath \ "testName").read[String] and
//      (JsPath \ "testSet").read[String] and
//      (JsPath \ "testDuration").read[String] and
//      (JsPath \ "testResult").read[TestResult] and
//      (JsPath \ "error").readNullable[String]
//    )(TestInfo.apply _)

  //    def javascriptRoutes = Action { implicit request =>
  // this tracks back the javascript method call on server side.
  // E.g. index.js: jsRoutes.controllers.MessageController.getMessage().ajax({
  //        Ok(Routes.javascriptRouter("jsRoutes")(routes.javascript.MessageController.getMessage)).as(JAVASCRIPT)
  //    }

}
