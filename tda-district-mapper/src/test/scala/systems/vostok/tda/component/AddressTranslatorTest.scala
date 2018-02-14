package systems.vostok.tda.component

import org.json4s.DefaultFormats
import org.json4s.jackson.Serialization.write
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.Matchers._
import org.scalatest.junit.JUnitRunner
import systems.vostok.tda.domain.Mapper
import systems.vostok.tda.exception.IllegalBuildingFormatException
import systems.vostok.tda.util.constants.BuildingType._

@RunWith(classOf[JUnitRunner])
class AddressTranslatorTest extends FunSuite {
  implicit val formats = DefaultFormats

  test("Address have proper format") {
    val buildingTypeParam = List(
      List("24", SIMPLE),
      List("24А", LITERAL),
      List("24/2", FRACTION)
    )

    buildingTypeParam.foreach { data =>
      AddressTranslator.getBuildingType(data(0).toString) should equal(data(1).asInstanceOf[BuildingType])
    }
  }

  test("IllegalBuildingFormatException should be thrown") {
    val illegalBuildingFormatExceptionAddress = List("test", "test24")

    illegalBuildingFormatExceptionAddress.foreach { address =>
      an[IllegalBuildingFormatException] should be thrownBy {
        AddressTranslator.getBuildingType(address)
      }
    }
  }

  /* Simple data mappers */
  val mapperDataSimple1 = List(
    new Mapper("test", "", "test"),
    new Mapper("test", "1-1/6", "test"),
    new Mapper("test", "1/6-24А", "test"),
    new Mapper("test", "24Б-50", "test")
  )

  val expectedMapperSimple1 = List(
    new Mapper("test", "", "test"),
    new Mapper("test", "1-1/6", "1", "1", "test"),
    new Mapper("test", "1/6-24А", "2", "24", "test"),
    new Mapper("test", "24Б-50", "25", "50", "test")
  )

  val mapperDataSimple2 = List(
    new Mapper("test", "1А-24А", "test"),
    new Mapper("test", "25-50Г", "test")
  )

  val expectedMapperSimple2 = List(
    new Mapper("test", "1А-24А", "2", "24", "test"),
    new Mapper("test", "25-50Г", "25", "50", "test")
  )

  /* Literal data mappers */
  val mapperDataLiteral1 = List(
    new Mapper("test", "", "test"),
    new Mapper("test", "1-1/6", "test"),
    new Mapper("test", "1/7-24А", "test"),
    new Mapper("test", "24Б-50", "test")
  )

  val expectedMapperLiteral1 = List(
    new Mapper("test", "", "test"),
    new Mapper("test", "1-1/6", "1", "1", "test"),
    new Mapper("test", "1/7-24А", "2", "24А", "test"),
    new Mapper("test", "24Б-50", "24Б", "50", "test")
  )

  val mapperDataLiteral2 = List(
    new Mapper("test", "1-2", "test"),
    new Mapper("test", "2/1-24", "test"),
    new Mapper("test", "25-50А", "test")
  )

  val expectedMapperLiteral2 = List(
    new Mapper("test", "1-2","1", "2", "test" ),
    new Mapper("test", "2/1-24", "3", "24", "test"),
    new Mapper("test", "25-50А", "25", "50А", "test")
  )

  /* Fraction data mappers */
  val mapperDataFraction1 = List(
    new Mapper("test", "1-1/6", "test"),
    new Mapper("test", "1/7-24А", "test"),
    new Mapper("test", "24Б-50", "test")
  )

  val expectedMapperFraction1 = List(
    new Mapper("test", "1-1/6", "1", "1/6", "test"),
    new Mapper("test", "1/7-24А", "1/7", "24", "test"),
    new Mapper("test", "24Б-50", "25", "50", "test")
  )

  val mapperDataFraction2 = List(
    new Mapper("test", "1-2", "test"),
    new Mapper("test", "2/1-24", "test"),
    new Mapper("test", "25-50/7", "test")
  )

  val expectedMapperFraction2 = List(
    new Mapper("test", "1-2", "1", "2", "test"),
    new Mapper("test", "2/1-24", "2/1", "24", "test"),
    new Mapper("test", "25-50/7", "25", "50/7", "test")
  )

  test("Mapper translator test") {
    val mapperTranslatorParam = List(
      List(mapperDataSimple1, SIMPLE, expectedMapperSimple1),
      List(mapperDataSimple2, SIMPLE, expectedMapperSimple2),

      List(mapperDataLiteral1, LITERAL, expectedMapperLiteral1),
      List(mapperDataLiteral2, LITERAL, expectedMapperLiteral2),

      List(mapperDataFraction1, FRACTION, expectedMapperFraction1),
      List(mapperDataFraction2, FRACTION, expectedMapperFraction2)
    )
    //TODO: refactor this
    mapperTranslatorParam.foreach { data =>
      listToJson(AddressTranslator.translateMapper(data(0).asInstanceOf[List[Mapper]], data(1).asInstanceOf[BuildingType]))
        .should(equal(listToJson(data(2).asInstanceOf[List[Mapper]])))
    }
  }

  def listToJson(list: Iterable[Mapper]): String = write(list)
}
