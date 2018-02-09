package system.vostok.tda.util

import system.vostok.tda.util.parser.{ExcelParser, MirrorDiagonalExcelParser, PlainHeaderExcelParser, SimpleTableExcelParser}
import system.vostok.tda.util.constants.ParserType._

object ParserFactory {
   def getParser(parserType: String): ExcelParser = {
     parserType match {
       case SIMPLE_TABLE => new SimpleTableExcelParser
       case PLAIN_HEADER => new PlainHeaderExcelParser
       case MIRROR_DIAGONAL => new MirrorDiagonalExcelParser
     }
  }
}
