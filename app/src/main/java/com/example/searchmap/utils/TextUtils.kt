package com.example.searchmap.utils

import android.graphics.Color
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import java.text.Normalizer
import java.util.Locale


// Xử lý nổi bật từ  tìm kiếm
fun highlightQuery(fullText: String, query: String): SpannableString {
    val spannable = SpannableString(fullText)

    val normalizedText = removeDiacritics(fullText).lowercase(Locale.getDefault())
    val normalizedQuery = removeDiacritics(query).lowercase(Locale.getDefault())

    var startIndex = normalizedText.indexOf(normalizedQuery)
    while (startIndex >= 0) {
        val endIndex = startIndex + normalizedQuery.length

        spannable.setSpan(
            StyleSpan(android.graphics.Typeface.BOLD),
            startIndex,
            endIndex,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannable.setSpan(
            ForegroundColorSpan(Color.BLACK),
            startIndex,
            endIndex,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        startIndex = normalizedText.indexOf(normalizedQuery, endIndex)
    }

    return spannable
}


fun removeDiacritics(str: String): String {
    val normalized = Normalizer.normalize(str, Normalizer.Form.NFD)
    return normalized.replace(Regex("\\p{InCombiningDiacriticalMarks}+"), "")
}
