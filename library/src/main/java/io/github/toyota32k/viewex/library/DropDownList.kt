package io.github.toyota32k.viewex.library

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputLayout

class DropDownList
@JvmOverloads
constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : FrameLayout(context, attrs, defStyleAttr) {
    val outline: TextInputLayout
    val input: MaterialAutoCompleteTextView
    init {
        outline = TextInputLayout(context, null, com.google.android.material.R.style.Widget_MaterialComponents_TextInputLayout_OutlinedBox_Dense_ExposedDropdownMenu)
        input = MaterialAutoCompleteTextView(context)
    }
}