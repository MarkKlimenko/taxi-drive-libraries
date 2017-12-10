package systems.vostok.tda.util

import org.testng.annotations.DataProvider
import org.testng.annotations.Test

import static org.testng.Assert.*;

class DataHelperTest {

    @DataProvider(name = 'extract_first_digits')
    Object[][] extractFirstDigitsParam() {
        [['15', '15'],
         ['15/3', '15'],
         ['15/23', '15'],
         ['1543/23', '1543'],
         ['15А', '15']]
    }

    @Test(dataProvider = 'extract_first_digits')
    void extractFirstDigitsTest(String building, String result) {
        DataHelper.extractFirstDigits(building)
                .with { assertEquals(it, result) }
    }


    @DataProvider(name = 'extract_letter_literal')
    Object[][] extractLetterLiteralParam() {
        [['15', null],
         ['15А', 'А'],
         ['15/23', null]]
    }

    @Test(dataProvider = 'extract_letter_literal')
    void extractLetterLiteralTest(String building, String result) {
        DataHelper.extractLetterLiteral(building)
                .with { assertEquals(it, result) }
    }

    @DataProvider(name = 'extract_numeric_literal')
    Object[][] extractNumericLiteralParam() {
        [['15', null],
         ['15А', null],
         ['15/23', '23']]
    }

    @Test(dataProvider = 'extract_numeric_literal')
    void extractNumericLiteralTest(String building, String result) {
        DataHelper.extractNumericLiteral(building)
                .with { assertEquals(it, result) }
    }
}