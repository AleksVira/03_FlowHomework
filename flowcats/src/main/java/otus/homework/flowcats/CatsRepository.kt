package otus.homework.flowcats

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

sealed class Result {
    data class Success<T>(val data: T) : Result()
    data class Error(val throwable: Throwable) : Result()
}

class CatsRepository(
    private val catsService: CatsService,
    private val refreshIntervalMs: Long = 5000
) {

    fun listenForCatFacts() = flow {
        while (true) {
            try {
                val fact = catsService.getCatFact()
                emit(Result.Success<Fact>(fact))
            } catch (e: Exception) {
                emit(Result.Error(e))
            }
            delay(refreshIntervalMs)
        }
    }
}