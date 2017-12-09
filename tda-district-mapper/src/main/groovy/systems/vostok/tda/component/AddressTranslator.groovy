package systems.vostok.tda.component

import systems.vostok.tda.exception.IllegalBuildingFormatException
import systems.vostok.tda.util.constants.BuildingType

import static BuildingType.*
import static systems.vostok.tda.util.DataHelper.*

class AddressTranslator {

    static BuildingType getBuildingType(String building) {
        building = building.toUpperCase()

        if (building.isNumber()) {
            SIMPLE
        } else if (building.contains('/')) {
            FRACTION
        } else if (building.matches(".*[А-Я].*")) {
            LITERAL
        } else {
            throw new IllegalBuildingFormatException(building)
        }
    }

    static List translateMapper(List mappingData, BuildingType buildingType) {
        switch (buildingType) {
            case SIMPLE:
                translateDataForSimple(mappingData)
                break
            case FRACTION:
                translateDataForFraction(mappingData)
                break
            case LITERAL:
                translateDataForLiteral(mappingData)
                break
        }
    }

    static List translateDataForSimple(List mappingData) {
        cloneMapping(mappingData).collect {
            String rawBuildingsFrom = it.building.split('-')[0]

            if ([FRACTION, LITERAL].contains(getBuildingType(rawBuildingsFrom))) {
                it << [buildingFrom: ((extractFirstDigits(rawBuildingsFrom) as Integer) + 1) as String]
            } else {
                it << [buildingFrom: extractFirstDigits(rawBuildingsFrom)]
            }

            it << [buildingTo: extractFirstDigits(it.building.split('-')[1] as String)]
        }
    }

    static List translateDataForFraction(List mappingData) {

    }

    static List translateDataForLiteral(List mappingData) {

    }

}
