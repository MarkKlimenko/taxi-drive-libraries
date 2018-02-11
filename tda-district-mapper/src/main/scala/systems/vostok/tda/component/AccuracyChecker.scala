package systems.vostok.tda.component

import systems.vostok.tda.exception._

object AccuracyChecker {

  //TODO: get rid of maps - add objects
  //TODO: refactor

  def checkAddressConsistence(address: Map[String, String]): Boolean = {
    checkEntityId(address.get("streetId"))
    checkEntityId(address.get("building"))
    checkBuilding(address.get("building"))
  }

  def checkMapperConsistence(mapper: List[Map[String, String]]): Boolean = {
    if (mapper.isEmpty) {
      throw new NoMapperDataException()
    }

    mapper.foreach { mapperLine =>
      checkEntityId(mapperLine.get("streetId"))
      checkEntityId(mapperLine.get("districtId"))

      if (mapperLine("building") != "") {
        checkBuilding(Option(mapperLine("building").split('-')(0)))
        checkBuilding(Option(mapperLine("building").split('-')(1)))
      }
    }
    true
  }

  def checkAddressMapperCompatibility(address: Map[String, String], mapper: List[Map[String, String]]): Boolean = {
    mapper.foreach { mapperLine =>
      if (address("streetId") != mapperLine("streetId")) {
        throw new NotCompatibleMapperException(address("streetId"), mapperLine("streetId"))
      }
    }
    true
  }

  protected def checkEntityId(id: Option[String]): Boolean = {
    if (id.isEmpty || id.get == "" || id.get.contains(" ")) {
      throw new IllegalEntityIdFormatException(id = id.toString)
    }
    true
  }

  protected def checkBuilding(building: Option[String]): Boolean = {
    if (!(building.isEmpty || building.get == "" || building.get.contains(" "))) {
      val buildingUp = building.get.toUpperCase

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
    throw new IllegalBuildingFormatException(building = building.get)
  }
}
