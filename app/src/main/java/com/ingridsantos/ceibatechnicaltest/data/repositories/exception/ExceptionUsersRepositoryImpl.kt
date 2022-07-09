package com.ingridsantos.ceibatechnicaltest.data.repositories.exception

import com.ingridsantos.ceibatechnicaltest.domain.CommonErrors
import com.ingridsantos.ceibatechnicaltest.domain.exceptions.DomainException
import com.ingridsantos.ceibatechnicaltest.domain.repositories.DomainExceptionRepository

class ExceptionUsersRepositoryImpl : CommonErrors(), DomainExceptionRepository {

    override fun manageError(error: Throwable): DomainException {
        return manageException(error)
    }
}
