package systems.vostok.tda.exception

class IllegalEntityIdFormatException extends RuntimeException {

    IllegalEntityIdFormatException(String id) {
        super("Illegal entity id format '${id}'")
    }
}
