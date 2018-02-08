package system.vostok.tda.util.parser

import java.io.InputStream

import com.sun.xml.internal.stream.Entity
import org.apache.poi.ss.usermodel.WorkbookFactory
import system.vostok.tda.util.CellValueUtil
import system.vostok.tda.util.constants.ParserType._

import scala.collection.JavaConverters._
import scala.collection.mutable

class SimpleExcelParser extends ExcelParser {
  override val parserType: String = PLAIN_HEADER

  override def parse(file: InputStream, sheetIndex: Integer): mutable.ArrayBuilder[Entity] = {
    WorkbookFactory.create(file)
      .getSheetAt(sheetIndex)
      .asScala
      .map(_.asScala.map(CellValueUtil.getCellValue(_)))
      .asInstanceOf[mutable.ArrayBuilder[mutable.ArrayBuilder[String]]]
  }


  /*static List plainHeaderExcelToList(Object file, Integer sheetIndex) {
    WorkbookFactory.create(file)
      .getSheetAt(sheetIndex)
      .collect { it.collect(CellValueUtil.&getCellValue) }
  }
*/

}
