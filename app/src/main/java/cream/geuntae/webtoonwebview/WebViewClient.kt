package cream.geuntae.webtoonwebview

import android.graphics.Bitmap
import android.view.View
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.core.view.isVisible

class WebViewClient(
    private val progressBar: ProgressBar,
    private val saveData: (String) -> Unit,
) : WebViewClient() {

    // 웹뷰를 true면 안 보여줄지 하는거
    // 한마디로 해당 값에서 다른페이지를 못가게 막는거
    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {

        if (request != null && request.url.toString().contains("")) {
            saveData(request.url.toString())
        }
        return super.shouldOverrideUrlLoading(view, request)
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)

        // 둘중 하나로 설정 뷰를 보여주기 싫을 때
        progressBar.visibility = View.GONE
        // progressBar.isVisible = false
    }

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)

        progressBar.visibility = View.VISIBLE
    }


}