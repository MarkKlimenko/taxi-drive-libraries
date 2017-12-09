package systems.vostok.tda.component

import systems.vostok.tda.exception.IllegalBuildingFormatException
import systems.vostok.tda.exception.IllegalEntityIdFormatException

class AccuracyChecker {
    static Boolean checkAddressConsistence(Map address) {
        checkEntityId(address.streetId as String)
        checkBuilding(address.building as String)
        true
    }

    static Boolean checkMapperConsistence(List mapper) {
        mapper.each {
            checkEntityId(it.streetId as String)
            checkBuilding(it.building.split('-')[0] as String)
            checkBuilding(it.building.split('-')[1] as String)
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
