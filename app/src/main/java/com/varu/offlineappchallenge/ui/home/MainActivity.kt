package com.varu.offlineappchallenge.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.varu.offlineappchallenge.R
import com.varu.offlineappchallenge.data.datasources.PersonLocalDS
import com.varu.offlineappchallenge.data.datasources.PersonRemoteDS
import com.varu.offlineappchallenge.data.local.AppDB
import com.varu.offlineappchallenge.data.models.Person
import com.varu.offlineappchallenge.data.remote.ApiClient
import com.varu.offlineappchallenge.data.repositories.impl.PersonRepoImpl

class MainActivity : AppCompatActivity() {

    private lateinit var rvMatchCards: RecyclerView
    private lateinit var pbLoading: ProgressBar
    private lateinit var txtError: TextView

    private lateinit var matchCardAdapter: MatchCardAdapter
    private lateinit var viewModel: HomeVM
    private var onItemSelect: (Person, MatchCardButtonAction) -> Unit = { person, action ->
        when(action) {
            MatchCardButtonAction.ACCEPT -> viewModel.processPersonAcceptance(person)
            MatchCardButtonAction.DECLINE -> viewModel.processPersonRejection(person)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        matchCardAdapter = MatchCardAdapter(onItemSelect)

        rvMatchCards = findViewById<RecyclerView>(R.id.rv_matches).apply {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            adapter = matchCardAdapter
        }
        pbLoading = findViewById(R.id.pb_loading)
        txtError = findViewById(R.id.txt_error)

        val remoteDS = PersonRemoteDS(ApiClient)
        val localDS = PersonLocalDS(AppDB.getInstance(this).personDao())
        val personRepo = PersonRepoImpl(remoteDS, localDS)
        val homeVMFactory = HomeVMFactory(personRepo)
        viewModel = ViewModelProvider(this, homeVMFactory).get(HomeVM::class.java)

        viewModel.personsForMatch.observe(this) { persons ->
            rvMatchCards.apply {
                if (isVisible.not()) {
                    visibility = View.VISIBLE
                }
            }
            matchCardAdapter.submitList(persons)
        }
        viewModel.loadingState.observe(this) { showLoading ->
            if (showLoading) {
                txtError.visibility = View.GONE
                rvMatchCards.visibility = View.GONE
                pbLoading.visibility = View.VISIBLE
            } else {
                pbLoading.visibility = View.GONE
            }
        }
        viewModel.error.observe(this) { errorMessage ->
            txtError.apply {
                visibility = View.VISIBLE
                text = errorMessage
            }
        }

        viewModel.loadPersonsForMatch()
    }
}