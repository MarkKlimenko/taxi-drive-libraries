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
  * HEADER -> | header | ... | ... |
  * SIMPLE -> | value  | ... | ... |
  * SIMPLE -> |  ...   | ... | ... |
  */

class PlainHeaderExcelParser extends ExcelParser {
  override val parserType: String = PLAIN_HEADER

  override def parse(file: InputStream, sheetIndex: Integer): Iterable[ParsedRow] = {
    WorkbookFactory.create(file)
      .getSheetAt(sheetIndex)
      .asScala
      .zipWithIndex
      .map{ case(row, index) => composeParsedRow(row, index)}
  }

  def composeParsedRow(row: Row, index: Long): ParsedRow = new ParsedRow(
    rowType = if(index == 0) HEADER else SIMPLE,
    content = row.asScala.map(cell => CellValueUtil.getCellValue(cell))
  )
}
