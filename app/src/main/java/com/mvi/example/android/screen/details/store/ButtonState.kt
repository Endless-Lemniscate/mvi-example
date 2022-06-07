package com.mvi.example.android.screen.details.store

import androidx.annotation.StringRes
import com.mvi.example.android.R

enum class ButtonState(@StringRes val label: Int) {
    SAVE(R.string.save_material),
    CLOSE(R.string.exit),
    CREATE(R.string.create);
}