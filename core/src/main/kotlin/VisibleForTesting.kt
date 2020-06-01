
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
@MustBeDocumented
annotation class VisibleForTesting(val otherwise: Int) {
    companion object {
        const val PRIVATE = 0
        const val PROTECTED = 1
        const val INTERNAL = 2
        const val NONE = 3
    }
}
