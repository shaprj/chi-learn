package com.shaprj.chi.learn.webview

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.webkit.WebView
import android.webkit.WebViewClient
import learnwd.shaprj.ru.learnwd.R

class MainActivityWebViewPdf : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.not_used_activity_main)
        val webView = findViewById(R.id.webView1) as WebView
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }
        }
        webView.settings.javaScriptEnabled = true
        webView.clearCache(true)

        webView.settings.useWideViewPort = true
        webView.setInitialScale(1)
        webView.settings.setSupportMultipleWindows(true)

        webView.setDownloadListener { url, userAgent, contentDisposition, mimetype, contentLength ->
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }

        val pdf = "http://www.adobe.com/devnet/acrobat/pdfs/pdf_open_parameters.pdf"
        webView.loadUrl("http://drive.google.com/viewerng/viewer?embedded=true&url=$pdf")
        //        PdfViewer pdf = new PdfViewer(this);
        //        pdf.openPdf("assets//HSK-1_480x800_s_ToGo.pdf");
    }
}
