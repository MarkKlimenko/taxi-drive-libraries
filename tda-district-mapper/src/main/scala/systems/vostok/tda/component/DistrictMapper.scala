package systems.vostok.tda.component

import systems.vostok.tda.component.AddressTranslator._
import systems.vostok.tda.domain.Mapper
import systems.vostok.tda.exception.NoTargetDistrictException
import systems.vostok.tda.util.DataHelper._
import systems.vostok.tda.util.constants.BuildingType._

object DistrictMapper {

  def mapAddressToDistrict(adoptedMapperData: List[Mapper], building: String, buildingType: BuildingType): String = {
    if (adoptedMapperData.head.building == "") {
      adoptedMapperData.head.districtId
    } else {
      def mapperOption = adoptedMapperData.find(checkAccordance(_, building, buildingType))

      mapperOption match {
        case Some(mapper) => mapper.districtId
        case _ => throw new NoTargetDistrictException()
      }
    }
  }

  protected def checkAccordance(mapper: Mapper, building: String, buildingType: BuildingType): Boolean = {
    buildingType match {
      case SIMPLE => checkSimpleAccordance(mapper, building)
      case LITERAL => checkLiteralAccordance(mapper, building)
      case FRACTION => checkFractionAccordance(mapper, building)
    }
  }

  protected def checkSimpleAccordance(mapper: Mapper, building: String): Boolean = {
    building.toInt <= mapper.buildingTo.toInt && building.toInt >= mapper.buildingFrom.toInt
  }

  protected def checkLiteralAccordance(mapper: Mapper, building: String): Boolean = {
    val litFreeBuilding = extractFirstDigits(building)
    val litFreeBuildingFrom = extractFirstDigits(mapper.buildingFrom)
    val litFreeBuildingTo = extractFirstDigits(mapper.buildingTo)

    if (litFreeBuilding != litFreeBuildingFrom && litFreeBuilding != litFreeBuildingTo) {
      checkSimpleAccordance(translateSingleMapperToSimple(mapper), litFreeBuilding)

    } else if (litFreeBuilding == litFreeBuildingFrom && litFreeBuilding != litFreeBuildingTo) {

      compareLetters(extractLetterLiteral(mapper.buildingFrom), "Я", extractLetterLiteral(building))

    } else if (litFreeBuilding != litFreeBuildingFrom && litFreeBuilding == litFreeBuildingTo) {
      compareLetters("А", extractLetterLiteral(mapper.buildingTo), extractLetterLiteral(building))

    } else if (litFreeBuilding == litFreeBuildingFrom && litFreeBuilding == litFreeBuildingTo) {
      compareLetters(extractLetterLiteral(mapper.buildingFrom), extractLetterLiteral(mapper.buildingTo), extractLetterLiteral(building))

    } else {
      false
    }
  }

  protected def compareLetters(letFrom: String, letTo: String, letBuilding: String): Boolean = {
    var reassignedLetFrom = letFrom

    if (letFrom == null) {
      reassignedLetFrom = "А"
    }
    if (letTo == null) {
      return false
    }

    (reassignedLetFrom.head to letTo.head).contains(letBuilding)
  }

  protected def checkFractionAccordance(mapper: Mapper, building: String): Boolean = {
    val litFreeBuilding = extractFirstDigits(building)
    val litFreeBuildingFrom = extractFirstDigits(mapper.buildingFrom)
    val litFreeBuildingTo = extractFirstDigits(mapper.buildingTo)

    if (litFreeBuilding != litFreeBuildingFrom && litFreeBuilding != litFreeBuildingTo) {
      checkSimpleAccordance(translateSingleMapperToSimple(mapper), litFreeBuilding)

    } else if (litFreeBuilding == litFreeBuildingFrom && litFreeBuilding != litFreeBuildingTo) {
      compareNumbers(extractNumericLiteral(mapper.buildingFrom), "9999", extractNumericLiteral(building))

    } else if (litFreeBuilding != litFreeBuildingFrom && litFreeBuilding == litFreeBuildingTo) {
      compareNumbers("0", extractNumericLiteral(mapper.buildingTo), extractNumericLiteral(building))

    } else if (litFreeBuilding == litFreeBuildingFrom && litFreeBuilding == litFreeBuildingTo) {
      compareNumbers(extractNumericLiteral(mapper.buildingFrom), extractNumericLiteral(mapper.buildingTo), extractNumericLiteral(building))

    } else {
      false
    }
  }

  protected def compareNumbers(numFrom: String, numTo: String, numBuilding: String): Boolean = {
    var reassignedNumFrom = numFrom

    if (numFrom == null) {
      reassignedNumFrom = "0"
    }
    if (numTo == null) {
      return false
    }

    (numFrom.toInt to numTo.toInt).contains(numBuilding.toInt)
  }
}
