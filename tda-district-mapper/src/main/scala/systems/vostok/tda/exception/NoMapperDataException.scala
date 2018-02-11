package systems.vostok.tda.exception

class NoMapperDataException(message: String) extends RuntimeException(message) {

  def this() {
    this("Provided mapper list is empty")
  }
}
