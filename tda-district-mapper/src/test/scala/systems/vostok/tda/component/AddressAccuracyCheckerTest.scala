package systems.vostok.tda.component

import org.scalatest.FunSuite
import org.scalatest.Matchers._
import systems.vostok.tda.domain.Address
import systems.vostok.tda.exception.{IllegalBuildingFormatException, IllegalEntityIdFormatException}

class AddressAccuracyCheckerTest extends FunSuite {

  test("Address have proper format") {
    val addressCheckerParam = List(
      new Address("svt", "3"),
      new Address("svt", "3А"),
      new Address("svt", "3/1")
    )

    addressCheckerParam.foreach { address =>
      AccuracyChecker.checkAddressConsistence(address) should equal(true)
    }
  }

  test("IllegalBuildingFormatException should be thrown") {
    val illegalBuildingFormatExceptionAddress = List(
      new Address("svt", "3АА"),
      new Address("svt", "3F"),
      new Address("svt", "3/01"),
      new Address("svt", "3/А"),
      new Address("svt", "3//1"),
      new Address("svt", "3/1А"),
      new Address("svt", "test")
    )

    illegalBuildingFormatExceptionAddress.foreach { address =>
      an[IllegalBuildingFormatException] should be thrownBy {
        AccuracyChecker.checkAddressConsistence(address)
      }
    }
  }

  test("IllegalEntityIdFormatException should be thrown") {
    val illegalEntityIdFormatExceptionAddress = List(
      new Address(null, "3"),
      new Address("svt", null),
      new Address("", "3"),
      new Address("svt v", "3"),
      new Address("svt", "")
    )

    illegalEntityIdFormatExceptionAddress.foreach { address =>
      an[IllegalEntityIdFormatException] should be thrownBy {
        AccuracyChecker.checkAddressConsistence(address)
      }
    }
  }
}
