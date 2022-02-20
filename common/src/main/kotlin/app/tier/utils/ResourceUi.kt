package app.tier.utils

sealed interface ResourceUi<out T> {
    object Loading : ResourceUi<Nothing>
    data class Success<T>(var data: T) : ResourceUi<T>
    data class Failure(val error: Throwable) : ResourceUi<Nothing>

    companion object {
        fun loading(): ResourceUi<Nothing> = Loading

        fun <T> success(data: T): ResourceUi<T> = Success(data)

        fun error(t: Throwable): ResourceUi<Nothing> = Failure(t)
    }
}
