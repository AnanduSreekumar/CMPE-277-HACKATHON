package com.rfid.hack277.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.ViewGroup
import android.view.WindowManager
import android.webkit.JavascriptInterface
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.window.OnBackInvokedDispatcher
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat.startActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.rfid.hack277.databinding.ActivityMainBinding

@Composable
fun ChatGpt(
    context: Context
) {
    val userAgent =
        "Mozilla/5.0 (Linux; Android 10) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/112.0.5615.135 Mobile Safari/537.36"
    val chatUrl = "https://chat.openai.com/c/bb33383e-156d-4693-a4fa-1a38069192aa"
    lateinit var webView: WebView

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            WebView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                this.settings.userAgentString = userAgent
                this.settings.domStorageEnabled = true
                this.settings.javaScriptEnabled = true

                this.webViewClient = object : WebViewClient() {
                    override fun shouldOverrideUrlLoading(
                        view: WebView?,
                        request: WebResourceRequest?
                    ): Boolean {
                        val url = request?.url ?: return false

                        if (url.toString().contains(chatUrl)) {
                            return false
                        }

                        // Handle other URL loading logic here if needed

                        return false
                    }
                }

                loadUrl(chatUrl)
                webView = this
            }
        }, update = {
            webView = it
        }
    )
}
