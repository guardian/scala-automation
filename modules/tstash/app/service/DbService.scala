package service

import model.TestInfo
import org.joda.time
import org.joda.time.DateTime

import scala.collection.mutable

/**
 * Created by ipamer on 27/05/2014.
 */
object DbService {

  private val testDb = new mutable.HashMap[(String, String, String, String), TestInfo]()

  def insertTestInfo(testInfo: TestInfo) {
    val ti = testDb.get(testInfo.testName, testInfo.testDate, testInfo.setName, testInfo.setDate)
    if (!ti.isEmpty) {
      ti.get.messages ++= testInfo.messages
    } else {
      testDb.put((testInfo.testName, testInfo.testDate, testInfo.setName, testInfo.setDate), testInfo)
    }
  }

  private def removeOlds() = {
    for (key <- testDb.keySet) {
      if (key._4.toLong < DateTime.now.minusDays(1).getMillis) {
        testDb.remove(key)
      }
    }
  }

  def getTestSets(): mutable.HashMap[String, mutable.HashMap[String, String]] = {
    removeOlds()

    val result = new mutable.HashMap[String, mutable.HashMap[String, String]]()
    for (((tn, td, sn, sd), testInfo) <- testDb) {
      if (result.get(sn).isEmpty) {
        result.put(sn, new mutable.HashMap[String, String]())
      }
      result.get(sn).map(_.put(sd, "PASSED"))
    }
    result
  }

  def getTests(setName: String, setDate: String): mutable.MutableList[TestInfo] = {
    val result = new mutable.MutableList[TestInfo]()
    for (((tn, td, sn, sd), testInfo) <- testDb) {
      if (sn == setName && sd == setDate) {
        result.+=:(testInfo)
      }
    }
    result
  }

  def getTestMessages(testName: String, testDate: String, setName: String, setDate: String): TestInfo = {
    testDb.get((testName, testDate, setName, setDate)).get
  }

}
