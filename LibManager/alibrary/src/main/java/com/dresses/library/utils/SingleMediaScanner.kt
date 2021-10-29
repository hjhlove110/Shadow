package com.dresses.library.utils

import android.content.Context
import android.media.MediaScannerConnection
import android.media.MediaScannerConnection.MediaScannerConnectionClient
import android.net.Uri
import java.io.File

/**
 * @author HUAN
 * @date
 */
class SingleMediaScanner(
    val context: Context
    , val mFile: File
    , val listener: ScanListener?
) : MediaScannerConnectionClient {

    private val mMs: MediaScannerConnection? by lazy {
        MediaScannerConnection(context.applicationContext, this)
    }

    fun scanFile(){
        mMs?.connect()
    }
    override fun onMediaScannerConnected() {
        mMs!!.scanFile(mFile.absolutePath,null)
    }

    override fun onScanCompleted(path: String?, uri: Uri?) {
        mMs!!.disconnect()
        listener?.onScanFinish()
    }

    interface ScanListener {
        fun onScanFinish()
    }
}