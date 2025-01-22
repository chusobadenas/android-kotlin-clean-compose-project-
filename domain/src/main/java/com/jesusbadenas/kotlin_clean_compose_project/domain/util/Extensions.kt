package com.jesusbadenas.kotlin_clean_compose_project.domain.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

fun <T> T.toList(): List<T> = listOf(this)

fun <T> T.toFlow(): Flow<T> = flowOf(this)

fun <T> List<T>.toFlow(): Flow<List<T>> = flowOf(this)
