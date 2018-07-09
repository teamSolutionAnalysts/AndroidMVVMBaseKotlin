package com.sa.baseproject.utils

/**
 * Created by altafhussain.shaikh on 4/21/2016.
 */

import android.text.TextUtils
import android.widget.EditText
import java.io.UnsupportedEncodingException
import java.net.URLDecoder
import java.net.URLEncoder
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*
import kotlin.experimental.and

/**
 * Utilities for working with strings like splitting, joining, url-encoding, hex, and digests.
 *
 * @author markus
 */
object StringUtils {
    private val HEX_CHARS = charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F')

    /**
     * Splits a String based on a single character, which is usually faster than regex-based String.split().
     * NOTE: split("AA;BB;;", ';') == ["AA", "BB", "", ""], this may be different from String.split()
     */
    fun split(string: String, delimiter: Char): Array<String?> {
        var delimeterCount = 0
        var start = 0
        var end = string.indexOf(delimiter, start)
        while (end != -1) {
            delimeterCount++
        }

        val result = arrayOfNulls<String>(delimeterCount + 1)
        start = 0
        for (i in 0 until delimeterCount) {
            end = string.indexOf(delimiter, start)
            result[i] = string.substring(start, end)
            start = end + 1
        }
        result[delimeterCount] = string.substring(start, string.length)
        return result
    }

    /**
     * URL-Encodes a given string using UTF-8 (some web pages have problems with UTF-8 and umlauts, consider
     * [.encodeUrlIso] also). No UnsupportedEncodingException to handle as it is dealt with in this
     * method.
     */
    fun encodeUrl(stringToEncode: String): String {
        try {
            return URLEncoder.encode(stringToEncode, "UTF-8")
        } catch (e1: UnsupportedEncodingException) {
            throw RuntimeException(e1)
        }

    }

    /**
     * URL-encodes a given string using ISO-8859-1, which may work better with web pages and umlauts compared to UTF-8.
     * No UnsupportedEncodingException to handle as it is dealt with in this method.
     */
    fun encodeUrlIso(stringToEncode: String): String {
        try {
            return URLEncoder.encode(stringToEncode, "ISO-8859-1")
        } catch (e1: UnsupportedEncodingException) {
            throw RuntimeException(e1)
        }

    }

    /**
     * URL-Decodes a given string using UTF-8. No UnsupportedEncodingException to handle as it is dealt with in this
     * method.
     */
    fun decodeUrl(stringToDecode: String): String {
        try {
            return URLDecoder.decode(stringToDecode, "UTF-8")
        } catch (e1: UnsupportedEncodingException) {
            throw RuntimeException(e1)
        }

    }

    /**
     * URL-Decodes a given string using ISO-8859-1. No UnsupportedEncodingException to handle as it is dealt with in
     * this method.
     */
    fun decodeUrlIso(stringToDecode: String): String {
        try {
            return URLDecoder.decode(stringToDecode, "ISO-8859-1")
        } catch (e1: UnsupportedEncodingException) {
            throw RuntimeException(e1)
        }

    }

    /**
     * Generates the MD5 digest (32 hex characters) for a given String based on UTF-8.
     */
    fun md5(stringToEncode: String): String {
        return digest(stringToEncode, "MD5", "UTF-8")
    }

    /**
     * Generates the SHA-1 digest (40 hex characters) for a given String based on UTF-8.
     * The SHA-1 algorithm produces less collisions than MD5.
     *
     * @return SHA-1 digest .
     */
    fun sha1(stringToEncode: String): String {
        return digest(stringToEncode, "SHA-1", "UTF-8")
    }

    /**
     * Generates a digest (hex string) for the given string
     */
    fun digest(string: String, digestAlgo: String, encoding: String): String {
        try {
            val digester = MessageDigest.getInstance(digestAlgo)
            digester.update(string.toByteArray(charset(encoding)))
            return hex(digester.digest())
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException(e)
        } catch (e: UnsupportedEncodingException) {
            throw RuntimeException(e)
        }

    }

    fun hex(bytes: ByteArray): String {
        val hexChars = CharArray(bytes.size * 2)
        for (i in bytes.indices) {
            val value = (bytes[i] and 0xFF.toByte()).toInt()
            hexChars[i * 2] = HEX_CHARS[value.ushr(4)]
            hexChars[i * 2 + 1] = HEX_CHARS[(value and 0x0F)]
        }
        return String(hexChars)
    }

    /**
     * @throws IllegalArgumentException if the given string is invalid hex
     */
    fun parseHex(hex: String): ByteArray {
        val length = hex.length
        if (length % 2 != 0) {
            throw IllegalArgumentException("Illegal string length: " + length)
        }
        val bytesLength = length / 2
        val bytes = ByteArray(bytesLength)
        var idxChar = 0
        for (i in 0..bytesLength - 1) {
            var value = parseHexDigit(hex[idxChar++]) shl 4
            value = value or parseHexDigit(hex[idxChar++])
            bytes[i] = value.toByte()
        }
        return bytes
    }

