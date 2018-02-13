package systems.vostok.tda.component

import org.scalatest.FunSuite
import org.scalatest.Matchers._
import systems.vostok.tda.domain.{Address, Mapper}
import systems.vostok.tda.exception.{IllegalBuildingFormatException, IllegalEntityIdFormatException, NoMapperDataException, NotCompatibleMapperException}

class MapperAccuracyCheckerTest extends FunSuite {

  val mapperData = List(
    new Mapper("test", "", "test"),
    new Mapper("test", "1-1/6", "test"),
    new Mapper("test", "1/6-24А", "test"),
    new Mapper("test", "24Б-50", "test")
  )

  test("Mapper have proper format") {
    AccuracyChecker.checkMapperConsistence(mapperData) should equal(true)
  }

  test("Mapper NoMapperDataException exception") {
    val mapperNoMapperData = List()

    an[NoMapperDataException] should be thrownBy {
      AccuracyChecker.checkMapperConsistence(mapperNoMapperData)
    }
  }

  test("Mapper IllegalBuildingFormatException exception") {
    val mapperIllegalBuilding = List(new Mapper("test", " ", "test"))

    an[IllegalBuildingFormatException] should be thrownBy {
      AccuracyChecker.checkMapperConsistence(mapperIllegalBuilding)
    }
  }

  test("Mapper IllegalEntityIdFormatException exception") {
    val mapperIllegalEntityIdFormat = List(
      List(new Mapper(" ", "1-1/6", "test")),
      List(new Mapper("test", "1-1/6", " "))
    )

    mapperIllegalEntityIdFormat.foreach { mapper =>
      an[IllegalEntityIdFormatException] should be thrownBy {
        AccuracyChecker.checkMapperConsistence(mapper)
      }
    }
  }

  test("Mapper compatibility test") {
    val properAddress = Address("test", "3")

    AccuracyChecker.checkAddressMapperCompatibility(properAddress, mapperData) should equal(true)
  }

  test("Mapper NotCompatibleMapperException exception") {
    val wrongAddress = Address("test_another_id", "3")

    an[NotCompatibleMapperException] should be thrownBy {
      AccuracyChecker.checkAddressMapperCompatibility(wrongAddress, mapperData)
    }
  }
}
