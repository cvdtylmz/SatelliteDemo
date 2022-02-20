package com.cevdetyilmaz.satellite.view

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.cevdetyilmaz.satellite.common.hideKeyboard
import com.cevdetyilmaz.satellite.databinding.ViewSearchViewBinding

/**
 * Created by Cevdet Yılmaz on 19.02.2022
 */

class CustomSearchView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private var binding: ViewSearchViewBinding =
        ViewSearchViewBinding.inflate(LayoutInflater.from(this.context), this)
    private var searchTextWatcher: TextWatcher? = null


    fun setHint(hint: String) {
        binding.edtSearch.hint = hint
    }

    fun getSearchText() = binding.edtSearch.text.toString()

    fun isSearchEnable(text: String): Boolean {
        return text.length >= 3 || text.isEmpty()
    }

    fun setImageButtonListener(listener: () -> Unit) {
        binding.imgBtnSearch.setOnClickListener {
            listener.invoke()
        }
    }

    fun registerTextWatcher(makeSearch: (String) -> Unit) {
        searchTextWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                //Has no use
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //Has no use
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (isSearchEnable(s.toString())) {
                    makeSearch(s.toString())
                }
            }
        }
        binding.edtSearch.apply {
            addTextChangedListener(searchTextWatcher)
            setOnEditorActionListener { view, _, _ ->
                view.hideKeyboard()
                false
            }
        }
    }

    fun unregisterToSearchTextWatcher() {
        binding.edtSearch.removeTextChangedListener(searchTextWatcher)
        searchTextWatcher = null
    }
}