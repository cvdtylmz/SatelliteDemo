package com.cevdetyilmaz.satellite.common

import android.content.Context
import android.os.Build
import android.text.Html
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.core.text.HtmlCompat

fun TextView.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

@SuppressWarnings("deprecation")
fun TextView.partialBold(boldString: String, regularText: String?) {
    this.text =
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            Html.fromHtml(
                "<b>".plus(boldString)
                    .plus("</b>".plus(regularText))
            )
        } else {
            Html.fromHtml(
                "<b>".plus(boldString).plus("</b>".plus(regularText)),
                HtmlCompat.FROM_HTML_MODE_LEGACY
            )
        }
}