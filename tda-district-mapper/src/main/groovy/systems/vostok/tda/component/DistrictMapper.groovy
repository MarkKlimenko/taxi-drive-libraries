package systems.vostok.tda.component

import systems.vostok.tda.exception.NoTargetDistrictException
import systems.vostok.tda.util.constants.BuildingType

import static systems.vostok.tda.component.AddressTranslator.translateSingleMapperToSimple
import static systems.vostok.tda.util.DataHelper.extractFirstDigits
import static systems.vostok.tda.util.DataHelper.extractLetterLiteral
import static systems.vostok.tda.util.DataHelper.extractNumericLiteral
import static systems.vostok.tda.util.constants.BuildingType.*

class DistrictMapper {

    static String mapAddressToDistrict(List adoptedMapperData, String building, BuildingType buildingType) {
        if(adoptedMapperData.first().building == '') {
            adoptedMapperData.first().districtId
        } else {
            Map properMapper = adoptedMapperData.find { checkAccordance(it as Map, building, buildingType) } as Map

            if (properMapper) {
                properMapper.districtId
            } else {
                throw new NoTargetDistrictException()
            }
        }
    }

    protected static Boolean checkAccordance(Map mapperData, String building, BuildingType buildingType) {
        switch (buildingType) {
            case SIMPLE:
                return checkSimpleAccordance(mapperData, building)
            case LITERAL:
                return checkLiteralAccordance(mapperData, building)
            case FRACTION:
                return checkFractionAccordance(mapperData, building)
        }
    }

    protected static Boolean checkSimpleAccordance(Map mapperData, String building) {
        ((building as Integer) <= (mapperData.buildingTo as Integer)) &&
                ((building as Integer) >= (mapperData.buildingFrom as Integer))
    }

    protected static Boolean checkLiteralAccordance(Map mapperData, String building) {
        String litFreeBuilding = extractFirstDigits(building)
        String litFreeBuildingFrom = extractFirstDigits(mapperData.buildingFrom)
        String litFreeBuildingTo = extractFirstDigits(mapperData.buildingTo)

        if (litFreeBuilding != litFreeBuildingFrom && litFreeBuilding != litFreeBuildingTo) {
            return checkSimpleAccordance(translateSingleMapperToSimple(mapperData), litFreeBuilding)

        } else if (litFreeBuilding == litFreeBuildingFrom && litFreeBuilding != litFreeBuildingTo) {
            return compareLetters(extractLetterLiteral(mapperData.buildingFrom),
                    'Я',
                    extractLetterLiteral(building))

        } else if (litFreeBuilding != litFreeBuildingFrom && litFreeBuilding == litFreeBuildingTo) {
            return compareLetters('А',
                    extractLetterLiteral(mapperData.buildingTo),
                    extractLetterLiteral(building))

        } else if (litFreeBuilding == litFreeBuildingFrom && litFreeBuilding == litFreeBuildingTo) {
            return compareLetters(extractLetterLiteral(mapperData.buildingFrom),
                    extractLetterLiteral(mapperData.buildingTo),
                    extractLetterLiteral(building))

        }
        false
    }

    protected static Boolean compareLetters(String letFrom, String letTo, String letBuilding) {
        if (!letFrom) {
            letFrom = 'А'
        }
        if (!letTo) {
            return false
        }

        [letFrom..letTo].flatten().contains(letBuilding)
    }

    protected static Boolean checkFractionAccordance(Map mapperData, String building) {
        String litFreeBuilding = extractFirstDigits(building)
        String litFreeBuildingFrom = extractFirstDigits(mapperData.buildingFrom)
        String litFreeBuildingTo = extractFirstDigits(mapperData.buildingTo)

        if (litFreeBuilding != litFreeBuildingFrom && litFreeBuilding != litFreeBuildingTo) {
            return checkSimpleAccordance(translateSingleMapperToSimple(mapperData), litFreeBuilding)

        } else if (litFreeBuilding == litFreeBuildingFrom && litFreeBuilding != litFreeBuildingTo) {
            return compareNumbers(extractNumericLiteral(mapperData.buildingFrom),
                    '9999',
                    extractNumericLiteral(building))

        } else if (litFreeBuilding != litFreeBuildingFrom && litFreeBuilding == litFreeBuildingTo) {
            return compareNumbers('0',
                    extractNumericLiteral(mapperData.buildingTo),
                    extractNumericLiteral(building))

        } else if (litFreeBuilding == litFreeBuildingFrom && litFreeBuilding == litFreeBuildingTo) {
            return compareNumbers(extractNumericLiteral(mapperData.buildingFrom),
                    extractNumericLiteral(mapperData.buildingTo),
                    extractNumericLiteral(building))

        }
        false
    }

    protected static Boolean compareNumbers(String numFrom, String numTo, String numBuilding) {
        if (!numFrom) {
            numFrom = 0
        }
        if (!numTo) {
            return false
        }

        [(numFrom as Integer)..(numTo as Integer)].flatten().contains(numBuilding as Integer)
    }
}
