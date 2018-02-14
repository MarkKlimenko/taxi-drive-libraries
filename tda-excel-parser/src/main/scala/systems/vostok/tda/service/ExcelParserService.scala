package systems.vostok.tda.service

import java.io.InputStream

import systems.vostok.tda.util.ParserFactory
import systems.vostok.tda.domain.ParsedRow
import systems.vostok.tda.util.ParserFactory

class ExcelParserService {
  def parseDocument(file: InputStream, sheetIndex: Integer, documentType: String): Iterable[ParsedRow] = {
    ParserFactory.getParser(documentType)
      .parse(file, sheetIndex)
  }
}
