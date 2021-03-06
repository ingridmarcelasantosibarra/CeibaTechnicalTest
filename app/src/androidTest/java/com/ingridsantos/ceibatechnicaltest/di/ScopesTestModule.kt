package com.ingridsantos.ceibatechnicaltest.di

import com.ingridsantos.ceibatechnicaltest.data.endpoints.PostsUserApi
import com.ingridsantos.ceibatechnicaltest.data.endpoints.UsersApi
import com.ingridsantos.ceibatechnicaltest.data.mappers.mapLocalUserToUser
import com.ingridsantos.ceibatechnicaltest.data.mappers.mapToLocalUser
import com.ingridsantos.ceibatechnicaltest.data.mappers.mapToPost
import com.ingridsantos.ceibatechnicaltest.data.mappers.mapToUser
import com.ingridsantos.ceibatechnicaltest.data.mappers.mapUserDTOToUser
import com.ingridsantos.ceibatechnicaltest.data.repositories.PostsUserRepositoryImpl
import com.ingridsantos.ceibatechnicaltest.data.repositories.UsersRepositoryImpl
import com.ingridsantos.ceibatechnicaltest.data.repositories.exception.ExceptionPostsUserRepositoryImpl
import com.ingridsantos.ceibatechnicaltest.data.repositories.exception.ExceptionUsersRepositoryImpl
import com.ingridsantos.ceibatechnicaltest.data.repositories.local.LocalUsersRepositoryImpl
import com.ingridsantos.ceibatechnicaltest.data.repositories.local.UserRepositoryImpl
import com.ingridsantos.ceibatechnicaltest.domain.repositories.PostsUserRepository
import com.ingridsantos.ceibatechnicaltest.domain.repositories.UsersRepository
import com.ingridsantos.ceibatechnicaltest.domain.repositories.local.LocalUsersRepository
import com.ingridsantos.ceibatechnicaltest.domain.repositories.local.UserRepository
import com.ingridsantos.ceibatechnicaltest.domain.usecases.FilterUsersUC
import com.ingridsantos.ceibatechnicaltest.domain.usecases.LocalUserUC
import com.ingridsantos.ceibatechnicaltest.domain.usecases.LocalUsersUC
import com.ingridsantos.ceibatechnicaltest.domain.usecases.PostsUC
import com.ingridsantos.ceibatechnicaltest.domain.usecases.UsersUC
import com.ingridsantos.ceibatechnicaltest.network.RetrofitClient
import com.ingridsantos.ceibatechnicaltest.presentation.users.fragment.PostsFragment
import com.ingridsantos.ceibatechnicaltest.presentation.users.fragment.UsersFragment
import com.ingridsantos.ceibatechnicaltest.presentation.users.viewmodel.FilterUsersViewModel
import com.ingridsantos.ceibatechnicaltest.presentation.users.viewmodel.PostsViewModel
import com.ingridsantos.ceibatechnicaltest.presentation.users.viewmodel.UsersViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val usersTestModule = module {
    scope<UsersFragment> {
        viewModel {
            UsersViewModel(
                usersUC = get(),
                localUsersUC = get()
            )
        }

        viewModel {
            FilterUsersViewModel(
                filterUsersUC = get()
            )
        }

        scoped {
            UsersUC(
                userRepository = get()
            )
        }

        scoped {
            LocalUsersUC(get())
        }

        scoped {
            FilterUsersUC()
        }

        scoped<UsersRepository> {
            UsersRepositoryImpl(
                userApi = get(),
                mapUser = mapUserDTOToUser,
                domainExceptionRepository = ExceptionUsersRepositoryImpl()
            )
        }

        scoped<LocalUsersRepository> {
            LocalUsersRepositoryImpl(
                technicalTestRoomDatabase = get(),
                mapToLocalUser = mapToLocalUser,
                mapToUsers = mapLocalUserToUser
            )
        }

        scoped {
            get<Retrofit>(named(MOSHI_RETROFIT_INSTANCE_NAME)).create(UsersApi::class.java)
        }
    }
}

val postsTestModule = module {
    scope<PostsFragment> {
        viewModel {
            PostsViewModel(
                postsUC = get(),
                localUserUC = get()
            )
        }

        scoped {
            PostsUC(get())
        }

        scoped {
            LocalUserUC(
                userRepository = get()
            )
        }

        scoped<PostsUserRepository> {
            PostsUserRepositoryImpl(
                postsUserApi = get(),
                mapPost = mapToPost,
                domainExceptionRepository = ExceptionPostsUserRepositoryImpl()
            )
        }

        scoped<UserRepository> {
            UserRepositoryImpl(
                technicalTestRoomDatabase = get(),
                mapToUser = mapToUser
            )
        }

        scoped {
            get<Retrofit>(named(MOSHI_RETROFIT_INSTANCE_NAME)).create(PostsUserApi::class.java)
        }
    }
}
