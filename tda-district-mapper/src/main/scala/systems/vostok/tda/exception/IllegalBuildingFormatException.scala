package systems.vostok.tda.exception

class IllegalBuildingFormatException(message: String) extends RuntimeException(message) {

  def this(building: String) {
    this(s"Illegal building number format '$building', " +
      "use one of the supported formats: '24', '24A', '24/5'")
  }
}
