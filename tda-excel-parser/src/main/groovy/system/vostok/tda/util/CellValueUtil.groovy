package system.vostok.tda.util

import org.apache.poi.ss.usermodel.Cell

import java.text.DecimalFormat

class CellValueUtil {
    static String getCellValue(Cell cell, String cellType) {
        switch (cellType) {
            case 'mixed':
                return getMixedCellValue(cell)
            case 'number':
                return cell.getNumericCellValue() as String
            case 'string':
                return cell.getStringCellValue().toString()
        }
        null
    }

    static String getMixedCellValue(Cell cell) {
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                return cell.getStringCellValue()
            case Cell.CELL_TYPE_NUMERIC:
                return formatNumericValue(cell.getNumericCellValue())
            case Cell.CELL_TYPE_BOOLEAN:
                return cell.getBooleanCellValue()
        }
        null
    }

    static String formatNumericValue(Double value) {
        new DecimalFormat("#.####").format(value)
    }
}
