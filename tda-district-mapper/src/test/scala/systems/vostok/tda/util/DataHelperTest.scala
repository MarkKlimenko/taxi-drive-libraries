package systems.vostok.tda.util

import org.scalatest.FunSuite
import org.scalatest.Matchers._

class DataHelperTest extends FunSuite {

  test("Extract first digit test") {
    val params = List(
      List("15", "15"),
      List("15/3", "15"),
      List("15/23", "15"),
      List("1543/23", "1543"),
      List("15А", "15")
    )

    params.foreach { param =>
      DataHelper.extractFirstDigits(param.head) should equal(param.last)
    }
  }

  test("Extract letter literal test") {
    val params = List(
      List("15", null),
      List("15А", "А"),
      List("15/23", null)
    )

    params.foreach { param =>
      DataHelper.extractLetterLiteral(param.head) should equal(param.last)
    }
  }

  test("Extract numeric literal test") {
    val params = List(
      List("15", null),
      List("15А", null),
      List("15/23", "23")
    )

    params.foreach { param =>
      DataHelper.extractNumericLiteral(param.head) should equal(param.last)
    }
  }
}