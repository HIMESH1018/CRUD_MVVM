package com.example.crud_mvvm.viewModels

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crud_mvvm.database.Subscriber
import com.example.crud_mvvm.events.Event
import com.example.crud_mvvm.repository.SubscriberRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SubscriberViewModel(private val repository: SubscriberRepository):ViewModel(),Observable {

    val subscribers = repository.subscribers
    private var isUpdatedOrDelete = false
    private lateinit var subscriberToUpdateOrDelete:Subscriber
    private val statusMessage = MutableLiveData<Event<String>>()

    val message:LiveData<Event<String>>
    get() = statusMessage

    @Bindable
    val inputName = MutableLiveData<String>()
    @Bindable
    val inputEmail = MutableLiveData<String>()

    @Bindable
    val saveOrUpdateButtonText = MutableLiveData<String>()
    @Bindable
    val clearAllOrDeleteButtonText = MutableLiveData<String>()

    init {
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"
    }


    fun saveOrUpdate(){

        if(inputName.value == null){
            statusMessage.value = Event("Please insert name")
        }else if(inputEmail.value == null){
            statusMessage.value = Event("Please insert email")
        }else {


            if (isUpdatedOrDelete) {
                subscriberToUpdateOrDelete.name = inputName.value!!
                subscriberToUpdateOrDelete.email = inputEmail.value!!
                update(subscriberToUpdateOrDelete)
            } else {
                val name: String = inputName.value!!
                val email: String = inputEmail.value!!

                insert(Subscriber(0, name, email))
                inputEmail.value = ""
                inputName.value = ""
            }
        }
    }

    fun clearAllOrDelete(){

        if(isUpdatedOrDelete){
            delete(subscriberToUpdateOrDelete)
        }else {
            clearAll()
        }
    }

    fun initUpdateAndDelete(subscriber: Subscriber){
        inputName.value = subscriber.name
        inputEmail.value = subscriber.email
        isUpdatedOrDelete = true
        subscriberToUpdateOrDelete = subscriber
        saveOrUpdateButtonText.value ="Update"
        clearAllOrDeleteButtonText.value = "Delete"
    }

    fun insert(subscriber: Subscriber):Job = viewModelScope.launch {
            repository.insert(subscriber)
            statusMessage.value = Event("Inserted Successfully")
    }

    fun update(subscriber: Subscriber):Job = viewModelScope.launch {
        repository.update(subscriber)

        inputName.value = ""
        inputEmail.value = ""
        isUpdatedOrDelete = false
        subscriberToUpdateOrDelete = subscriber
        saveOrUpdateButtonText.value ="Save"
        clearAllOrDeleteButtonText.value = "Clear All"
        statusMessage.value = Event("Updated Successfully")
    }

    fun delete(subscriber: Subscriber):Job = viewModelScope.launch {
        repository.delete(subscriber)
        inputName.value = ""
        inputEmail.value = ""
        isUpdatedOrDelete = false
        subscriberToUpdateOrDelete = subscriber
        saveOrUpdateButtonText.value ="Save"
        clearAllOrDeleteButtonText.value = "Clear All"
        statusMessage.value = Event("Deleted Successfully")
    }

    fun clearAll():Job = viewModelScope.launch {
        repository.deleteAll()
        statusMessage.value = Event("All Data Cleared Successfully")
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

}