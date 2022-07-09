package com.ingridsantos.ceibatechnicaltest.data.repositories

import com.ingridsantos.ceibatechnicaltest.data.endpoints.UsersApi
import com.ingridsantos.ceibatechnicaltest.data.models.UserDTO
import com.ingridsantos.ceibatechnicaltest.domain.entities.User
import com.ingridsantos.ceibatechnicaltest.domain.repositories.DomainExceptionRepository
import com.ingridsantos.ceibatechnicaltest.domain.repositories.UsersRepository
import com.ingridsantos.ceibatechnicaltest.utils.Mapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class UserRepositoryImpl(
    private val userApi: UsersApi,
    private val mapUser: Mapper<UserDTO, User>,
    private val domainExceptionRepository: DomainExceptionRepository
) : UsersRepository {

    override fun getUsers(): Flow<List<User>> {
        return flow {
            val posts = userApi.getUsers().map(mapUser)
            emit(posts)
        }.catch {
            throw domainExceptionRepository.manageError(it)
        }
    }
}
