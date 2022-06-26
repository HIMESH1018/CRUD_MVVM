package com.example.crud_mvvm.UI.activities.adapters.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.example.crud_mvvm.database.Subscriber
import com.example.crud_mvvm.databinding.ItemRecyclerviewBinding

class HomeViewHolder(val binding: ItemRecyclerviewBinding):
    RecyclerView.ViewHolder(binding.root) {

        fun bind(subscriber: Subscriber,clickListner:(Subscriber)-> Unit){

            binding.mLblID.text = subscriber.id.toString()
            binding.mLblName.text = subscriber.name
            binding.mLblEmail.text = subscriber.email
            binding.mCardViewItem.setOnClickListener{
                clickListner(subscriber)
            }
        }
}