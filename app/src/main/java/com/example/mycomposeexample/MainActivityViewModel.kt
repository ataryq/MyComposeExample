package com.example.mycomposeexample

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel: ViewModel() {
    val inputName = MutableLiveData("")
}