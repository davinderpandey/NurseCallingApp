package com.app.nursecalling.adapters

import android.graphics.ColorSpace.Model
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.app.nursecalling.R
import com.app.nursecalling.model.EventsModel
import kotlinx.android.synthetic.main.card_layout.view.*

class EventsAdapter(val eventsList: List<EventsModel>) : Adapter<EventsAdapter.EventsViewHolder>() {
    class EventsViewHolder(itemView: View) : ViewHolder(itemView) {
        fun bindItems(model: EventsModel){
            itemView.titleTv.text = model.systemName
            itemView.descriptionTv1.text = model.address
            itemView.descriptionTv2.text = model.eventName
            itemView.eventId.text =model.id.toString();
//            itemView.descriptionTv3.text = model.date.toString();

        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.card_layout,parent,false)
        return EventsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return eventsList.size
    }

    override fun onBindViewHolder(holder: EventsViewHolder, position: Int) {
        holder.bindItems(eventsList[position])
    }










}

