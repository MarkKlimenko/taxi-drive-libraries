package system.vostok.tda.util.parser

import java.io.InputStream

import com.sun.xml.internal.stream.Entity
import system.vostok.tda.util.constants.ParserType._

import scala.collection.mutable

class MirrorDiagonalExcelParser extends SimpleExcelParser {
  override val parserType: String = MIRROR_DIAGONAL

  override def parse(file: InputStream, sheetIndex: Integer): mutable.ArrayBuilder[Entity] = ???


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
