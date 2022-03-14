package io.github.toyota32k.viewex.library

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.widget.FrameLayout
import androidx.annotation.AttrRes
import androidx.annotation.StyleRes
import androidx.core.view.children
import kotlin.math.max
import kotlin.math.min

/**
 * WPF/XAML の Viewbox 的なビュー
 * 中身をよい感じに縮小・拡大して表示
 */
open class Viewbox
@JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, @AttrRes defStyleAttr: Int = 0, @StyleRes defStyleRes: Int = 0)
    : FrameLayoutEx(context, attrs, defStyleAttr, defStyleRes) {

//    var expandable:Boolean = false
//        set(v) {
//            if(field!=v) {
//                field = v
//                requestLayout()
//            }
//        }
    var expandable:Boolean by LiteOvservableProperty(false) { requestLayout() }

    init {
        val a = getContext().obtainStyledAttributes(attrs, R.styleable.Viewbox)
        expandable = a.getBoolean(R.styleable.Viewbox_expandable, false)
        a.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        measureChildren(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED))
    }

    @Suppress("RemoveRedundantQualifierName")
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        val width = right - left
        val height = bottom - top
        for (view in children) {
            var scale = if(expandable) Float.MAX_VALUE else 1f
            val lp = view.layoutParams as? FrameLayout.LayoutParams
            val mVert = lp?.run { leftMargin + rightMargin } ?: 0
            val mHorz = lp?.run { topMargin + bottomMargin } ?: 0
            val w = max((width - mHorz).toFloat(), 1f)
            val h = max((height - mVert).toFloat(), 1f)
            if (view.measuredWidth > w || expandable) {
                scale = min(scale, w / view.measuredWidth)
            }
            if (view.measuredHeight > h || expandable) {
                scale = min(scale, h / view.measuredHeight)
            }

            view.pivotX = 0f
            view.pivotY = 0f
            if(lp!=null && lp.gravity!=FrameLayout.LayoutParams.UNSPECIFIED_GRAVITY) {
                when (lp.gravity.and(Gravity.HORIZONTAL_GRAVITY_MASK)) {
                    Gravity.CENTER_HORIZONTAL -> view.pivotX = view.measuredWidth.toFloat() / 2f
                    Gravity.RIGHT -> view.pivotX = view.measuredWidth.toFloat()
                }
                when (lp.gravity.and(Gravity.VERTICAL_GRAVITY_MASK)) {
                    Gravity.CENTER_VERTICAL -> view.pivotY = view.measuredHeight.toFloat()/2f
                    Gravity.BOTTOM -> view.pivotY = view.measuredHeight.toFloat()
                }
            }
            view.scaleX = scale
            view.scaleY = scale
        }
        super.onLayout(changed, left, top, right, bottom)
    }
}