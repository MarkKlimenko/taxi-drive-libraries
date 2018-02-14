package systems.vostok.tda.service

import org.scalatest.FunSuite
import org.scalatest.Matchers._
import systems.vostok.tda.domain.{Address, Mapper}

class DistrictMapperServiceTest extends FunSuite {

  val mapperEmpty = List(
    new Mapper("svt", "", "cnt")
  )

  val mapperSimple = List(
    new Mapper("svt", "1-24", "cnt"),
    new Mapper("svt", "25-50", "dal"),
    new Mapper("svt", "51-106", "lug")
  )

  val mapperLiteral = List(
    new Mapper("svt", "1-24Б", "cnt"),
    new Mapper("svt", "24В-24Д", "dal"),
    new Mapper("svt", "24Е-106", "lug"),
    new Mapper("svt", "106А-109", "spr")
  )

  val mapperDash = List(
    new Mapper("svt", "1-24В", "cnt"),
    new Mapper("svt", "24Г-24/3", "dal"),
    new Mapper("svt", "24/4-106", "lug"),
    new Mapper("svt", "107-107/2", "spor"),
    new Mapper("svt", "107/3-107/9", "rog"),
    new Mapper("svt", "108-112/6", "tih"),
  )

  val mapperMix = List(
    new Mapper("svt", "1-23", "cnt"),
    new Mapper("svt", "24-24В", "dal"),
    new Mapper("svt", "24Г-24Е", "lug"),
    new Mapper("svt", "24З-25", "spor"),
    new Mapper("svt", "25А-107/9", "rog"),
    new Mapper("svt", "108-112/6", "tih"),
  )

  val testData = List(
    List(new Address("svt", "24"), mapperEmpty, "cnt"),

    List(new Address("svt", "25"), mapperSimple, "dal"),
    List(new Address("svt", "3"), mapperSimple, "cnt"),
    List(new Address("svt", "3"), mapperLiteral, "cnt"),
    List(new Address("svt", "24"), mapperLiteral, "cnt"),
    List(new Address("svt", "50"), mapperLiteral, "lug"),
    List(new Address("svt", "3"), mapperDash, "cnt"),
    List(new Address("svt", "24"), mapperDash, "cnt"),
    List(new Address("svt", "50"), mapperDash, "lug"),

    List(new Address("svt", "1Е"), mapperLiteral, "cnt"),
    List(new Address("svt", "24Г"), mapperLiteral, "dal"),
    List(new Address("svt", "24Е"), mapperLiteral, "lug"),
    List(new Address("svt", "24З"), mapperLiteral, "lug"),
    List(new Address("svt", "28Е"), mapperLiteral, "lug"),
    List(new Address("svt", "106Б"), mapperLiteral, "spr"),

    List(new Address("svt", "1/1"), mapperDash, "cnt"),
    List(new Address("svt", "107/1"), mapperDash, "spor"),
    List(new Address("svt", "107/3"), mapperDash, "rog"),
    List(new Address("svt", "112/6"), mapperDash, "tih"),
    List(new Address("svt", "108/6"), mapperDash, "tih"),

    List(new Address("svt", "24А"), mapperMix, "dal"),
    List(new Address("svt", "24Г"), mapperMix, "lug"),
    List(new Address("svt", "25А"), mapperMix, "rog")
  )

  test("IllegalBuildingFormatException should be thrown") {
    testData.foreach { data =>
      new DistrictMapperService().getDistrict(data(1).asInstanceOf[List[Mapper]], data(0).asInstanceOf[Address]) should equal(data(2).toString)
    }
  }
}