package com.ingridsantos.ceibatechnicaltest.presentation.di

import com.ingridsantos.ceibatechnicaltest.data.endpoints.PostsUserApi
import com.ingridsantos.ceibatechnicaltest.data.endpoints.UsersApi
import com.ingridsantos.ceibatechnicaltest.data.mappers.mapToPost
import com.ingridsantos.ceibatechnicaltest.data.mappers.mapToUser
import com.ingridsantos.ceibatechnicaltest.data.repositories.PostsUserRepositoryImpl
import com.ingridsantos.ceibatechnicaltest.data.repositories.UserRepositoryImpl
import com.ingridsantos.ceibatechnicaltest.data.repositories.exception.ExceptionPostsUserRepositoryImpl
import com.ingridsantos.ceibatechnicaltest.data.repositories.exception.ExceptionUsersRepositoryImpl
import com.ingridsantos.ceibatechnicaltest.domain.repositories.PostsUserRepository
import com.ingridsantos.ceibatechnicaltest.domain.repositories.UsersRepository
import com.ingridsantos.ceibatechnicaltest.domain.usecases.PostsUC
import com.ingridsantos.ceibatechnicaltest.domain.usecases.UsersUC
import com.ingridsantos.ceibatechnicaltest.network.RetrofitClient
import com.ingridsantos.ceibatechnicaltest.presentation.users.fragment.PostsFragment
import com.ingridsantos.ceibatechnicaltest.presentation.users.fragment.UsersFragment
import com.ingridsantos.ceibatechnicaltest.presentation.users.viewmodel.PostsViewModel
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

val postsModule = module {
    scope<PostsFragment> {
        viewModel {
            PostsViewModel(
                postsUC = get()
            )
        }

        scoped {
            PostsUC(get())
        }

        scoped<PostsUserRepository> {
            PostsUserRepositoryImpl(
                postsUserApi = get(),
                mapPost = mapToPost,
                domainExceptionRepository = ExceptionPostsUserRepositoryImpl()
            )
        }

        scoped {
            RetrofitClient.getInstanceRetrofit().create(PostsUserApi::class.java)
        }
    }
}
