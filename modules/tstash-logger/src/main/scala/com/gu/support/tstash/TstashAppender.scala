package com.gu.automation.api

import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.UnsynchronizedAppenderBase
import com.ning.http.client.AsyncHttpClient
import com.ning.http.client.websocket.{WebSocket, WebSocketTextListener, WebSocketUpgradeHandler}

import scala.collection.mutable

object TstashAppender {
  val sockets = mutable.Map[String, WebSocket]()
}

class TstashAppender extends UnsynchronizedAppenderBase[ILoggingEvent] {

  override def append(eventObject: ILoggingEvent): Unit = {
    if("[TEST START]" == eventObject.getMessage) {
      createWebSocket(eventObject).map(TstashAppender.sockets.put(eventObject.getMDCPropertyMap.get("ID"), _))
    } else if("[TEST END]" == eventObject.getMessage) {
      TstashAppender.sockets.get(eventObject.getMDCPropertyMap.get("ID")).map(_.close())
    } else {
      TstashAppender.sockets.get(eventObject.getMDCPropertyMap.get("ID")).map(_.sendTextMessage(
        s"""{
           |"message":"${eventObject.getFormattedMessage}",
           |"timeStamp":"${eventObject.getTimeStamp}"
           |}""".stripMargin))
    }
  }

  private def createWebSocket(eventObject: ILoggingEvent): Option[WebSocket] = {
    val asyncHttpClient = new AsyncHttpClient()
    val websocket: WebSocket = asyncHttpClient.prepareGet("ws://10.252.93.148:8081/report")
//    val websocket: WebSocket = asyncHttpClient.prepareGet("ws://localhost:9000/report")
      .addQueryParameter("testname", eventObject.getMDCPropertyMap.get("testname"))
      .addQueryParameter("testdate", eventObject.getMDCPropertyMap.get("testdate"))
      .addQueryParameter("setname", eventObject.getMDCPropertyMap.get("setname"))
      .addQueryParameter("setdate", eventObject.getMDCPropertyMap.get("setdate"))
      .execute(new WebSocketUpgradeHandler.Builder().addWebSocketListener(
      new WebSocketTextListener() {
        override def onMessage(message: String): Unit = { println(message) }
        override def onFragment(fragment: String, last: Boolean): Unit = { println(fragment) }
        override def onError(t: Throwable): Unit = { println(t.getMessage) }
        override def onClose(websocket: WebSocket): Unit = {}
        override def onOpen(websocket: WebSocket): Unit = {}
      }).build()).get()

    if (websocket == null) {
      println("Failed to create connection to Test-Stash for test: " + eventObject.getMDCPropertyMap.get("testname"))
      return None
    } else {
      return Some(websocket)
    }
  }

}
