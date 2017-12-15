package systems.vostok.tda.service

import org.testng.annotations.DataProvider
import org.testng.annotations.Test

import static org.testng.Assert.assertEquals

class DistrictMapperServiceTest {

    static List mapperEmpty = [
            [streetId: 'svt', building: '', districtId: 'cnt']
    ]

    static List mapperSimple = [
            [streetId: 'svt', building: '1-24', districtId: 'cnt'],
            [streetId: 'svt', building: '25-50', districtId: 'dal'],
            [streetId: 'svt', building: '51-106', districtId: 'lug']
    ]

    static List mapperLiteral = [
            [streetId: 'svt', building: '1-24Б', districtId: 'cnt'],
            [streetId: 'svt', building: '24В-24Д', districtId: 'dal'],
            [streetId: 'svt', building: '24Е-106', districtId: 'lug'],
            [streetId: 'svt', building: '106А-109', districtId: 'spr']
    ]

    static List mapperDash = [
            [streetId: 'svt', building: '1-24В', districtId: 'cnt'],
            [streetId: 'svt', building: '24Г-24/3', districtId: 'dal'],
            [streetId: 'svt', building: '24/4-106', districtId: 'lug'],
            [streetId: 'svt', building: '107-107/2', districtId: 'spor'],
            [streetId: 'svt', building: '107/3-107/9', districtId: 'rog'],
            [streetId: 'svt', building: '108-112/6', districtId: 'tih'],
    ]

    static List mapperMix = [
            [streetId: 'svt', building: '1-23', districtId: 'cnt'],
            [streetId: 'svt', building: '24-24В', districtId: 'dal'],
            [streetId: 'svt', building: '24Г-24Е', districtId: 'lug'],
            [streetId: 'svt', building: '24З-25', districtId: 'spor'],
            [streetId: 'svt', building: '25А-107/9', districtId: 'rog'],
            [streetId: 'svt', building: '108-112/6', districtId: 'tih'],
    ]

    @DataProvider(name = 'mapper_test')
    Object[][] mapperParam() {
        [
                [[streetId: 'svt', building: '24'], mapperEmpty, 'cnt'],

                [[streetId: 'svt', building: '25'], mapperSimple, 'dal'],
                [[streetId: 'svt', building: '3'], mapperSimple, 'cnt'],
                [[streetId: 'svt', building: '3'], mapperLiteral, 'cnt'],
                [[streetId: 'svt', building: '24'], mapperLiteral, 'cnt'],
                [[streetId: 'svt', building: '50'], mapperLiteral, 'lug'],
                [[streetId: 'svt', building: '3'], mapperDash, 'cnt'],
                [[streetId: 'svt', building: '24'], mapperDash, 'cnt'],
                [[streetId: 'svt', building: '50'], mapperDash, 'lug'],

                [[streetId: 'svt', building: '1Е'], mapperLiteral, 'cnt'],
                [[streetId: 'svt', building: '24Г'], mapperLiteral, 'dal'],
                [[streetId: 'svt', building: '24Е'], mapperLiteral, 'lug'],
                [[streetId: 'svt', building: '24З'], mapperLiteral, 'lug'],
                [[streetId: 'svt', building: '28Е'], mapperLiteral, 'lug'],
                [[streetId: 'svt', building: '106Б'], mapperLiteral, 'spr'],

                [[streetId: 'svt', building: '1/1'], mapperDash, 'cnt'],
                [[streetId: 'svt', building: '107/1'], mapperDash, 'spor'],
                [[streetId: 'svt', building: '107/3'], mapperDash, 'rog'],
                [[streetId: 'svt', building: '112/6'], mapperDash, 'tih'],
                [[streetId: 'svt', building: '108/6'], mapperDash, 'tih'],

                [[streetId: 'svt', building: '24А'], mapperMix, 'dal'],
                [[streetId: 'svt', building: '24Г'], mapperMix, 'lug'],
                [[streetId: 'svt', building: '25А'], mapperMix, 'rog']
        ]
    }

    @Test(dataProvider = 'mapper_test')
    void mapperTest(Map address, List mapper, String expected) {
        new DistrictMapperService().getDistrict(mapper, address)
                .with { assertEquals(it, expected) }
    }
}