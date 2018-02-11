package systems.vostok.tda.exception

class IllegalBuildingFormatException extends RuntimeException {

    IllegalBuildingFormatException(String building) {
        super("Illegal building number format '${building}', " +
              "use one of the supported formats: '24', '24A', '24/5'")
    }
}
