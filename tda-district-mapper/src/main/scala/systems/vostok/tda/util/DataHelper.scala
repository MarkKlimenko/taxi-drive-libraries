package systems.vostok.tda.util

object DataHelper {

  def extractFirstDigits(data: String): String = {
    "^\\d+".r.findFirstMatchIn(data).get.toString
  }

  def extractLetterLiteral(data: String): String = {
    if (data.matches(".*[А - Я]")) {
      return "[А - Я]+".r.findFirstMatchIn(data).get.toString
    }
    null
  }

  def extractNumericLiteral(data: String): String = {
    if (data.matches(".\\d*\\/.\\d*")) {
      return "\\/.\\d*".r.findFirstMatchIn(data).get.toString.replace("/", "")
    }
    null
  }

  /*
    static List cloneMapping(List mapping) {
      mapping.collect {
        it.clone()
      }
    }*/
}
