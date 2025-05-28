package com.example.searchmap.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.searchmap.R
import com.example.searchmap.data.models.PlaceModel
import com.example.searchmap.utils.highlightQuery

class SearchAdapter(
    private val onItemClick: (PlaceModel) -> Unit
) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    private val items = mutableListOf<PlaceModel>()
    private var currentQuery = ""

    fun submitData(newItems: List<PlaceModel>, query: String = "") {
        items.clear()
        items.addAll(newItems)
        currentQuery = query
        notifyDataSetChanged()
    }

    inner class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvAddress: TextView = itemView.findViewById(R.id.tvAddress)
        private val imgDirection: ImageButton = itemView.findViewById(R.id.imgDirection)

        fun bind(item: PlaceModel) {
            tvAddress.text = if (currentQuery.isNotEmpty()) {
                highlightQuery(item.displayName, currentQuery)
            } else {
                item.displayName
            }
            imgDirection.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_place, parent, false)
        return SearchViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(items[position])
    }

}


