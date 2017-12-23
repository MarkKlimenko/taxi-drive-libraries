package system.vostok.tda.service

import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.FormulaEvaluator
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import system.vostok.tda.util.CellValueUtil

class ExcelParserService {

    List<String> parseDocument(Object file, Integer sheetIndex, String documentType) {
        [MIRROR_DIAGONAL: { mirrorDiagonalExcelToList(file, sheetIndex) },
         PLAIN_HEADER   : { plainHeaderExcelToList(file, sheetIndex) }]
                ."${documentType}"
                .call()
    }

    static List plainHeaderExcelToList(Object file, Integer sheetIndex) {
        List fileContent = []
        Workbook wb = new XSSFWorkbook(file)
        FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator()
        Iterator<Row> rowIterator = wb
                .getSheetAt(sheetIndex)
                .iterator()

        while (rowIterator.hasNext()) {
            List rowContent = []
            Iterator<Cell> cellIterator = rowIterator.next().iterator()

            while (cellIterator.hasNext()) {
                rowContent << CellValueUtil.getCellValue(evaluator.evaluate(cellIterator.next()), 'mixed')
            }
            fileContent << rowContent
        }
        fileContent
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
                                result << [ from, header[cellIndex - 1], cell ]
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
