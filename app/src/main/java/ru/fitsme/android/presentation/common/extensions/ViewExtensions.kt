package ru.fitsme.android.presentation.common.extensions

import android.view.View
import android.widget.EditText

fun EditText.isEmpty() = text.toString().isEmpty()

fun View.visible() { visibility = View.VISIBLE }

fun View.invisible() { visibility = View.INVISIBLE }

fun View.gone() { visibility = View.GONE }
