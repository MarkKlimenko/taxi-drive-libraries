package system.vostok.tda.util.parser

import java.io.InputStream

import org.apache.poi.ss.usermodel.{Row, WorkbookFactory}
import system.vostok.tda.domain.ParsedRow
import system.vostok.tda.util.CellValueUtil
import system.vostok.tda.util.constants.ParserType._
import system.vostok.tda.util.constants.RowType._

import scala.collection.JavaConverters._

/**
  * Contain output in form
  * SIMPLE -> |value| ... | ... |
  * SIMPLE -> | ... | ... | ... |
  */

class SimpleTableExcelParser extends ExcelParser {
  override val parserType: String = SIMPLE_TABLE

  override def parse(file: InputStream, sheetIndex: Integer): Iterable[ParsedRow] = {
    WorkbookFactory.create(file)
      .getSheetAt(sheetIndex)
      .asScala
      .map(row => composeParsedRow(row))
  }

  def composeParsedRow(row: Row): ParsedRow = new ParsedRow(
    rowType = SIMPLE,
    content = row.asScala.map(cell => CellValueUtil.getCellValue(cell))
  )
}
