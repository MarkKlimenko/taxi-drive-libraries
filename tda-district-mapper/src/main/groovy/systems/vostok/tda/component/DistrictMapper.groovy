package systems.vostok.tda.component

import systems.vostok.tda.exception.NoTargetDistrictException
import systems.vostok.tda.util.constants.BuildingType

import static systems.vostok.tda.util.constants.BuildingType.*

class DistrictMapper {

    static String mapAddressToDistrict(List adoptedMapperData, String building, BuildingType buildingType) {
        Map properMapper = adoptedMapperData.find { checkAccordance(it as Map, building, buildingType) } as Map

        if(properMapper) {
            return properMapper.districtId
        } else {
            throw new NoTargetDistrictException()
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
        ( (building as Integer) <= (mapperData.buildingTo as Integer)) &&
                ( (building as Integer)  >= (mapperData.buildingFrom as Integer) )
    }

    protected static Boolean checkLiteralAccordance(Map mapperData, String building) {

    }

    protected static Boolean checkFractionAccordance(Map mapperData, String building) {

    }

}
