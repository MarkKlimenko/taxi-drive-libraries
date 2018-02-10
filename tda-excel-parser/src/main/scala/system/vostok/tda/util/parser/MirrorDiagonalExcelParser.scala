package system.vostok.tda.util.parser

import java.io.InputStream

import system.vostok.tda.domain.ParsedRow
import system.vostok.tda.util.IterableUtil
import system.vostok.tda.util.constants.ParserType._
import system.vostok.tda.util.constants.RowType.SIMPLE

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
      .foreach { case (row, rowIndex) =>

        if (rowIndex == 0) {
          header = row.content
        } else {
          val from = row.content.toList.head
          row.content
            .zipWithIndex
            .foreach { case (cell, cellIndex) =>
              if (cellIndex != 0) {
                result += new ParsedRow(
                  rowType = SIMPLE,
                  content = ArrayBuffer[String](from, header.toList(cellIndex - 1), cell))
              }
            }
        }
      }
    result
  }
}
