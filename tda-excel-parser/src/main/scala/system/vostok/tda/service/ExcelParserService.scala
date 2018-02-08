package system.vostok.tda.service

import java.io.InputStream

import com.sun.xml.internal.stream.Entity
import system.vostok.tda.util.ParserFactory

import scala.collection.mutable

class ExcelParserService {
  def parseDocument(file: InputStream, sheetIndex: Integer, documentType: String): mutable.ArrayBuilder[Entity] = {
    ParserFactory.getParser(documentType)
      .parse(file, sheetIndex)
  }
}
