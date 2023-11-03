package com.sipalingandroid.pbiexam.common

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class InstructionItem(
    @StringRes val text: Int,
    @DrawableRes val imageResource: Int,
)
