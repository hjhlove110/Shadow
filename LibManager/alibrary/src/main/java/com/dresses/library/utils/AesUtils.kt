package com.dresses.library.utils

import android.util.Base64
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object AesUtils {
    /**
     * 算法/模式/填充
     */
    private const val CipherMode = "AES/CBC/PKCS5Padding"
    private const val key_ = "mGHldgjsudoyjvgk"
    private const val iv = "youlaikeadoghaha"

    /**
     * 创建密钥
     */
    private fun createKey(key: String): SecretKeySpec {
        var key: String? = key
        var data: ByteArray? = null
        if (key == null) {
            key = ""
        }
        val sb = StringBuffer(16)
        sb.append(key)
        while (sb.length < 16) {
            sb.insert(0, " ")
        }
        if (sb.length > 16) {
            sb.setLength(16)
        }
        try {
            data = sb.toString().toByteArray(Charset.forName("utf-8"))
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }
        return SecretKeySpec(data, "AES")
    }

    private fun createIV(password: String): IvParameterSpec {
        var password: String? = password
        var data: ByteArray? = null
        if (password == null) {
            password = ""
        }
        val sb = StringBuffer(16)
        sb.append(password)
        while (sb.length < 16) {
            sb.append("0")
        }
        if (sb.length > 16) {
            sb.setLength(16)
        }
        try {
            data = sb.toString().toByteArray()
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }
        return IvParameterSpec(data)
    }

    /**
     * 加密字节数据
     */
    private fun encrypt(content: ByteArray?): ByteArray? {
        try {
            val key = createKey(key_)
            val cipher = Cipher.getInstance(CipherMode)
            cipher.init(Cipher.ENCRYPT_MODE, key, createIV(iv))
            return cipher.doFinal(content)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * 加密(结果为16进制字符串)
     */
    fun encrypt(content: String): String {
        var data: ByteArray? = null
        try {
            data = content.toByteArray()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        data = encrypt(data)
        return Base64.encodeToString(data, Base64.NO_WRAP)
    }

    /**
     * 解密字节数组
     */
    fun decrypt(content: ByteArray?): ByteArray? {
        try {
            val key = createKey(key_)
            val cipher = Cipher.getInstance(CipherMode)
            cipher.init(Cipher.DECRYPT_MODE, key, createIV(iv))
            return cipher.doFinal(content)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * 解密(输出结果为字符串)
     */
    fun decrypt(content: String): String? {
        var data: ByteArray? = null
        try {
            data = hex2byte(content)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        data = decrypt(data)
        if (data == null) return null
        var result: String? = null
        try {
            result = String(data, Charset.forName("UTF-8"))
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }
        return result
    }

    /**
     * 字节数组转成16进制字符串
     */
    fun byte2hex(b: ByteArray?): String { // 一个字节的数，
        val sb = StringBuffer(b!!.size * 2)
        var tmp = ""
        for (n in b.indices) { // 整数转成十六进制表示
            tmp = Integer.toHexString(b[n].toInt() and 0XFF)
            if (tmp.length == 1) {
                sb.append("0$tmp")
            }
            sb.append(tmp)
        }
        return sb.toString().toUpperCase() // 转成大写
    }

    /**
     * 将hex字符串转换成字节数组
     */
    private fun hex2byte(inputString: String): ByteArray {
        var inputString: String? = inputString
        if (inputString == null || inputString.length < 2) {
            return ByteArray(0)
        }
        inputString = inputString.toLowerCase()
        val l = inputString.length / 2
        val result = ByteArray(l)
        for (i in 0 until l) {
            val tmp = inputString.substring(2 * i, 2 * i + 2)
            result[i] = (tmp.toInt(16) and 0xFF).toByte()
        }
        return result
    }
}