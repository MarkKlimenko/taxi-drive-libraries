package system.vostok.tda.util

import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.CellValue

import java.text.DecimalFormat

class CellValueUtil {
    static String getCellValue(CellValue cell, String cellType) {
        if(cell) {
            switch (cellType) {
                case 'mixed':
                    return getMixedCellValue(cell)
                case 'number':
                    return cell.getNumberValue() as String
                case 'string':
                    return cell.getStringValue().toString()
            }
        }
        null
    }

    static String getMixedCellValue(CellValue cell) {
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                return cell.getStringValue()
            case Cell.CELL_TYPE_NUMERIC:
                return formatNumericValue(cell.getNumberValue())
            case Cell.CELL_TYPE_BOOLEAN:
                return cell.getBooleanValue()
        }
        null
    }

    static String formatNumericValue(Double value) {
        new DecimalFormat("#.####").format(value)
    }
}
