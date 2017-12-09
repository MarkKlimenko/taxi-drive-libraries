package systems.vostok.tda.component

import org.testng.annotations.DataProvider
import org.testng.annotations.Test
import systems.vostok.tda.exception.IllegalBuildingFormatException
import systems.vostok.tda.exception.IllegalEntityIdFormatException

import static org.testng.Assert.assertEquals

class AccuracyCheckerTest {

    @DataProvider(name = 'address_checker_test')
    Object[][] addressCheckerParam() {
        [[[streetId: 'svt', building: '3']],
         [[streetId: 'svt', building: '3А']],
         [[streetId: 'svt', building: '3/1']]]
    }

    @Test(dataProvider = 'address_checker_test')
    void addressCheckerTest(Map address) {
        new AccuracyChecker().checkAddressConsistence(address)
                .with { assertEquals(it, true) }
    }


    @DataProvider(name = 'address_checker_exception_test')
    Object[][] addressCheckerExceptionParam() {
        [[[building: '3']],
         [[streetId: 'svt']],
         [[streetId: '', building: '3']],
         [[streetId: 'svt v', building: '3']],
         [[streetId: 'svt', building: '']],
         [[streetId: 'svt', building: '3АА']],
         [[streetId: 'svt', building: '3F']],
         [[streetId: 'svt', building: '3/01']],
         [[streetId: 'svt', building: '3/А']],
         [[streetId: 'svt', building: '3//1']],
         [[streetId: 'svt', building: '3/1А']],
         [[streetId: 'svt', building: 'test']]
         ]
    }

    @Test(dataProvider = 'address_checker_exception_test',
            expectedExceptions = [IllegalBuildingFormatException.class, IllegalEntityIdFormatException.class])
    void addressCheckerExceptionTest(Map address) {
        new AccuracyChecker().checkAddressConsistence(address)
    }


    List mapperData = [
            [streetId: 'test', building: '1-1/6', districtId: 'test'],
            [streetId: 'test', building: '1/6-24А', districtId: 'test'],
            [streetId: 'test', building: '24Б-50', districtId: 'test']
    ]

    @Test
    void mapperCheckerTest() {
        new AccuracyChecker().checkMapperConsistence(mapperData)
                .with { assertEquals(it, true) }
    }

// TODO
    @DataProvider(name = 'mapper_checker_exception_test')
    Object[][] mapperCheckerExceptionParam() {
        [[[building: '3']],
         [[streetId: 'svt']],
         [[streetId: '', building: '3']],
         [[streetId: 'svt v', building: '3']],
         [[streetId: 'svt', building: '']],
         [[streetId: 'svt', building: '3АА']],
         [[streetId: 'svt', building: '3F']],
         [[streetId: 'svt', building: '3/01']],
         [[streetId: 'svt', building: '3/А']],
         [[streetId: 'svt', building: '3//1']],
         [[streetId: 'svt', building: '3/1А']],
         [[streetId: 'svt', building: 'test']]
        ]
    }

    @Test(dataProvider = 'mapper_checker_exception_test',
            expectedExceptions = [IllegalBuildingFormatException.class, IllegalEntityIdFormatException.class])
    void mapperCheckerExceptionTest(Map address) {
        new AccuracyChecker().checkAddressConsistence(address)
    }
}
