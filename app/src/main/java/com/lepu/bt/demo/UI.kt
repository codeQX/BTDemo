package com.lepu.bt.demo

import android.app.Activity
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar

/**
 * =================================================================================================
 * 默认描述
 *
 * Author: qixin
 * Date: 2021/6/30 下午 5:31
 * =================================================================================================
 */
internal fun Activity.showSnackbarShort(text: CharSequence) {
    Snackbar.make(findViewById(android.R.id.content), text, Snackbar.LENGTH_SHORT).show()
}

internal fun Activity.showSnackbarShort(@StringRes text: Int) {
    Snackbar.make(findViewById(android.R.id.content), text, Snackbar.LENGTH_SHORT).show()
}