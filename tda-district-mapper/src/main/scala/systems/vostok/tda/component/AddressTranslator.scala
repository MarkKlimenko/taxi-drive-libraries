package systems.vostok.tda.component

import systems.vostok.tda.domain.Mapper
import systems.vostok.tda.exception.IllegalBuildingFormatException
import systems.vostok.tda.util.constants.BuildingType._

object AddressTranslator {

  def getBuildingType(building: String): BuildingType = {
    val buildingUp = building.toUpperCase()

    if (buildingUp forall Character.isDigit) {
      SIMPLE
    } else if (buildingUp.contains("/")) {
      FRACTION
    } else if (buildingUp.matches(".*[А-Я]")) {
      LITERAL
    } else {
      throw new IllegalBuildingFormatException(building)
    }
  }

  /*def translateMapper(mappingData: List[Mapper], buildingType: BuildingType): List[Mapper] = {
   /* buildingType match {
      //case SIMPLE => translateDataForSimple(mappingData)
      //case _ => translateDataForComplex(mappingData, buildingType)
    }*/
  }*/

  /*def translateSingleMapperToSimple(singleMapper: Mapper): Mapper = {
    val result = singleMapper//.clone()

    result.buildingFrom = extractFirstDigits(singleMapper.buildingFrom)
    result.buildingTo = extractFirstDigits(singleMapper.buildingTo)
  }*/

  /*

  static Map translateSingleMapperToSimple(Map singleMapper) {
      Map result = singleMapper.clone() as Map

      result << [ buildingFrom: extractFirstDigits(singleMapper.buildingFrom),
                  buildingTo: extractFirstDigits(singleMapper.buildingTo) ]
  }

  protected static List translateDataForSimple(List mappingData) {
      cloneMapping(mappingData).collect {
          if(it.building == '') {
              it
          } else {
              String rawBuildingsFrom = it.building.split('-')[0]

              if ([FRACTION, LITERAL].contains(getBuildingType(rawBuildingsFrom))) {
                  it << [buildingFrom: ((extractFirstDigits(rawBuildingsFrom) as Integer) + 1) as String]
              } else {
                  it << [buildingFrom: extractFirstDigits(rawBuildingsFrom)]
              }

              it << [buildingTo: extractFirstDigits(it.building.split('-')[1] as String)]
          }
      }
  }

  protected static List translateDataForComplex(List mappingData, BuildingType buildingType) {
      cloneMapping(mappingData).collect {
          if(it.building == '') {
              it
          } else {
              String rawBuildingsFrom = it.building.split('-')[0]
              String rawBuildingsTo = it.building.split('-')[1]

              if ([buildingType, SIMPLE].contains(getBuildingType(rawBuildingsFrom))) {
                  it << [buildingFrom: rawBuildingsFrom as String]
              } else {
                  it << [buildingFrom: ((extractFirstDigits(rawBuildingsFrom) as Integer) + 1) as String]
              }

              if ([buildingType, SIMPLE].contains(getBuildingType(rawBuildingsTo))) {
                  it << [buildingTo: rawBuildingsTo as String]
              } else {
                  it << [buildingTo: extractFirstDigits(rawBuildingsTo)]
              }
          }
      }
  }*/
}
