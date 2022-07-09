package com.ingridsantos.ceibatechnicaltest.presentation.users.viewmodel

import androidx.lifecycle.ViewModel
import com.ingridsantos.ceibatechnicaltest.domain.usecases.LocalUserUC

class UserLocalViewModel(
    private val localUserUC: LocalUserUC
) : ViewModel()
