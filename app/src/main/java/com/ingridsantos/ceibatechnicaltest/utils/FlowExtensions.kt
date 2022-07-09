package com.ingridsantos.ceibatechnicaltest.utils

import com.ingridsantos.ceibatechnicaltest.domain.exceptions.DomainException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal fun <T> Flow<T>.handleViewModelExceptions(onError: (DomainException) -> Unit): Flow<T> =
    flow {
        try {
            collect { value -> emit(value) }
        } catch (e: DomainException) {
            onError(e)
        }
    }
