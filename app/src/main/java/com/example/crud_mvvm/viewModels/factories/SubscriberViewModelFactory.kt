package com.example.crud_mvvm.viewModels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.crud_mvvm.repository.SubscriberRepository
import com.example.crud_mvvm.viewModels.SubscriberViewModel

class SubscriberViewModelFactory(private val repository: SubscriberRepository):ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SubscriberViewModel::class.java)) {
            return SubscriberViewModel(repository) as T
        }
        throw IllegalAccessException("Unknown View Model Class")
    }

}