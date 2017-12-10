package systems.vostok.tda.service

import systems.vostok.tda.component.DistrictMapper
import systems.vostok.tda.util.constants.BuildingType

import static systems.vostok.tda.component.AccuracyChecker.*
import static systems.vostok.tda.component.AddressTranslator.*

import static systems.vostok.tda.component.DistrictMapper.*

class DistrictMapperService {

    String getDistrict(List mapperData, Map address) {
        checkAddressConsistence(address)
        checkMapperConsistence(mapperData)
        checkAddressMapperCompatibility(address, mapperData)

        BuildingType buildingType = getBuildingType(address.building)
        List adoptedMapperData = translateMapper(mapperData, buildingType)

        mapAddressToDistrict(adoptedMapperData, address.building, buildingType)
    }
}
