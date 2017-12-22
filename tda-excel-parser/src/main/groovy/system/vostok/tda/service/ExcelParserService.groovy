package system.vostok.tda.service

import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import system.vostok.tda.util.CellValueUtil

class ExcelParserService {

    List parseDocument(Object file, Integer sheetIndex, String documentType) {
        [MIRROR_DIAGONAL: { mirrorDiagonalExcelToList(file, sheetIndex) },
         PLAIN_HEADER   : { plainHeaderExcelToList(file, sheetIndex) }]
                ."${documentType}"
                .call()
    }

    static List plainHeaderExcelToList(Object file, Integer sheetIndex) {
        List fileContent = []
        Iterator<Row> rowIterator = new XSSFWorkbook(file)
                .getSheetAt(sheetIndex)
                .iterator()

        while (rowIterator.hasNext()) {
            List rowContent = []
            Iterator<Cell> cellIterator = rowIterator.next().iterator()

            while (cellIterator.hasNext()) {
                rowContent << CellValueUtil.getCellValue(cellIterator.next(), 'mixed')
            }
            fileContent << rowContent
        }
        fileContent
    }

    static List mirrorDiagonalExcelToList(Object file, Integer sheetIndex) {

    }
}
