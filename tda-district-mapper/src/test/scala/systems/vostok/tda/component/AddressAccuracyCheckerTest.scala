package systems.vostok.tda.component

import org.scalatest.FunSuite
import org.scalatest.Matchers._
import systems.vostok.tda.exception.{IllegalBuildingFormatException, IllegalEntityIdFormatException}

class AddressAccuracyCheckerTest extends FunSuite {

  test("Address have proper format") {
    val addressCheckerParam = List(
      Map("streetId" -> "svt", "building" -> "3"),
      Map("streetId" -> "svt", "building" -> "3А"),
      Map("streetId" -> "svt", "building" -> "3/1")
    )

    addressCheckerParam.foreach { address =>
      print(address("streetId"))
      AccuracyChecker.checkAddressConsistence(address) should equal(true)
    }
  }

  test("IllegalBuildingFormatException should be thrown") {
    val illegalBuildingFormatExceptionAddress = List(
      Map("building" -> "3"),
      Map("streetId" -> "svt"),
      Map("streetId" -> "", "building" -> "3"),
      Map("streetId" -> "svt v", "building" -> "3"),
      Map("streetId" -> "svt", "building" -> ""),
      Map("streetId" -> "svt", "building" -> "3АА"),
      Map("streetId" -> "svt", "building" -> "3F"),
      Map("streetId" -> "svt", "building" -> "3/01"),
      Map("streetId" -> "svt", "building" -> "3/А"),
      Map("streetId" -> "svt", "building" -> "3//1"),
      Map("streetId" -> "svt", "building" -> "3/1А"),
      Map("streetId" -> "svt", "building" -> "test")
    )

    illegalBuildingFormatExceptionAddress.foreach { address =>
      an[IllegalBuildingFormatException] should be thrownBy {
        AccuracyChecker.checkAddressConsistence(address)
      }
    }
  }

  test("IllegalEntityIdFormatException should be thrown") {
    val illegalEntityIdFormatExceptionAddress = List(
      Map("building" -> "3"),
      Map("streetId" -> "svt"),
      Map("streetId" -> "", "building" -> "3"),
      Map("streetId" -> "svt v", "building" -> "3"),
      Map("streetId" -> "svt", "building" -> ""),
      Map("streetId" -> "svt", "building" -> "3АА"),
      Map("streetId" -> "svt", "building" -> "3F"),
      Map("streetId" -> "svt", "building" -> "3/01"),
      Map("streetId" -> "svt", "building" -> "3/А"),
      Map("streetId" -> "svt", "building" -> "3//1"),
      Map("streetId" -> "svt", "building" -> "3/1А"),
      Map("streetId" -> "svt", "building" -> "test")
    )

    illegalEntityIdFormatExceptionAddress.foreach { address =>
      an[IllegalEntityIdFormatException] should be thrownBy {
        AccuracyChecker.checkAddressConsistence(address)
      }
    }
  }
}
