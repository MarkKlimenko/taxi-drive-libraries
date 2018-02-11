package systems.vostok.tda.exception

class NotCompatibleMapperException extends RuntimeException {

    NotCompatibleMapperException(String targetStreet, String streetInMapper) {
        super("Provided street '${streetInMapper}' in mapper " +
                "not compatible with target street '${targetStreet}'")
    }
}
