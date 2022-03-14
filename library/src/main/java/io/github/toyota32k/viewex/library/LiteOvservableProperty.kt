package io.github.toyota32k.viewex.library

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty


class LiteOvservableProperty<T>(var value:T, val onChanged:()->Unit): ReadWriteProperty<Any, T> {

    override fun getValue(thisRef: Any, property: KProperty<*>): T {
        return value
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        if(this.value!=value) {
            this.value = value
            onChanged()
        }
    }

}