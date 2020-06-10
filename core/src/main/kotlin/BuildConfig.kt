object BuildConfig {
    private val LOG_LEVEL = LogLevel.DEBUG

    val DEBUG = LOG_LEVEL.value <= LogLevel.DEBUG.value
    val INFO = LOG_LEVEL.value <= LogLevel.INFO.value
    val WARN = LOG_LEVEL.value <= LogLevel.WARN.value
    val ERROR = LOG_LEVEL.value <= LogLevel.ERROR.value
}

private enum class LogLevel(val value: Int) { DEBUG(1), INFO(2), WARN(3), ERROR(4) }
