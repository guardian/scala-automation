package controllers

import play.api.libs.iteratee.{Concurrent, Enumerator, Iteratee}
import util.EnumUtils
import play.api.mvc._
import play.api.libs.json._
import play.api.libs.functional.syntax._
import service.DbService
import model.TestInfo
import model.TestResult.TestResult
import model.TestResult
import scala.concurrent.ExecutionContext.Implicits.global


object MessageController extends Controller {

    implicit val testResultReads: Reads[TestResult] = EnumUtils.enumReads(TestResult)
    val testInfoReads: Reads[TestInfo] = (
            (JsPath \ "testName").read[String] and
            (JsPath \ "testSet").read[String] and
            (JsPath \ "testDuration").read[String] and
            (JsPath \ "testResult").read[TestResult] and
            (JsPath \ "error").readNullable[String]
        )(TestInfo.apply _)

    def report(testname: String, testdate: String, setname: String, setdate: String) = WebSocket.using[JsValue] { request =>
//        Logger.info(s"wsEcho, client connected.")
//        var channel: Option[Concurrent.Channel[JsValue]] = None
//        val out: Enumerator[String] = Concurrent.unicast(c => channel = Some(c))
        val out = Enumerator(Json.parse("""{"message":"OK"}"""))
//        val out = Enumerator("OK")

        val in = Iteratee.foreach[JsValue](receivedString => {
            // send string back
            println(s"wsEcho, received: ${receivedString}")
//            channel.foreach(_.push(receivedString))
        })

        (in, out)
    }

//    def report = Action(parse.json) { request =>
//        val testInfoResult = request.body.validate[TestInfo](testInfoReads)
//        testInfoResult.fold(
//            errors => {
//                BadRequest(Json.obj("status" ->"KO", "message" -> JsError.toFlatJson(errors)))
//            },
//            testInfo => {
//                println("report received: " + testInfo)
//                DbService.insertTestInfo(testInfo)
//                Ok(Json.obj("status" -> "OK", "message" -> ("Report received.") ))
//            }
//        )
//    }

    //    def javascriptRoutes = Action { implicit request =>
    // this tracks back the javascript method call on server side.
    // E.g. index.js: jsRoutes.controllers.MessageController.getMessage().ajax({
    //        Ok(Routes.javascriptRouter("jsRoutes")(routes.javascript.MessageController.getMessage)).as(JAVASCRIPT)
    //    }

}
