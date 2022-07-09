package com.ingridsantos.ceibatechnicaltest.data.mappers

import com.ingridsantos.ceibatechnicaltest.data.local.entities.LocalUser
import com.ingridsantos.ceibatechnicaltest.data.models.UserDTO
import com.ingridsantos.ceibatechnicaltest.domain.entities.User
import com.ingridsantos.ceibatechnicaltest.utils.Mapper

val mapLocalUserToUser: Mapper<List<LocalUser>, List<User>> = { input ->
    val returnList: MutableList<User> = mutableListOf()
    input.forEach { localUser ->
        with(localUser) {
            returnList.add(
                User(
                    id = id,
                    username = username,
                    email = email,
                    phone = phone
                )
            )
        }
    }
    returnList
}

val mapToLocalUser: Mapper<List<User>, List<LocalUser>> = { input ->
    val returnList: MutableList<LocalUser> = mutableListOf()
    input.forEach { user ->
        with(user) {
            returnList.add(
                LocalUser(
                    id = id,
                    username = username,
                    email = email,
                    phone = phone
                )
            )
        }
    }
    returnList
}

val mapUserDTOToUser: Mapper<UserDTO, User> = { userDTO ->
    with(userDTO) {
        User(
            id = id,
            email = email,
            phone = phone,
            username = username
        )
    }
}

val mapToUser: Mapper<LocalUser, User> = { localUser ->
    with(localUser) {
        User(
            id = id,
            email = email,
            phone = phone,
            username = username
        )
    }
}
