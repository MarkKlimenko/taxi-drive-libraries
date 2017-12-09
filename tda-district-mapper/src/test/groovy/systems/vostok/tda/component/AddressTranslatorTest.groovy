package systems.vostok.tda.component

import org.testng.annotations.DataProvider
import org.testng.annotations.Test
import systems.vostok.tda.exception.IllegalBuildingFormatException
import systems.vostok.tda.util.constants.BuildingType

import static org.testng.Assert.assertEquals
import static BuildingType.*

class AddressTranslatorTest {

    @DataProvider(name = 'building_type_test')
    Object[][] buildingTypeParam() {
        [['24', SIMPLE],
         ['24А', LITERAL],
         ['24/2', FRACTION]]
    }

    @Test(dataProvider = 'building_type_test')
    void buildingTypeTest(String building, BuildingType type) {
        new AddressTranslator().getBuildingType(building)
                .with { assertEquals(it, type) }
    }


    @DataProvider(name = 'building_type_exception_test')
    Object[][] buildingTypeExceptionParam() {
        [['test'],
         ['test24']]
    }

    @Test(dataProvider = 'building_type_exception_test', expectedExceptions = IllegalBuildingFormatException.class)
    void buildingTypeExceptionTest(String building) {
        new AddressTranslator().getBuildingType(building)
    }


    List mapperDataSimple1 = [
            [streetId: 'test', building: '1-1/6', districtId: 'test'],
            [streetId: 'test', building: '1/6-24А', districtId: 'test'],
            [streetId: 'test', building: '24Б-50', districtId: 'test']
    ]

    List expectedMapperSimple1 = [
            [streetId: 'test', building: '1-1/6', districtId: 'test', buildingFrom: '1', buildingTo: '1'],
            [streetId: 'test', building: '1/6-24А', districtId: 'test', buildingFrom: '2', buildingTo: '24'],
            [streetId: 'test', building: '24Б-50', districtId: 'test', buildingFrom: '25', buildingTo: '50']
    ]

    List mapperDataSimple2 = [
            [streetId: 'test', building: '1А-24А', districtId: 'test'],
            [streetId: 'test', building: '25-50Г', districtId: 'test']
    ]

    List expectedMapperSimple2 = [
            [streetId: 'test', building: '1А-24А', districtId: 'test', buildingFrom: '2', buildingTo: '24'],
            [streetId: 'test', building: '25-50Г', districtId: 'test', buildingFrom: '25', buildingTo: '50']
    ]

    @DataProvider(name = 'mapper_translator_test')
    Object[][] mapperTranslatorParam() {
        [[mapperDataSimple1, SIMPLE, expectedMapperSimple1],
         [mapperDataSimple2, SIMPLE, expectedMapperSimple2]]
    }

    @Test(dataProvider = 'mapper_translator_test')
    void mapperTranslatorTest(List data, BuildingType buildingType, List expectedData) {
        List translatedData = new AddressTranslator().translateMapper(data, buildingType)
        assertEquals(translatedData, expectedData)
    }
}
