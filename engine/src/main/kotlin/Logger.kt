@file:Suppress("ConstantConditionIf")

class Logger(site: Any) {

    private val prefix = site::class.simpleName

    fun debug(text: String) {
        if (BuildConfig.DEBUG) {
            println("$prefix: $text")
        }
    }

    fun info(text: String) {
        if (BuildConfig.INFO) {
            println("$prefix: $text")
        }
    }

    fun warn(text: String) {
        if (BuildConfig.WARN) {
            println("$prefix: $text")
        }
    }

    fun error(text: String) {
        if (BuildConfig.ERROR) {
            println("$prefix: $text")
        }
    }

    fun d(text: String) {
        debug(text)
    }

    fun i(text: String) {
        info(text)
    }

    fun w(text: String) {
        warn(text)
    }

    fun e(text: String) {
        error(text)
    }
}