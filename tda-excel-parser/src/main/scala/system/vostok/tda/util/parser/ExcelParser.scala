package system.vostok.tda.util.parser

import java.io.InputStream

import com.sun.xml.internal.stream.Entity

import scala.collection.mutable

trait ExcelParser {
  val parserType: String

  def parse(file: InputStream, sheetIndex: Integer): mutable.ArrayBuilder[Entity]
}
