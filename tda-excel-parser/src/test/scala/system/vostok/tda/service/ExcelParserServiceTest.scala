package system.vostok.tda.service

import java.io.{File, FileInputStream}

import org.json4s._
import org.json4s.jackson.Serialization.write
import org.junit.jupiter.api.Assertions._
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

import scala.io.Source

object ExcelParserServiceTest {
  implicit val formats = DefaultFormats

  @ParameterizedTest
  @ValueSource(strings = Array("SIMPLE_TABLE", "PLAIN_HEADER", "MIRROR_DIAGONAL"))
  def plainHeaderExcelToListTest(parserType: String): Unit = {
    val xlsxFile: File = new File(s"src/test/resources/rates/$parserType.xlsx")
    val expectedResultFile: File = new File(s"src/test/resources/rates/$parserType.json")

    assertEquals(getExpectedJson(expectedResultFile), parseFileToJson(xlsxFile, parserType))
  }

  def parseFileToJson(xlsxFile: File, xlsxFileType: String): String = write(
    new ExcelParserService()
      .parseDocument(new FileInputStream(xlsxFile), 0, xlsxFileType)
  )

  def getExpectedJson(expectedResultFile: File): String = {
    Source.fromFile(expectedResultFile).getLines.mkString
  }
}