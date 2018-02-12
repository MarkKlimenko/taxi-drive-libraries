package systems.vostok.tda.component

import systems.vostok.tda.domain.{Address, Mapper}
import systems.vostok.tda.exception._

object AccuracyChecker {

  //TODO: get rid of maps - add objects
  //TODO: refactor

  def checkAddressConsistence(address: Address): Boolean = {
    checkEntityId(address.streetId)
    checkEntityId(address.building)
    checkBuilding(address.building)
  }

  def checkMapperConsistence(mapper: List[Mapper]): Boolean = {
    if (mapper.isEmpty) {
      throw new NoMapperDataException()
    }

    mapper.foreach { mapperLine =>
      checkEntityId(mapperLine.streetId)
      checkEntityId(mapperLine.districtId)

      if (mapperLine.building != "") {
        checkBuilding(mapperLine.building.split('-')(0))
        checkBuilding(mapperLine.building.split('-')(1))
      }
    }
    true
  }

  def checkAddressMapperCompatibility(address: Address, mapper: List[Mapper]): Boolean = {
    mapper.foreach { mapperLine =>
      if (address.streetId != mapperLine.streetId) {
        throw new NotCompatibleMapperException(address.streetId, mapperLine.streetId)
      }
    }
    true
  }

  protected def checkEntityId(id: String): Boolean = {
    if (id == null || id == "" || id.contains(" ")) {
      throw new IllegalEntityIdFormatException(id = id)
    }
    true
  }

  protected def checkBuilding(building: String): Boolean = {
    if (!(building == null || building == "" || building.contains(" "))) {
      val buildingUp = building.toUpperCase

      if (buildingUp forall Character.isDigit) {
        return true
      } else if (buildingUp.matches(".\\d*\\/.\\d*")) {
        val pattern = "(.\\d*)\\/(.\\d*)".r
        val pattern(firstLit, secondLit) = buildingUp

        if (firstLit.matches("[1-9]*") && secondLit.matches("[1-9]*")) {
          return true
        }

      } else if (buildingUp.matches(".\\d*[А-Я]")) {
        return true
      }
    }
    throw new IllegalBuildingFormatException(building = building)
  }
}
