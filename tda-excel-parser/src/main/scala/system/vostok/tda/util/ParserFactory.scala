package system.vostok.tda.util

import system.vostok.tda.util.parser.{ExcelParser, MirrorDiagonalExcelParser, SimpleExcelParser}
import system.vostok.tda.util.constants.ParserType._

object ParserFactory {
   def getParser(parserType: String): ExcelParser = {
     parserType match {
       case PLAIN_HEADER => new SimpleExcelParser
       case MIRROR_DIAGONAL => new MirrorDiagonalExcelParser
     }
  }
}
