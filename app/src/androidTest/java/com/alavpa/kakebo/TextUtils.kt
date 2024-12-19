package com.alavpa.kakebo

import android.content.Context
import androidx.annotation.StringRes
import androidx.test.core.app.ApplicationProvider

object TextUtils {
    fun getText(@StringRes resId: Int): String {
        val targetContext: Context = ApplicationProvider.getApplicationContext()
        return targetContext.getString(resId)
    }
}