    /**
     * @throws IllegalArgumentException if the given char is invalid hex
     */
    fun parseHexDigit(c: Char): Int {
        if ('0' <= c && c <= '9') {
            return c - '0'
        } else if ('A' <= c && c <= 'F') {
            return c - 'A' + 10
        } else if ('a' <= c && c <= 'f') {
            return c - 'a' + 10
        }
        throw IllegalArgumentException("Illegal hex digit: " + c)
    }

    /**
     * Simple HTML/XML entity resolving: Only supports unicode enitities and a very limited number text represented
     * entities (apos, quot, gt, lt, and amp). There are many more: http://www.w3.org/TR/REC-html40/sgml/dtd.html
     *
     * @param entity The entity name without & and ; (null throws NPE)
     * @return Resolved entity or the entity itself if it could not be resolved.
     */
    fun resolveEntity(entity: String): String {
        return if (entity.length > 1 && entity[0] == '#') {
            if (entity[1] == 'x') {
                Integer.parseInt(entity.substring(2), 16).toChar().toString()
            } else {
                Integer.parseInt(entity.substring(1)).toChar().toString()
            }
        } else if (entity == "apos") {
            "'"
        } else if (entity == "quot") {
            "\""
        } else if (entity == "gt") {
            ">"
        } else if (entity == "lt") {
            "<"
        } else if (entity == "amp") {
            "&"
        } else {
            entity
        }
    }

    /**
     * Cuts the string at the end if it's longer than maxLength and appends the given end string to it. The length of
     * the resulting string is always less or equal to the given maxLength. It's valid to pass a null text; in this
     * case null is returned.
     */
    @JvmOverloads
    fun ellipsize(text: String?, maxLength: Int, end: String = "..."): String? {
        return if (text != null && text.length > maxLength) {
            text.substring(0, maxLength - end.length) + end
        } else text
    }

    fun splitLines(text: String, skipEmptyLines: Boolean): Array<String> {
        return if (skipEmptyLines) {
            text.split("[\n\r]+".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
        } else {
            text.split("\\r?\\n".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
        }
    }

    fun findLinesContaining(text: String, searchText: String): List<String> {
        val splitLinesSkipEmpty = splitLines(text, true)
        val matching = ArrayList<String>()
        for (line in splitLinesSkipEmpty) {
            if (line.contains(searchText)) {
                matching.add(line)
            }
        }
        return matching
    }

    /**
     * Joins the given iterable objects using the given separator into a single string.
     *
     * @return the joined string or an empty string if iterable is null
     */
    fun join(iterable: Iterable<*>?, separator: String): String {
        if (iterable != null) {
            val buf = StringBuilder()
            val it = iterable.iterator()
            val singleChar : Int = if (separator.length == 1) separator[0].toInt() else 0
            while (it.hasNext()) {
                buf.append(it.next())
                if (it.hasNext()) {
                    if (singleChar != 0) {
                        // More efficient
                        buf.append(singleChar)
                    } else {
                        buf.append(separator)
                    }
                }
            }
            return buf.toString()
        } else {
            return ""
        }
    }

    /**
     * Joins the given ints using the given separator into a single string.
     *
     * @return the joined string or an empty string if the int array is null
     */
    fun join(array: IntArray?, separator: String): String {
        if (array != null) {
            val buf = StringBuilder(Math.max(16, (separator.length + 1) * array.size))
            val singleChar : Int = if (separator.length == 1) separator[0].toInt() else 0
            for (i in array.indices) {
                if (i != 0) {
                    if (singleChar.toInt() != 0) {
                        // More efficient
                        buf.append(singleChar)
                    } else {
                        buf.append(separator)
                    }
                }
                buf.append(array[i])
            }
            return buf.toString()
        } else {
            return ""
        }
    }

    /**
     * Joins the given Strings using the given separator into a single string.
     *
     * @return the joined string or an empty string if the String array is null
     */
    fun join(array: Array<String>?, separator: String): String {
        if (array != null) {
            val buf = StringBuilder(Math.max(16, (separator.length + 1) * array.size))
            val singleChar = if (separator.length == 1) separator[0].toInt() else 0
            for (i in array.indices) {
                if (i != 0) {
                    if (singleChar != 0) {
                        // More efficient
                        buf.append(singleChar)
                    } else {
                        buf.append(separator)
                    }
                }
                buf.append(array[i])
            }
            return buf.toString()
        } else {
            return ""
        }
    }

    //check for the null and is view(EditText, TextView) are empty or not
    fun isEmpty(data: EditText?): Boolean {

        return !(data != null && !TextUtils.isEmpty(data.text.toString().trim { it <= ' ' }))
    }
}
/**
 * Cuts the string at the end if it's longer than maxLength and appends "..." to it. The length of the resulting
 * string including "..." is always less or equal to the given maxLength. It's valid to pass a null text; in this
 * case null is returned.
 */