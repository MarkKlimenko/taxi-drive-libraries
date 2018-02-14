package systems.vostok.tda.util

import systems.vostok.tda.domain.ParsedRow
import systems.vostok.tda.domain.ParsedRow

class IterableUtil(val iterable: Iterable[ParsedRow]) {
  def getRidOfEmptyCells: Iterable[ParsedRow] = {
    iterable.filter(row => row.content != null)
      .map(row => getFilteredParsedRow(row))
      .filter(row => row != null)
  }

  def getFilteredParsedRow(parsedRow: ParsedRow): ParsedRow = {
    val rowContent = parsedRow.content
      .filter(cell => cell != null && cell != "")

    if (rowContent != null && rowContent.nonEmpty) {
      parsedRow.content = rowContent
      parsedRow
    } else {
      null
    }
  }
}
