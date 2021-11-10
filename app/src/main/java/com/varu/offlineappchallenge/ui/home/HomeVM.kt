package com.varu.offlineappchallenge.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.varu.offlineappchallenge.data.models.Person
import com.varu.offlineappchallenge.data.repositories.PersonRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

private const val PAGE_SIZE = 5

enum class MatchCardButtonAction(val actionValue: String) {
    ACCEPT("ACCEPT"),
    DECLINE("DECLINE")
}

class HomeVMFactory(private val personRepo: PersonRepo): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeVM::class.java)) {
            return HomeVM(personRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class HomeVM(private val personRepo: PersonRepo): ViewModel() {

    val loadingState: MutableLiveData<Boolean> = MutableLiveData(false)
    val personsForMatch: MutableLiveData<List<Person>> = MutableLiveData()
    val error: MutableLiveData<String> = MutableLiveData() //prefer to create app level abstract error object to use as wrapper to any error need to supply to UI

    fun loadPersonsForMatch() {
        loadingState.value = true
        runBlocking {
            withContext(Dispatchers.IO) {
                personRepo.fetchPersonsForMatch(PAGE_SIZE, 0)
            }
        }.onSuccess {
            loadingState.value = false
            personsForMatch.value = it
        }.onFailure {
            loadingState.value = false
            error.value = "Something went wrong please try again"
        }
    }

    fun processPersonAcceptance(person: Person) {
        processPersonAction(person, MatchCardButtonAction.ACCEPT)
    }

    fun processPersonRejection(person: Person) {
        processPersonAction(person, MatchCardButtonAction.DECLINE)
    }

    private fun processPersonAction(person: Person, action: MatchCardButtonAction) {
        runBlocking {
            withContext(Dispatchers.IO) {
                personRepo.processPersonMatchAction(person.apply {
                    matchAction = action.actionValue
                })
            }
        }
    }
}