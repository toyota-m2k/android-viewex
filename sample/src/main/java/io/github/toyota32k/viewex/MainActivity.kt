package io.github.toyota32k.viewex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.FrameLayout
import android.widget.Spinner
import androidx.core.view.children
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.github.toyota32k.bindit.Binder
import io.github.toyota32k.bindit.CheckBinding
import io.github.toyota32k.bindit.SliderBinding
import io.github.toyota32k.utils.disposableObserve
import io.github.toyota32k.utils.dp2px
import io.github.toyota32k.utils.setLayoutHeight
import io.github.toyota32k.utils.setLayoutWidth
import io.github.toyota32k.viewex.library.Viewbox
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {
    class MainViewModel: ViewModel() {
        val expandable= MutableLiveData(true)
        val width = MutableLiveData(200f)
        val height = MutableLiveData(50f)
    }

    enum class GravityParam(val id:Int, val horz:String, val horzValue:Int, val vert:String, val vertValue:Int) {
        START(0, "Left", Gravity.START,"Top", Gravity.TOP),
        CENTER(1, "Center", Gravity.CENTER_HORIZONTAL, "Center", Gravity.CENTER_VERTICAL),
        END(2, "Right", Gravity.END, "Bottom", Gravity.BOTTOM);

        companion object {
            val horzLabels:Array<String> get() = values().map { it.horz }.toTypedArray()
            val vertLabels:Array<String> get() = values().map { it.vert }.toTypedArray()
            fun valueOf(i:Int):GravityParam? {
                return values().singleOrNull { it.id == i }
            }
        }
    }

    private val binder = Binder()
    private lateinit var viewModel:MainViewModel

    private val viewboxes: Sequence<Viewbox>
        get() = findViewById<ViewGroup>(R.id.rootView).children.mapNotNull { it as? Viewbox }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val horzSpinner:Spinner = findViewById(R.id.horz_gravity)
        horzSpinner.adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, GravityParam.horzLabels)
        val vertSpinner:Spinner = findViewById(R.id.vert_gravity)
        vertSpinner.adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, GravityParam.vertLabels)
        horzSpinner.onItemSelectedListener = object:AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, pos: Int, p3: Long) {
                val p = GravityParam.valueOf(pos) ?: return
                viewboxes.forEach {
                    val child = it.getChildAt(0)
                    val lp = child.layoutParams as? FrameLayout.LayoutParams ?: return@forEach
                    val gravity = lp.gravity.and(Gravity.HORIZONTAL_GRAVITY_MASK.inv()).or(p.horzValue)
                    lp.gravity = gravity
                    child.layoutParams = lp
                    it.requestLayout()
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
        vertSpinner.onItemSelectedListener = object:AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, pos: Int, p3: Long) {
                val p = GravityParam.valueOf(pos) ?: return
                viewboxes.forEach {
                    val child = it.getChildAt(0)
                    val lp = child.layoutParams as? FrameLayout.LayoutParams ?: return@forEach
                    val gravity = lp.gravity.and(Gravity.VERTICAL_GRAVITY_MASK.inv()).or(p.vertValue)
                    lp.gravity = gravity
                    child.layoutParams = lp
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }



        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        binder.register(
            CheckBinding.create(this, findViewById(R.id.expandable_check), viewModel.expandable),
            viewModel.expandable.disposableObserve(this) {
                val expandable = it == true
                viewboxes.forEach { viewbox->
                    viewbox.expandable = expandable
                }
            },
            SliderBinding.create(this, findViewById(R.id.width_slider), viewModel.width),
            SliderBinding.create(this, findViewById(R.id.height_slider), viewModel.height),
            viewModel.width.disposableObserve(this) {
                val width = dp2px(it ?: 20f).roundToInt()
                viewboxes.forEach { viewbox->
                    viewbox.setLayoutWidth(width)
                }
            },
            viewModel.height.disposableObserve(this) {
                val height = dp2px(it ?: 10f).roundToInt()
                viewboxes.forEach { viewbox->
                    viewbox.setLayoutHeight(height)
                }
            },

        )
    }
}