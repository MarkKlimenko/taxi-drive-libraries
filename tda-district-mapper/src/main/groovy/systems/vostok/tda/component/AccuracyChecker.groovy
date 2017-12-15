package systems.vostok.tda.component

import systems.vostok.tda.exception.IllegalBuildingFormatException
import systems.vostok.tda.exception.IllegalEntityIdFormatException
import systems.vostok.tda.exception.NoMapperDataException
import systems.vostok.tda.exception.NotCompatibleMapperException

class AccuracyChecker {
    static Boolean checkAddressConsistence(Map address) {
        checkEntityId(address.streetId as String)
        checkBuilding(address.building as String)
        true
    }

    static Boolean checkMapperConsistence(List mapper) {
        if(mapper.isEmpty()) {
            throw new NoMapperDataException()
        }

        mapper.each {
            checkEntityId(it.streetId as String)
            checkEntityId(it.districtId as String)
            if(it.building != '') {
                checkBuilding(it.building.split('-')[0] as String)
                checkBuilding(it.building.split('-')[1] as String)
            }
        }
        true
    }

    static Boolean checkAddressMapperCompatibility(Map address, List mapper) {
        String targetStreetId = address.streetId as String

        mapper.each {
            if(targetStreetId != it.streetId) {
                throw new NotCompatibleMapperException(targetStreetId, it.streetId as String)
            }
        }
        true
    }

    protected static Boolean checkEntityId(String id) {
        if (id == null || id == '' || id.contains(' ')) {
            throw new IllegalEntityIdFormatException(id)
        }
        true
    }

    protected static Boolean checkBuilding(String building) {
        if (building != null) {
            building = building.toUpperCase()

            if (building.isNumber()) {
                return true
            } else if (building.matches(/.\d*\/.\d*/)) {
                    List<String> matcherResult = (building =~ /(.\d*)\/(.\d*)/)[0]

                    if(matcherResult[1].isNumber()
                            && matcherResult[2].isNumber()
                            && !matcherResult[2].matches(/^0.*/)) {
                        return true
                    }
            } else if(building.matches(/.\d*[А-Я]/)) {
                    return true
            }
        }
        throw new IllegalBuildingFormatException(building)
    }
}
