package systems.vostok.tda.service

import org.testng.annotations.DataProvider
import org.testng.annotations.Test

import static org.testng.Assert.*

class DistrictMapperServiceTest {

    static List mapperSimple = [
            [streetId: 'Svetlanskaya', building: '1-24', districtId: 'Center'],
            [streetId: 'Svetlanskaya', building: '25-50', districtId: 'Dalzavod'],
            [streetId: 'Svetlanskaya', building: '51-106', districtId: 'Lugovaya']
    ]

    static List mapperLiteral = [
            [streetId: 'Svetlanskaya', building: '1-24В', districtId: 'Center'],
            [streetId: 'Svetlanskaya', building: '24В-24З', districtId: 'Dalzavod'],
            [streetId: 'Svetlanskaya', building: '24З-106', districtId: 'Lugovaya']
    ]

    static List mapperDash = [
            [streetId: 'Svetlanskaya', building: '1-24В', districtId: 'Center'],
            [streetId: 'Svetlanskaya', building: '24В-24/3', districtId: 'Dalzavod'],
            [streetId: 'Svetlanskaya', building: '24/4-106', districtId: 'Lugovaya']
    ]

// Simple test suite
    @DataProvider(name = 'simple_mapper_test')
    Object[][] simpleMapperParam() {
        [[[street: 'Svetlanskaya', building: '25'], 'Dalzavod'],
         [[street: 'Svetlanskaya', building: '3'], 'Center']]
    }

    @Test(dataProvider = 'simple_mapper_test')
    void simpleMapperTest(Map address, String expected) {
        new DistrictMapperService().getDistrict(mapperSimple, address)
                .with { assertEquals(it, expected) }
    }


// Exception handler suite
    @DataProvider(name = 'exception_mapper_test')
    Object[][] exceptionMapperParam() {
        [[[street: 'Svetlanskaya', building: ''], new IllegalArgumentException('no_property_supplied')],
         [[street: 'Svetlanskaya', building: '110'], new IllegalArgumentException('building_out_of_range')],
         [[street: 'Svetlanskaya', building: '3АВ'], new IllegalArgumentException('illegal_literal_format')],
         [[street: 'Svetlanskaya', building: '3/a'], new IllegalArgumentException('illegal_literal_format')],
         [[street: 'Svetlanskaya', building: '3F'], new IllegalArgumentException('wrong_literal_language')]]
    }

    @Test(dataProvider = 'exception_mapper_test')
    void exceptionMapperTest(Map address, String expected) {
        new DistrictMapperService().getDistrict(mapperSimple, address)
                .with { assertEquals(it, expected) }
    }


}