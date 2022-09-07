package com.example.lisboo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lisboo.R
import com.example.lisboo.model.Book

class DetailsAdapter : RecyclerView.Adapter<DetailsAdapter.ViewHolder>() {

    var bookList = emptyList<Book>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val bookTitle: TextView = itemView.findViewById(R.id.details_book_title)
        val bookDetails: TextView = itemView.findViewById(R.id.details_book_details)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.details_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = bookList[position]
        holder.bookTitle.text = currentItem.title
        holder.bookDetails.text = currentItem.details
    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    fun setData(book: List<Book>) {
        this.bookList = book
        notifyDataSetChanged()
    }

}