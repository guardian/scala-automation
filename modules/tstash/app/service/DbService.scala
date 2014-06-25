package service

import model.TestInfo
import scala.collection.mutable

/**
 * Created by ipamer on 27/05/2014.
 */
object DbService {

    val testDb = mutable.Map[String, List[TestInfo]]()

    def insertTestInfo(testInfo: TestInfo) {
        val list = testInfo :: testDb.get(testInfo.testSet).getOrElse(Nil)
        if (list.length > 50) {
            testDb += (testInfo.testSet -> list.take(list.length - 1))
        } else {
            testDb += (testInfo.testSet -> list)
        }
    }

}
