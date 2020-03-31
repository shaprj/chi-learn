package com.shaprj.chi.learn.pdfviewer

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

/**
 * Handles the opening of PDF files, using whatever app the user has installed to view PDFs
 * If no PDF app installed, shows a warning and directs them to appropriate Store listing
 */
class PdfViewer(private val context: Context) {

    private val isPdfAppAvailable: Boolean
        get() {
            val packageManager = context.packageManager
            val testIntent = Intent(Intent.ACTION_VIEW)
            testIntent.type = "application/pdf"
            val list = packageManager.queryIntentActivities(testIntent, PackageManager.MATCH_DEFAULT_ONLY)
            return list.size > 0
        }

    private val buttonListener: DialogInterface.OnClickListener
        get() = DialogInterface.OnClickListener { dialogInterface, i -> goToGooglePlayStoreEntry() }

    private val appListingIntent: Intent
        get() {
            val intent = Intent(Intent.ACTION_VIEW)
            val pdfAppPackageName = "com.adobe.reader"
            intent.data = Uri.parse("market://details?id=$pdfAppPackageName")
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            return intent
        }

    fun openPdf(filename: String) {
        if (isPdfAppAvailable) {
            copyPdfAndOpenIt(filename)
        } else {
            showPdfWarning()
        }
    }

    private fun copyPdfAndOpenIt(filename: String) {
        try {
            val file = copyPdfFromAssetsToStorage(filename)
            startPdfIntent(file)
        } catch (e: Exception) {
            Log.e("PdfHandler", "Error handling the PDF file", e)
        }

    }

    @Throws(Exception::class)
    private fun copyPdfFromAssetsToStorage(filename: String): File {
        val tempFilename = "temp.pdf"
        val `is` = context.assets
        val inputStream = `is`.open(filename)
        val outFilename = context.filesDir.toString() + "/" + tempFilename
        val outputStream = context.openFileOutput(tempFilename, Context.MODE_WORLD_READABLE)
        copy(inputStream, outputStream)
        outputStream.flush()
        outputStream.close()
        inputStream.close()
        return File(outFilename)
    }

    @Throws(IOException::class)
    private fun copy(fis: InputStream, fos: FileOutputStream) {
        val b = ByteArray(8)
        var i: Int
        while ((fis.read(b).apply { i = this }) != -1) {
            fos.write(b, 0, i)
        }
    }

    private fun startPdfIntent(file: File) {
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        val uri = Uri.fromFile(file)
        intent.setDataAndType(uri, "application/pdf")
        context.startActivity(intent)
    }

    private fun showPdfWarning() {
        val builder = AlertDialog.Builder(context)
        builder.setMessage("Please install an app to view PDF file")
        builder.setCancelable(false)
        builder.setPositiveButton("Install", buttonListener)
        builder.setNegativeButton("Cancel", null)
        builder.setTitle("PDF Viewer")
        val alert = builder.create()
        alert.show()
    }

    private fun goToGooglePlayStoreEntry() {
        context.startActivity(appListingIntent)
    }

}
