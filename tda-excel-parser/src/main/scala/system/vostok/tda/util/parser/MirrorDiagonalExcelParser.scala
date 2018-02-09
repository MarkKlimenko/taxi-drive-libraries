package system.vostok.tda.util.parser

import java.io.InputStream

import system.vostok.tda.domain.ParsedRow
import system.vostok.tda.util.constants.ParserType._

/**
  * Contain output in form
  * HEADER -> | header | ... | ... |
  * SIMPLE -> | value  | ... | ... |
  * SIMPLE -> |  ...   | ... | ... |
  */

class MirrorDiagonalExcelParser extends ExcelParser {
  override val parserType: String = MIRROR_DIAGONAL

  override def parse(file: InputStream, sheetIndex: Integer): Iterable[ParsedRow] = {
    new SimpleExcelParser().parse(file, sheetIndex)

  }



  /*static List mirrorDiagonalExcelToList(Object file, Integer sheetIndex) {
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
  }*/

}
