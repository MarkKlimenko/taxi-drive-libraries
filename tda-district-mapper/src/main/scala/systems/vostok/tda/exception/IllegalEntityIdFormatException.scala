package systems.vostok.tda.exception

class IllegalEntityIdFormatException(id: String, message: String) extends RuntimeException(message) {

  def this(id: String) {
    this(id, s"Illegal entity id format '$id'")
  }
}
