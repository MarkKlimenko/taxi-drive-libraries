package systems.vostok.tda.component

import systems.vostok.tda.exception.IllegalBuildingFormatException
import systems.vostok.tda.util.constants.BuildingType

import static systems.vostok.tda.util.constants.BuildingType.*
import static systems.vostok.tda.util.DataHelper.*

class AddressTranslator {

    static BuildingType getBuildingType(String building) {
        building = building.toUpperCase()

        if (building.isNumber()) {
            SIMPLE
        } else if (building.contains('/')) {
            FRACTION
        } else if (building.matches(/.*[А-Я]/)) {
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
                translateDataForComplex(mappingData, buildingType)
                break
            case LITERAL:
                translateDataForComplex(mappingData, buildingType)
                break
        }
    }

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
    }
}
