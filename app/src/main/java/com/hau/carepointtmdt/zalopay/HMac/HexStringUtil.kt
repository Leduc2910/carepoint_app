package com.hau.carepointtmdt.zalopay.HMac


object HexStringUtil {
    private val HEX_CHAR_TABLE = byteArrayOf(
        '0'.code.toByte(), '1'.code.toByte(), '2'.code.toByte(), '3'.code.toByte(),
        '4'.code.toByte(), '5'.code.toByte(), '6'.code.toByte(), '7'.code.toByte(),
        '8'.code.toByte(), '9'.code.toByte(), 'a'.code.toByte(), 'b'.code.toByte(),
        'c'.code.toByte(), 'd'.code.toByte(), 'e'.code.toByte(), 'f'.code.toByte()
    )

    // @formatter:on
    /**
     * Convert a byte array to a hexadecimal string
     *
     * @param raw
     * A raw byte array
     *
     * @return Hexadecimal string
     */
    fun byteArrayToHexString(raw: ByteArray): String {
        val hex = ByteArray(2 * raw.size)
        var index = 0

        for (b in raw) {
            val v = b.toInt() and 0xFF
            hex[index++] = HEX_CHAR_TABLE[v ushr 4]
            hex[index++] = HEX_CHAR_TABLE[v and 0xF]
        }
        return String(hex)
    }

    /**
     * Convert a hexadecimal string to a byte array
     *
     * @param hex
     * A hexadecimal string
     *
     * @return The byte array
     */
    fun hexStringToByteArray(hex: String): ByteArray {
        val hexstandard = hex.lowercase()
        val sz = hexstandard.length / 2
        val bytesResult = ByteArray(sz)

        var idx = 0
        for (i in 0 until sz) {
            bytesResult[i] = hexstandard[idx].code.toByte()
            ++idx
            var tmp = hexstandard[idx].code.toByte()
            ++idx

            if (bytesResult[i] > HEX_CHAR_TABLE[9]) {
                bytesResult[i] = (bytesResult[i] - ('a'.code.toByte() - 10)).toByte()
            } else {
                bytesResult[i] = (bytesResult[i] - '0'.code.toByte()).toByte()
            }
            tmp = if (tmp > HEX_CHAR_TABLE[9]) {
                (tmp - ('a'.code.toByte() - 10)).toByte()
            } else {
                (tmp - '0'.code.toByte()).toByte()
            }

            bytesResult[i] = (bytesResult[i] * 16 + tmp).toByte()
        }
        return bytesResult
    }
}
