package systems.vostok.tda.domain

class Mapper(var streetId: String, var building: String, var buildingFrom: String, var buildingTo: String, var districtId: String) {

  def this(streetId: String, building: String, districtId: String) {
    this(streetId, building, null, null, districtId)
  }
}
