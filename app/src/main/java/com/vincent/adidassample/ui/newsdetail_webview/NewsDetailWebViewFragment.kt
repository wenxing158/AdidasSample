package com.vincent.adidassample.ui.newsdetail_webview

import android.graphics.Bitmap
import android.net.http.SslError
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.SslErrorHandler
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.vincent.adidassample.R
import com.vincent.adidassample.databinding.FragmentNewsDetailWebviewBinding
import com.vincent.adidassample.utils.AppConst
import kotlinx.android.synthetic.main.fragment_news_detail_webview.view.*

class NewsDetailWebViewFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = DataBindingUtil.inflate<FragmentNewsDetailWebviewBinding>(
            inflater,
            R.layout.fragment_news_detail_webview,
            null,
            false
        )
        val url = arguments?.getString(AppConst.ARG_DETAIL_WEBVIEW_URL)
        val root = rootView.root
        Log.i(javaClass.name, "url: " + url)
        val webView: WebView = root.webview_detail

        webView.settings.builtInZoomControls = false
        webView.settings.javaScriptEnabled = true
        webView.settings.blockNetworkImage = false
        webView.settings.javaScriptCanOpenWindowsAutomatically = true
        webView.settings.allowFileAccess = true
        webView.webViewClient = object : WebViewClient() {

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
            }

            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                url?.let { view!!.loadUrl(it) }
                return false
            }

            override fun onReceivedSslError(
                view: WebView?,
                handler: SslErrorHandler?,
                error: SslError?
            ) {
                handler!!.proceed()
            }
        }
        url?.let { webView.loadUrl(it) }
        return root
    }

}