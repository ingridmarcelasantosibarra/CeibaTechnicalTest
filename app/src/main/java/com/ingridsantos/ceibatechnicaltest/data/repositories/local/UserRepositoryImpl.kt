package com.ingridsantos.ceibatechnicaltest.data.repositories.local

import com.ingridsantos.ceibatechnicaltest.data.local.TechnicalTestRoomDatabase
import com.ingridsantos.ceibatechnicaltest.data.local.entities.LocalUser
import com.ingridsantos.ceibatechnicaltest.domain.entities.User
import com.ingridsantos.ceibatechnicaltest.domain.repositories.local.UserRepository
import com.ingridsantos.ceibatechnicaltest.utils.Mapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserRepositoryImpl(
    private val technicalTestRoomDatabase: TechnicalTestRoomDatabase,
    private val mapToUser: Mapper<LocalUser, User>
) : UserRepository {

    override fun getUser(userId: Int): Flow<User> {
        val localUser = technicalTestRoomDatabase.userDao().getInfoUser(userId)
        return localUser.map {
            mapToUser(it)
        }
    }
}
