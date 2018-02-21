package systems.vostok.tda.component

import systems.vostok.tda.domain.Mapper
import systems.vostok.tda.exception.IllegalBuildingFormatException
import systems.vostok.tda.util.DataHelper._
import systems.vostok.tda.util.constants.BuildingType._

object AddressTranslator {

  def getBuildingType(building: String): BuildingType = {
    val buildingUp = building.toUpperCase()

    if (buildingUp.matches("[0-9]*")) {
      SIMPLE
    } else if (buildingUp.contains("/")) {
      FRACTION
    } else if (buildingUp.matches(".*[А-Я]")) {
      LITERAL
    } else {
      throw new IllegalBuildingFormatException(building)
    }
  }

  def translateMapper(mappingData: List[Mapper], buildingType: BuildingType): List[Mapper] = {
    buildingType match {
      case SIMPLE => translateDataForSimple(mappingData)
      case _ => translateDataForComplex(mappingData, buildingType)
    }
  }

  def translateSingleMapperToSimple(singleMapper: Mapper): Mapper = {
    new Mapper(
      streetId = singleMapper.streetId,
      building = singleMapper.building,
      buildingFrom = extractFirstDigits(singleMapper.buildingFrom),
      buildingTo = extractFirstDigits(singleMapper.buildingTo),
      districtId = singleMapper.districtId
    )
  }

  protected def translateDataForSimple(mappingData: List[Mapper]): List[Mapper] = {
    mappingData.map { mapper =>
      if (mapper.building == "" || mapper.building == null) {
        mapper
      } else {
        val rawBuildingsFrom = mapper.building.split("-").head
        if (List(FRACTION, LITERAL).contains(getBuildingType(rawBuildingsFrom))) {
          mapper.buildingFrom = (extractFirstDigits(rawBuildingsFrom).toInt + 1).toString
        } else {
          mapper.buildingFrom = extractFirstDigits(rawBuildingsFrom)
        }
        mapper.buildingTo = extractFirstDigits(mapper.building.split("-").last)

        mapper
      }
    }
  }

  def translateDataForComplex(mappingData: List[Mapper], buildingType: BuildingType): List[Mapper] = {
    mappingData.map { mapper =>
      if (mapper.building == "") {
        mapper
      } else {
        val rawBuildingsFrom = mapper.building.split('-').head
        val rawBuildingsTo = mapper.building.split('-').last

        if (List(buildingType, SIMPLE).contains(getBuildingType(rawBuildingsFrom))) {
          mapper.buildingFrom = rawBuildingsFrom
        } else {
          mapper.buildingFrom = (extractFirstDigits(rawBuildingsFrom).toInt + 1).toString
        }

        if (List(buildingType, SIMPLE).contains(getBuildingType(rawBuildingsTo))) {
          mapper.buildingTo = rawBuildingsTo
        } else {
          mapper.buildingTo = extractFirstDigits(rawBuildingsTo)
        }

        mapper
      }
    }
  }
}
