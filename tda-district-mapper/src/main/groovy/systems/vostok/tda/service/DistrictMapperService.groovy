package systems.vostok.tda.service

import static systems.vostok.tda.component.AccuracyChecker.*

class DistrictMapperService {

    String getDistrict(List mapperData, Map address) {
        checkAddressConsistence(address)
        checkMapperConsistence(mapperData)



    }
}
