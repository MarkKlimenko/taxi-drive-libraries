package systems.vostok.tda.service

import org.testng.annotations.DataProvider
import org.testng.annotations.Test

import static org.testng.Assert.*

class DistrictMapperServiceTest {

    static List mapperSimple = [
            [streetId: 'svt', building: '1-24', districtId: 'cnt'],
            [streetId: 'svt', building: '25-50', districtId: 'dal'],
            [streetId: 'svt', building: '51-106', districtId: 'lug']
    ]

    static List mapperLiteral = [
            [streetId: 'svt', building: '1-24В', districtId: 'cnt'],
            [streetId: 'svt', building: '24В-24З', districtId: 'dal'],
            [streetId: 'svt', building: '24З-106', districtId: 'lug']
    ]

    static List mapperDash = [
            [streetId: 'svt', building: '1-24В', districtId: 'cnt'],
            [streetId: 'svt', building: '24В-24/3', districtId: 'dal'],
            [streetId: 'svt', building: '24/4-106', districtId: 'lug']
    ]

// Simple test suite
    @DataProvider(name = 'simple_mapper_test')
    Object[][] simpleMapperParam() {
        [[[streetId: 'svt', building: '25'], 'dal'],
         [[streetId: 'svt', building: '3'], 'cnt']]
    }

    @Test(dataProvider = 'simple_mapper_test')
    void simpleMapperTest(Map address, String expected) {
        new DistrictMapperService().getDistrict(mapperSimple, address)
                .with { assertEquals(it, expected) }
    }


}