package systems.vostok.tda.exception

class IllegalEntityIdFormatException(message: String) extends RuntimeException(message) {

  def this(id: String) {
    this(s"Illegal entity id format '$id'")
  }
}
