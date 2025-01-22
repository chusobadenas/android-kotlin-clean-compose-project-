package com.jesusbadenas.kotlin_clean_compose_project.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

abstract class UseCaseFlow<Params, Result> {

    abstract fun execute(params: Params): Flow<Result>

    fun invoke(
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        params: Params
    ): Flow<Result> = execute(params).flowOn(dispatcher)
}

abstract class UseCaseFlowNoParams<Result> {

    abstract fun execute(): Flow<Result>

    fun invoke(
        dispatcher: CoroutineDispatcher = Dispatchers.IO
    ): Flow<Result> = execute().flowOn(dispatcher)
}
