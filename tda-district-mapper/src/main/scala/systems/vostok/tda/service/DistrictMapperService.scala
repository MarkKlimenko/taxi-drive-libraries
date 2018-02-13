package systems.vostok.tda.service

import systems.vostok.tda.component.AccuracyChecker._
import systems.vostok.tda.component.AddressTranslator._
import systems.vostok.tda.component.DistrictMapper._
import systems.vostok.tda.domain.{Address, Mapper}

class DistrictMapperService {
  def getDistrict(mapperData: List[Mapper], address: Address): String = {
    checkAddressConsistence(address)
    checkMapperConsistence(mapperData)
    checkAddressMapperCompatibility(address, mapperData)

    val buildingType = getBuildingType(address.building)
    val adoptedMapperData = translateMapper(mapperData, buildingType)

    mapAddressToDistrict(adoptedMapperData, address.building, buildingType)
  }
}
