package systems.vostok.tda.exception

class NoTargetDistrictException(message: String) extends RuntimeException(message) {

  def this() {
    this("Can`t find target district for provided street")
  }
}
