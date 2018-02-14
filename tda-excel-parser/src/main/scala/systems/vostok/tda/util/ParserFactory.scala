package systems.vostok.tda.util

import systems.vostok.tda.util.parser.{MirrorDiagonalExcelParser, PlainHeaderExcelParser, SimpleTableExcelParser}
import systems.vostok.tda.util.constants.ParserType._
import systems.vostok.tda.util.parser.{ExcelParser, MirrorDiagonalExcelParser, PlainHeaderExcelParser, SimpleTableExcelParser}

object ParserFactory {
   def getParser(parserType: String): ExcelParser = {
     parserType match {
       case SIMPLE_TABLE => new SimpleTableExcelParser
       case PLAIN_HEADER => new PlainHeaderExcelParser
       case MIRROR_DIAGONAL => new MirrorDiagonalExcelParser
     }
  }
}
