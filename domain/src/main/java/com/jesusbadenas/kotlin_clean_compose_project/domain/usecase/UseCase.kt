package com.jesusbadenas.kotlin_clean_compose_project.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class UseCase<Params, Result> {

    abstract suspend fun execute(params: Params): Result

    fun invoke(
        scope: CoroutineScope,
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        params: Params,
        onError: (Throwable) -> Unit = {},
        onResult: (Result) -> Unit = {}
    ) {
        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            onError.invoke(throwable)
        }
        scope.launch(exceptionHandler) {
            val result: Result = withContext(dispatcher) {
                execute(params)
            }
            onResult(result)
        }
    }
}

abstract class UseCaseFlow<Params, Result> {

    abstract fun execute(params: Params): Flow<Result>

    fun invoke(
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        params: Params
    ): Flow<Result> = execute(params).flowOn(dispatcher)
}

abstract class UseCaseNoParams<Result> {

    abstract suspend fun execute(): Result

    fun invoke(
        scope: CoroutineScope,
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        onError: (Throwable) -> Unit = {},
        onResult: (Result) -> Unit = {}
    ) {
        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            onError.invoke(throwable)
        }
        scope.launch(exceptionHandler) {
            val result: Result = withContext(dispatcher) {
                execute()
            }
            onResult(result)
        }
    }
}

abstract class UseCaseFlowNoParams<Result> {

    abstract fun execute(): Flow<Result>

    fun invoke(
        dispatcher: CoroutineDispatcher = Dispatchers.IO
    ): Flow<Result> = execute().flowOn(dispatcher)
}
