package systems.vostok.tda.component

import org.scalatest.FunSuite
import org.scalatest.Matchers._
import systems.vostok.tda.domain.Address
import systems.vostok.tda.exception.{IllegalBuildingFormatException, IllegalEntityIdFormatException}

class AddressAccuracyCheckerTest extends FunSuite {

  test("Address have proper format") {
    val addressCheckerParam = List(
      Address("svt", "3"),
      Address("svt", "3А"),
      Address("svt", "3/1")
    )

    addressCheckerParam.foreach { address =>
      AccuracyChecker.checkAddressConsistence(address) should equal(true)
    }
  }

  test("IllegalBuildingFormatException should be thrown") {
    val illegalBuildingFormatExceptionAddress = List(
      Address("svt", "3АА"),
      Address("svt", "3F"),
      Address("svt", "3/01"),
      Address("svt", "3/А"),
      Address("svt", "3//1"),
      Address("svt", "3/1А"),
      Address("svt", "test")
    )

    illegalBuildingFormatExceptionAddress.foreach { address =>
      an[IllegalBuildingFormatException] should be thrownBy {
        AccuracyChecker.checkAddressConsistence(address)
      }
    }
  }

  test("IllegalEntityIdFormatException should be thrown") {
    val illegalEntityIdFormatExceptionAddress = List(
      Address(null, "3"),
      Address("svt", null),
      Address("", "3"),
      Address("svt v", "3"),
      Address("svt", "")
    )

    illegalEntityIdFormatExceptionAddress.foreach { address =>
      an[IllegalEntityIdFormatException] should be thrownBy {
        AccuracyChecker.checkAddressConsistence(address)
      }
    }
  }
}
