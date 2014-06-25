package model

import model.TestResult.TestResult

/**
 * Created by ipamer on 27/05/2014.
 */
case class TestInfo(testName: String, testSet: String, testDuration: String, testResult: TestResult, error: Option[String])

object TestResult extends Enumeration {
    type TestResult = Value
    val PASSED, FAILED = Value
}
