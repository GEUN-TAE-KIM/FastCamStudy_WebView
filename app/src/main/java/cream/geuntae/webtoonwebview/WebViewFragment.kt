package cream.geuntae.webtoonwebview

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.edit
import cream.geuntae.webtoonwebview.databinding.FragmentWebViewBinding

class WebViewFragment(private val position: Int, private val webViewUrl: String) : Fragment() {

    var listener: OnTabLayoutNameChanged? = null

    private lateinit var binding: FragmentWebViewBinding

    companion object {
        const val SHARED_PREFERENCE = "WEB_HISTORY"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentWebViewBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.webView.webViewClient =
            WebViewClient(binding.progressBar) { url ->

                //SharedPreferences 객체는 수정 가능한 객체가 아니므로, 데이터를 수정하려면 SharedPreferences.Editor 객체를 사용
                //수정된 데이터가 SharedPreferences 객체에 적용
                activity?.getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE)?.edit {
                    putString("tab$position", url)
                }
            }
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.loadUrl(webViewUrl)

        binding.backToLastButton.setOnClickListener {
            // 데이터 저장을 하는 것
            val sharedPreference =
                activity?.getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE)

            val url = sharedPreference?.getString("tab$position", "")

            if (url.isNullOrEmpty()) {
                Toast.makeText(context, "마지막 저장 시점이 없습니다.", Toast.LENGTH_SHORT).show()
            } else {
                binding.webView.loadUrl(url)
            }
        }

        binding.changeTabNameButton.setOnClickListener {
            val dialog = AlertDialog.Builder(context)
            val editText = EditText(context)

            dialog.setView(editText)
            dialog.setPositiveButton("저장") { _, _ ->
                activity?.getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE)?.edit {
                    putString("tab${position}_name", editText.text.toString())
                    listener?.nameChanged(position, editText.text.toString())
                }
            }
            dialog.setNegativeButton("취소") { dialogInterface, _ ->
                dialogInterface.cancel()
            }
            dialog.show()
        }
    }

    fun canGoBack(): Boolean {
        return binding.webView.canGoBack()
    }

    fun goBack() {
        binding.webView.goBack()
    }

}

interface OnTabLayoutNameChanged {
    fun nameChanged(position: Int, name: String)
}