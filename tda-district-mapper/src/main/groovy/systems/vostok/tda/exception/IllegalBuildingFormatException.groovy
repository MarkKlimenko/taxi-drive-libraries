package systems.vostok.tda.exception

class IllegalBuildingFormatException extends RuntimeException {

    IllegalBuildingFormatException(String message) {
        super("Illegal building number format '${message}', " +
              "use one of the supported formats: '24', '24A', '24/5'")
    }
}
