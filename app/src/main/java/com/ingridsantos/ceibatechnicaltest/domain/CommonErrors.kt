package com.ingridsantos.ceibatechnicaltest.domain

import com.google.gson.JsonSyntaxException
import com.ingridsantos.ceibatechnicaltest.domain.exceptions.DomainException
import com.ingridsantos.ceibatechnicaltest.domain.exceptions.InternalErrorException
import com.ingridsantos.ceibatechnicaltest.domain.exceptions.NoConnectivityDomainException
import com.ingridsantos.ceibatechnicaltest.domain.exceptions.NoConnectivityException
import com.ingridsantos.ceibatechnicaltest.domain.exceptions.ParseException
import com.ingridsantos.ceibatechnicaltest.domain.exceptions.TimeOutException
import com.ingridsantos.ceibatechnicaltest.domain.exceptions.UnknownError
import com.squareup.moshi.JsonDataException
import java.net.ConnectException
import java.net.SocketTimeoutException

open class CommonErrors {

    fun manageException(throwable: Throwable): DomainException {
        return manageJavaErrors(throwable)
    }

    fun manageJavaErrors(throwable: Throwable): DomainException {
        return when (throwable) {
            is SocketTimeoutException -> TimeOutException
            is ConnectException -> InternalErrorException(throwable.message ?: String())
            else -> manageParsingExceptions(throwable)
        }
    }

    fun manageParsingExceptions(throwable: Throwable): DomainException {
        return when (throwable) {
            is JsonDataException -> ParseException
            is JsonSyntaxException -> ParseException
            else -> manageOtherException(throwable)
        }
    }

    fun manageOtherException(throwable: Throwable): DomainException {
        return when (throwable) {
            is NoConnectivityException -> NoConnectivityDomainException
            else -> UnknownError(throwable.message.toString())
        }
    }
}
