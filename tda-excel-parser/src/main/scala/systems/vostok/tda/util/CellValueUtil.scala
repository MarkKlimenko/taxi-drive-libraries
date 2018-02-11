package systems.vostok.tda.util

import java.text.DecimalFormat

import org.apache.poi.ss.usermodel.CellType._
import org.apache.poi.ss.usermodel.{Cell, DateUtil}

object CellValueUtil {
  def getCellValue(cell: Cell): String = {
    cell.getCellTypeEnum match {
      case BOOLEAN => cell.getBooleanCellValue.toString
      case STRING => cell.getRichStringCellValue.toString
      case NUMERIC => getNumericValue(cell)
      case FORMULA => getFormulaValue(cell)
      case BLANK => ""
      case _ => null
    }
  }

  def getFormulaValue(cell: Cell): String = {
    cell.getCachedFormulaResultTypeEnum match {
      case STRING => cell.getRichStringCellValue.toString
      case NUMERIC => getNumericValue(cell)
      case _ => null
    }
  }

  def getNumericValue(cell: Cell): String = {
    if (DateUtil.isCellDateFormatted(cell)) {
      cell.getDateCellValue.toString
    } else {
      new DecimalFormat("#.####")
        .format(cell.getNumericCellValue)
    }
  }
}
