package app.tier.map.base

import app.tier.utils.Dispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher

class DispatcherImplTest : Dispatcher {
    override fun main(): CoroutineDispatcher = TestCoroutineDispatcher()

    override fun io(): CoroutineDispatcher = TestCoroutineDispatcher()

    override fun default(): CoroutineDispatcher = TestCoroutineDispatcher()
}
