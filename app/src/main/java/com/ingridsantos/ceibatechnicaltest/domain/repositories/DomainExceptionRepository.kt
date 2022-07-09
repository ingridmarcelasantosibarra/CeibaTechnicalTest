package com.ingridsantos.ceibatechnicaltest.domain.repositories

import com.ingridsantos.ceibatechnicaltest.domain.exceptions.DomainException

interface DomainExceptionRepository {
    fun manageError(error: Throwable): DomainException
}
