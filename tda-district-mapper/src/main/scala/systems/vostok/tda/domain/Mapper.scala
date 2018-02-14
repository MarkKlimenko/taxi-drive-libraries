package systems.vostok.tda.domain

class Mapper(var streetId: String, var building: String, var buildingFrom: String, var buildingTo: String, var districtId: String) {

  def this(streetId: String, building: String, districtId: String) {
    this(streetId, building, null, null, districtId)
  }

  def canEqual(a: Any): Boolean = a.isInstanceOf[Mapper]

  override def equals(that: Any): Boolean = that match {
    case that: Mapper => that.canEqual(this) &&
      this.streetId == that.streetId &&
      this.building == that.building &&
      this.buildingFrom == that.buildingFrom &&
      this.buildingTo == that.buildingTo &&
      this.districtId == that.districtId
    case _ => false
  }
}
