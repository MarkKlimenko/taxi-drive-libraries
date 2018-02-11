package systems.vostok.tda.exception

class NoTargetDistrictException extends RuntimeException {

    NoTargetDistrictException() {
        super('Can`t find target district for provided street')
    }
}
