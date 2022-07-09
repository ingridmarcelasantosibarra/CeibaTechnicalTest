package com.ingridsantos.ceibatechnicaltest.data.repositories.local

import com.ingridsantos.ceibatechnicaltest.data.local.TechnicalTestRoomDatabase
import com.ingridsantos.ceibatechnicaltest.data.local.entities.LocalUser
import com.ingridsantos.ceibatechnicaltest.domain.entities.User
import com.ingridsantos.ceibatechnicaltest.domain.repositories.local.LocalUsersRepository
import com.ingridsantos.ceibatechnicaltest.utils.Mapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalUsersRepositoryImpl(
    private val technicalTestRoomDatabase: TechnicalTestRoomDatabase,
    private val mapToLocalUser: Mapper<List<User>, List<LocalUser>>,
    private val mapToUsers: Mapper<List<LocalUser>, List<User>>
) : LocalUsersRepository {

    override suspend fun insertAll(users: List<User>): List<Long> {
        return technicalTestRoomDatabase.userDao().insertAll(mapToLocalUser(users))
    }

    override fun getUsers(): Flow<List<User>> {
        return technicalTestRoomDatabase.userDao().getUsers().map {
            mapToUsers(it)
        }
    }
}
