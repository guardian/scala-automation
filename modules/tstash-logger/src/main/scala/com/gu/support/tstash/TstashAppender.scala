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
    val url = sys.props.getOrElseUpdate("teststash.url", "")
    if (url == "" ||
      eventObject.getMDCPropertyMap.get("testName") == null ||
      eventObject.getMDCPropertyMap.get("testDate") == null ||
      eventObject.getMDCPropertyMap.get("setName") == null ||
      eventObject.getMDCPropertyMap.get("setDate") == null) {
      return None
    }
    val websocket: WebSocket = asyncHttpClient.prepareGet(url)
      .addQueryParameter("testName", eventObject.getMDCPropertyMap.get("testName"))
      .addQueryParameter("testDate", eventObject.getMDCPropertyMap.get("testDate"))
      .addQueryParameter("setName", eventObject.getMDCPropertyMap.get("setName"))
      .addQueryParameter("setDate", eventObject.getMDCPropertyMap.get("setDate"))
      .execute(new WebSocketUpgradeHandler.Builder().addWebSocketListener(
      new WebSocketTextListener() {
        override def onMessage(message: String): Unit = {} //println("[T-Stash ws message] " + message) }
        override def onFragment(fragment: String, last: Boolean): Unit = {} //println("[T-Stash ws fragment] " + fragment) }
        override def onError(t: Throwable): Unit = {} //println("[T-Stash ws error] " + t.getMessage) }
        override def onClose(websocket: WebSocket): Unit = {}
        override def onOpen(websocket: WebSocket): Unit = {}
      }).build()).get()

    if (websocket == null) {
      println("Failed to create connection to Test-Stash for test: " + eventObject.getMDCPropertyMap.get("testName"))
      return None
    } else {
      return Some(websocket)
    }
  }

}
