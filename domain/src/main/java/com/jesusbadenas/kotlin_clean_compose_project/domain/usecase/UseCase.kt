package com.jesusbadenas.kotlin_clean_compose_project.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class UseCase<Params, Result> {

    abstract suspend fun execute(params: Params): Result

    fun invoke(
        scope: CoroutineScope,
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        params: Params,
        onResult: (Result) -> Unit = {}
    ) {
        scope.launch {
            val result: Result = withContext(dispatcher) {
                execute(params)
            }
            onResult(result)
        }
    }
}

abstract class UseCaseNoParams<Result> {

    abstract suspend fun execute(): Result

    fun invoke(
        scope: CoroutineScope,
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        onResult: (Result) -> Unit = {}
    ) {
        scope.launch {
            val result: Result = withContext(dispatcher) {
                execute()
            }
            onResult(result)
        }
    }
}
