package system.vostok.tda.service

import java.io.InputStream

import system.vostok.tda.domain.ParsedRow
import system.vostok.tda.util.ParserFactory

class ExcelParserService {
  def parseDocument(file: InputStream, sheetIndex: Integer, documentType: String): Iterable[ParsedRow] = {
    ParserFactory.getParser(documentType)
      .parse(file, sheetIndex)
  }
}
