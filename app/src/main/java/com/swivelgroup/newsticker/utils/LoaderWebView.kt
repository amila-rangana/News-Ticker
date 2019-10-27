package com.swivelgroup.newsticker.utils

import android.view.KeyEvent
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar

class LoaderWebView() : WebViewClient() {

    lateinit var progressBar: ProgressBar
    lateinit var url: String

    constructor(progressBar: ProgressBar, url:String) : this() {
        this.progressBar = progressBar
        this.url = url
        progressBar.visibility = View.VISIBLE
    }

    override fun shouldOverrideKeyEvent(view: WebView?, event: KeyEvent?): Boolean {
        view?.loadData(url,"text/html", null)
        return true
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        progressBar.visibility = View.GONE
    }
}