package com.tipandsplitcal.sharedmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Created by Monil Panchal, B00838558 on 2020-03-04.
 * Organization: Dalhousie university
 * Email: monil.panchal@dal.ca
 */

/**
 * A subclass of [ViewModel], instance of this class used as Shared ViewModel to allow fragments
 * to communicate with each other by sharing common data.
 */

class SharedViewModel : ViewModel() {

    /**
     * A [MutableMap] is used to store the common data which is shared by fragments.
     */
    private val data = MutableLiveData<MutableMap<String, Any>>()

    fun setSharedData(input: MutableMap<String, Any>) {
        data.value = input
    }

    fun getSharedData(): LiveData<MutableMap<String, Any>>? {
        return data
    }
}