package systems.vostok.tda.util.parser

import java.io.InputStream

import systems.vostok.tda.domain.ParsedRow

trait ExcelParser {
  val parserType: String

  def parse(file: InputStream, sheetIndex: Integer): Iterable[ParsedRow]
}
