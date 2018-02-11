package systems.vostok.tda.component

import org.scalatest.FunSuite
import org.scalatest.Matchers._
import systems.vostok.tda.exception.{IllegalBuildingFormatException, IllegalEntityIdFormatException, NotCompatibleMapperException}

class MapperAccuracyCheckerTest extends FunSuite {

  val mapperData = List(
    Map("streetId" -> "test", "building" -> "", "districtId" -> "test"),
    Map("streetId" -> "test", "building" -> "1-1/6", "districtId" -> "test"),
    Map("streetId" -> "test", "building" -> "1/6-24А", "districtId" -> "test"),
    Map("streetId" -> "test", "building" -> "24Б-50", "districtId" -> "test")
  )

  test("Mapper have proper format") {
    AccuracyChecker.checkMapperConsistence(mapperData) should equal(true)
  }

  test("Mapper NoMapperDataException exception") {
    val mapperNoMapperData = List()

    an[IllegalBuildingFormatException] should be thrownBy {
      AccuracyChecker.checkMapperConsistence(mapperNoMapperData)
    }
  }

  test("Mapper IllegalBuildingFormatException exception") {
    val mapperIllegalBuilding = List(Map("streetId" -> "test", "building" -> " ", "districtId" -> "test"))

    an[IllegalBuildingFormatException] should be thrownBy {
      AccuracyChecker.checkMapperConsistence(mapperIllegalBuilding)
    }
  }

  test("Mapper IllegalEntityIdFormatException exception") {
    val mapperIllegalEntityIdFormat = List(
      List(Map("streetId" -> " ", "building" -> "1-1/6", "districtId" -> "test")),
      List(Map("streetId" -> "test", "building" -> "1-1/6", "districtId" -> " "))
    )

    mapperIllegalEntityIdFormat.foreach { mapper =>
      an[IllegalEntityIdFormatException] should be thrownBy {
        AccuracyChecker.checkMapperConsistence(mapper)
      }
    }
  }

  test("Mapper compatibility test") {
    val properAddress = Map("streetId" -> "test", "building" -> "3")

    AccuracyChecker.checkAddressMapperCompatibility(properAddress, mapperData) should equal(true)
  }

  test("Mapper NotCompatibleMapperException exception") {
    val wrongAddress = Map("streetId" -> "test_another_id", "building" -> "3")

    an[NotCompatibleMapperException] should be thrownBy {
      AccuracyChecker.checkAddressMapperCompatibility(wrongAddress, mapperData)
    }
  }
}
