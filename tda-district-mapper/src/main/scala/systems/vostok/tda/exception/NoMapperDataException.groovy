package systems.vostok.tda.exception

class NoMapperDataException extends RuntimeException {

    NoMapperDataException() {
        super('Provided mapper list is empty')
    }
}
