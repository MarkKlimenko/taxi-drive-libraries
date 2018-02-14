package systems.vostok.tda.util.parser

import java.io.InputStream

import systems.vostok.tda.domain.ParsedRow
import systems.vostok.tda.util.IterableUtil
import systems.vostok.tda.util.constants.ParserType._
import systems.vostok.tda.util.constants.RowType.SIMPLE

import scala.collection.mutable.ArrayBuffer

/**
  * Contain output in form
  * SIMPLE -> | value | ... | ... |
  * SIMPLE -> | value | ... | ... |
  * SIMPLE -> |  ...  | ... | ... |
  */

class MirrorDiagonalExcelParser extends ExcelParser {
  override val parserType: String = MIRROR_DIAGONAL

  implicit def bindToToIterable(iterable: Iterable[ParsedRow]): IterableUtil = new IterableUtil(iterable)

  override def parse(file: InputStream, sheetIndex: Integer): Iterable[ParsedRow] = {
    var header = Iterable[String]()
    var result = ArrayBuffer[ParsedRow]()

    new SimpleTableExcelParser()
      .parse(file, sheetIndex)
      .getRidOfEmptyCells
      .zipWithIndex
      .foreach { case (row, rowIndex) => processRow(row, rowIndex) }

    def processRow(row: ParsedRow, rowIndex: Long): Unit = {
      if (rowIndex == 0) {
        header = row.content
      } else {
        row.content
          .zipWithIndex
          .foreach { case (cell, cellIndex) =>
            if (cellIndex != 0) {
              result += new ParsedRow(
                rowType = SIMPLE,
                content = ArrayBuffer[String](row.content.toList.head, header.toList(cellIndex - 1), cell))
            }
          }
      }
    }

    result
  }
}
