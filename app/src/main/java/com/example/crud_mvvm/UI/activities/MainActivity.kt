package com.example.crud_mvvm.UI.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.crud_mvvm.R
import com.example.crud_mvvm.UI.activities.adapters.HomeRecyclerAdapter
import com.example.crud_mvvm.database.Subscriber
import com.example.crud_mvvm.database.SubscriberDAO
import com.example.crud_mvvm.database.SubscriberDatabase
import com.example.crud_mvvm.databinding.ActivityMainBinding
import com.example.crud_mvvm.repository.SubscriberRepository
import com.example.crud_mvvm.viewModels.SubscriberViewModel
import com.example.crud_mvvm.viewModels.factories.SubscriberViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var mDataBinding: ActivityMainBinding
    private lateinit var subscriberViewModel: SubscriberViewModel
    private lateinit var adapter: HomeRecyclerAdapter
    private lateinit var dao: SubscriberDAO
    private lateinit var repository: SubscriberRepository
    private lateinit var factory: ViewModelProvider.Factory
    private var TAG:String = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        initializeInstance()

    }

    private fun initializeInstance(){

        dao = SubscriberDatabase.getInstance(application).subscriberDAO
        repository = SubscriberRepository(dao)
        factory = SubscriberViewModelFactory(repository)
        subscriberViewModel = ViewModelProvider(this,factory).get(SubscriberViewModel::class.java)
        mDataBinding.myViewModel = subscriberViewModel
        mDataBinding.lifecycleOwner = this


        initRecyclerview()
        showMessage()
    }


    private fun initRecyclerview(){

        mDataBinding.mRecyclerData.layoutManager = LinearLayoutManager(this)
        adapter = HomeRecyclerAdapter { selectedItem:Subscriber->listItemClicked(selectedItem) }
        mDataBinding.mRecyclerData.adapter = adapter
        displaySubscribersList()

    }

    private fun displaySubscribersList(){

        subscriberViewModel.subscribers.observe(this, Observer {
            Log.i(TAG, "displaySubscribersList: $it")
            adapter.setListItems(it)
            adapter.notifyDataSetChanged()
        })

    }

    private fun listItemClicked(subscriber: Subscriber){
        Log.i(TAG, "listItemClicked: "+subscriber.id.toString())

        subscriberViewModel.initUpdateAndDelete(subscriber)
    }

    private fun showMessage(){
        subscriberViewModel.message.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this,it,Toast.LENGTH_SHORT).show()
            }
        })
    }
}