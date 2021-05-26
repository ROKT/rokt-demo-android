package com.rokt.roktdemo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.distinctUntilChanged
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class MainActivityViewModel @Inject constructor() : ViewModel() {
    private val _selectedTagId = MutableLiveData<String>()
    val selectedTagId = _selectedTagId.distinctUntilChanged()

    fun updateSelectedTagId(tagId: String) {
        _selectedTagId.value = tagId
    }
}
