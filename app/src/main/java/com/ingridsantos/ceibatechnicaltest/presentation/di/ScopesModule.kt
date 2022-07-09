package com.ingridsantos.ceibatechnicaltest.presentation.di

import com.ingridsantos.ceibatechnicaltest.data.endpoints.UsersApi
import com.ingridsantos.ceibatechnicaltest.data.mappers.mapToUser
import com.ingridsantos.ceibatechnicaltest.data.repositories.UserRepositoryImpl
import com.ingridsantos.ceibatechnicaltest.data.repositories.exception.ExceptionUsersRepositoryImpl
import com.ingridsantos.ceibatechnicaltest.domain.repositories.UsersRepository
import com.ingridsantos.ceibatechnicaltest.domain.usecases.UsersUC
import com.ingridsantos.ceibatechnicaltest.network.RetrofitClient
import com.ingridsantos.ceibatechnicaltest.presentation.users.fragment.UsersFragment
import com.ingridsantos.ceibatechnicaltest.presentation.users.viewmodel.UsersViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val usersModule = module {
    scope<UsersFragment> {
        viewModel {
            UsersViewModel(
                usersUC = get()
            )
        }

        scoped {
            UsersUC(
                userRepository = get()
            )
        }

        scoped<UsersRepository> {
            UserRepositoryImpl(
                userApi = get(),
                mapUser = mapToUser,
                domainExceptionRepository = ExceptionUsersRepositoryImpl()
            )
        }

        scoped {
            RetrofitClient.getInstanceRetrofit().create(UsersApi::class.java)
        }
    }
}
