package com.jiujiu.task.util

import android.text.TextUtils

object PlaceValidator {

    fun isValid(text: String): Boolean {
        // todo: Validate whether the text is a address
        return !TextUtils.isEmpty(text)
    }
}