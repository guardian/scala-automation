package model

import model.TestResult.TestResult

import scala.collection.mutable

/**
 * Created by ipamer on 27/05/2014.
 */
case class TestInfo(testName: String, testDate: String, setName: String, setDate: String,
                    testResult: TestResult, error: Option[String],
                    messages: mutable.MutableList[String])

object TestResult extends Enumeration {
    type TestResult = Value
    val PASSED, FAILED = Value
}
