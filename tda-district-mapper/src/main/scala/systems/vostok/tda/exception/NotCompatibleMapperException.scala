package systems.vostok.tda.exception

class NotCompatibleMapperException(message: String) extends RuntimeException(message) {

  def this(targetStreet: String, streetInMapper: String) {
    this(s"Provided street '$streetInMapper' in mapper " +
      s"not compatible with target street '$targetStreet'")
  }
}
