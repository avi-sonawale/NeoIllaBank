package com.example.neosoftassignment.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.neosoftassignment.network.CallState
import com.example.neosoftassignment.model.HorizontalAndVerticalListModel
import com.example.neosoftassignment.repository.LocalRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class CustomViewModel(private val localRepository: LocalRepository) : ViewModel() {
    private val TAG = CustomViewModel::class.java.name
    private val _listMutableLiveData =
        MutableLiveData<CallState<List<HorizontalAndVerticalListModel>>>()
    val listLiveData: LiveData<CallState<List<HorizontalAndVerticalListModel>>>
        get() = _listMutableLiveData


    fun fetchDataToLoad() {
        _listMutableLiveData.value = CallState.loading()
        viewModelScope.launch {
            localRepository.fetchDataToLoad().catch {
                Log.d(TAG, "fetchDataToLoad() error : ${it.message}")
                _listMutableLiveData.value = CallState.error(it.message.toString())
            }.collect {
                Log.d(TAG, "fetchDataToLoad() success : ${it.data}")
                _listMutableLiveData.value = CallState.success(it.data)
            }
        }
    }


}