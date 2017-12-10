package systems.vostok.tda.util

class DataHelper {

    static String extractFirstDigits(String data) {
        data.find(/^\d+/)
    }

    static String extractLetterLiteral(String data) {
        if (!data.matches(/.*[А-Я]/)) {
            return null
        } else {
            data.find(/[А-Я]+/)
        }
    }

    static String extractNumericLiteral(String data) {
        if (!data.matches(/.\d*\/.\d*/)) {
            return null
        } else {
            data.find(/\/.\d*/).replace('/', '')
        }
    }

    static List cloneMapping(List mapping) {
        mapping.collect { it.clone() }
    }
}
