package system.vostok.tda.service

import org.apache.poi.ss.usermodel.WorkbookFactory
import system.vostok.tda.util.CellValueUtil

class ExcelParserService {
    List<String> parseDocument(Object file, Integer sheetIndex, String documentType) {
        [MIRROR_DIAGONAL: { mirrorDiagonalExcelToList(file, sheetIndex) },
         PLAIN_HEADER   : { plainHeaderExcelToList(file, sheetIndex) }]
                ."${documentType}"
                .call()
    }

    static List plainHeaderExcelToList(Object file, Integer sheetIndex) {
        WorkbookFactory.create(file)
                .getSheetAt(sheetIndex)
                .collect { it.collect(CellValueUtil.&getCellValue) }
    }

    static List mirrorDiagonalExcelToList(Object file, Integer sheetIndex) {
        List header = []
        List result = []

        plainHeaderExcelToList(file, sheetIndex)
                .with { getRidOfEmptyCells(it) }
                .eachWithIndex { row, rowIndex ->
            if (rowIndex == 0) {
                header = row
            } else {
                String from = row.first()
                row.eachWithIndex { cell, cellIndex ->
                    if (cellIndex != 0) {
                        result << [from, header[cellIndex - 1], cell]
                    }
                }
            }
        }
        result
    }

    static List getRidOfEmptyCells(List table) {
        table.findAll { it }
                .collect { it.findAll { it } }
                .findAll { it }
    }
}
