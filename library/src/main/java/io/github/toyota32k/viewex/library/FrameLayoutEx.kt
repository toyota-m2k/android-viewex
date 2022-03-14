package io.github.toyota32k.viewex.library

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.annotation.AttrRes
import androidx.annotation.StyleRes
import kotlin.math.min

/**
 * maxWidth/maxHeightプロパティを指定可能な FrameLayout
 */
open class FrameLayoutEx
@JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, @AttrRes defStyleAttr: Int = 0, @StyleRes defStyleRes: Int = 0)
    : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    var maxWidth:Int = Int.MAX_VALUE
        set(v) {
            if(field!=v) {
                field = v
                requestLayout()
            }
        }
    var maxHeight:Int = Int.MAX_VALUE
        set(v) {
            if(field!=v) {
                field = v
                requestLayout()
            }
        }

    init {
        val a = getContext().obtainStyledAttributes(attrs, R.styleable.FrameLayoutEx)
        maxWidth = a.getDimensionPixelSize(R.styleable.FrameLayoutEx_maxWidth, Int.MAX_VALUE)
        maxHeight = a.getDimensionPixelSize(R.styleable.FrameLayoutEx_maxHeight, Int.MAX_VALUE)
        a.recycle()
    }

    private fun makeMeasureSpec(spec:Int, max:Int) :Int {
        val mode = MeasureSpec.getMode(spec)
        val size = MeasureSpec.getSize(spec)
        return when(mode) {
            MeasureSpec.EXACTLY -> spec     // 幅が明示的に指定されている場合は、mim/maxより優先しておこう。
            else -> MeasureSpec.makeMeasureSpec(min(size, max), MeasureSpec.AT_MOST)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val modifiedWidthMeasureSpec = makeMeasureSpec(widthMeasureSpec, maxWidth)
        val modifiedHeightMeasureSpec:Int = makeMeasureSpec(heightMeasureSpec, maxHeight)
        if(modifiedWidthMeasureSpec == widthMeasureSpec && modifiedHeightMeasureSpec == heightMeasureSpec) {
            return super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        } else {
            measure(modifiedWidthMeasureSpec, modifiedHeightMeasureSpec)
            setMeasuredDimension(measuredWidth, measuredHeight)
        }
    }
}