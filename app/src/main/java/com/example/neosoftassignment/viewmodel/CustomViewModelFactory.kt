package com.example.neosoftassignment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.neosoftassignment.repository.LocalRepository

class CustomViewModelFactory(private val localRepository: LocalRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(CustomViewModel::class.java)) {
            CustomViewModel(this.localRepository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}