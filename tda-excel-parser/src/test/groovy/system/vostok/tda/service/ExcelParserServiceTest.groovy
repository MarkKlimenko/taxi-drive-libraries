package system.vostok.tda.service

import groovy.json.JsonSlurper
import org.junit.Test

import static org.testng.Assert.assertEquals

class ExcelParserServiceTest {
    @Test
    void plainHeaderExcelToListTest() {
        File xlsxFile = new File('src/test/resources/rates/PLAIN_HEADER.xlsx')
        File resultFile = new File('src/test/resources/rates/PLAIN_HEADER')

        new ExcelParserService()
                .parseDocument(xlsxFile, 0, 'PLAIN_HEADER')
                .with { assertEquals(it, new JsonSlurper().parseText(resultFile.getText())) }
    }

    @Test
    void mirrorDiagonalExcelToListTest() {
        File xlsxFile = new File('src/test/resources/rates/MIRROR_DIAGONAL.xlsx')
        File resultFile = new File('src/test/resources/rates/MIRROR_DIAGONAL')

        new ExcelParserService()
                .parseDocument(xlsxFile, 0, 'MIRROR_DIAGONAL')
                .with { assertEquals(it, new JsonSlurper().parseText(resultFile.getText())) }
    }
}