package app.tier.utils

sealed class Resource<out T : Any> {
    class Success<out T : Any>(val data: T) : Resource<T>()
    class Error(val throwable: Throwable) : Resource<Nothing>()
}
