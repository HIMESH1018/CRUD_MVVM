package com.example.crud_mvvm.UI.activities.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.crud_mvvm.R
import com.example.crud_mvvm.UI.activities.adapters.viewholders.HomeViewHolder
import com.example.crud_mvvm.database.Subscriber
import com.example.crud_mvvm.databinding.ItemRecyclerviewBinding

class HomeRecyclerAdapter(

    private val clickListner:(Subscriber)-> Unit): RecyclerView.Adapter<HomeViewHolder>() {

    private val subscribers = ArrayList<Subscriber>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val mlayoutBinding: ItemRecyclerviewBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_recyclerview, parent, false
        )
        return HomeViewHolder(mlayoutBinding)

    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {

        holder.bind(subscribers[position],clickListner)
    }

    override fun getItemCount(): Int {

        return if(subscribers.isEmpty()){
            0
        } else{
            subscribers.size
        }
    }


    fun setListItems(subscriberList: List<Subscriber>){
        subscribers.clear()
        subscribers.addAll(subscriberList)

    }

}