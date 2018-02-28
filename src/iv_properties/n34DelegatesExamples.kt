package iv_properties

import util.TODO
import util.doc34
import kotlin.reflect.KProperty

class Delegate<out T : Any>(private val initializer: () -> T) {

    private var initialized = false
    private lateinit var value: T

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        if(!initialized) {
            value = initializer()
            initialized = true
        }

        return value
    }
}

class LazyPropertyUsingDelegates(val initializer: () -> Int) {
    val lazyValue: Int by Delegate(initializer)
}

fun todoTask34(): Lazy<Int> = TODO(
    """
        Task 34.
        Read about delegated properties and make the property lazy by using delegates.
    """,
    documentation = doc34()
)
