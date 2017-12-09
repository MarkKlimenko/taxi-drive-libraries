package systems.vostok.tda.util

class DataHelper {

    static String extractFirstDigits(String data) {
        data.find(/^\d+/)
    }

    static List cloneMapping(List mapping) {
        mapping.collect { it.clone() }
    }
}
