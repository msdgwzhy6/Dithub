package com.di.dithub.feature.repo

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.di.dithub.R
import com.di.dithub.base.BaseFragment
import com.di.dithub.extensions.gone
import com.di.dithub.extensions.visible
import kotlinx.android.synthetic.main.fragment_repo_detail.*

class RepoDetailFragment : BaseFragment() {

    override fun layout(): Int = R.layout.fragment_repo_detail

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val url = arguments?.getString("URL")
        webView.apply {
            settings.javaScriptEnabled = true
            webChromeClient = object : WebChromeClient() {
                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                    if (newProgress == 100) {
                        loadingProgress.gone()
                    } else {
                        loadingProgress.visible()
                        loadingProgress.progress = newProgress
                    }
                    super.onProgressChanged(view, newProgress)
                }
            }
            loadUrl(url)
        }

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (webView.canGoBack()) webView.goBack()
                else findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }
}