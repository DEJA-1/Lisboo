package com.example.lisboo.adapter

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.lisboo.R
import com.example.lisboo.fragments.MainFragment
import com.example.lisboo.fragments.MainFragmentDirections
import com.example.lisboo.fragments.UpdateFragment
import com.example.lisboo.model.Book
import com.example.lisboo.viewmodel.BookViewModel

class MainAdapter: RecyclerView.Adapter<MainAdapter.MyViewHolder>() {

    private var bookList = emptyList<Book>()
    //initializing MainFragment, required to use deleteBook method

    private lateinit var mBookViewModel: BookViewModel

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val bookScore : TextView = itemView.findViewById(R.id.book_score)
        val bookName : TextView = itemView.findViewById(R.id.book_name)
        val bookDate : TextView = itemView.findViewById(R.id.book_date)
        val row : View = itemView.findViewById(R.id.row_Layout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = bookList[position]
        holder.bookScore.text = currentItem.score.toString()
        holder.bookName.text = currentItem.title
        holder.bookDate.text = currentItem.date
        holder.row.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToUpdateFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }

    }

    override fun getItemCount(): Int {
        return bookList.size
    }


    fun setData(book: List<Book>){
        this.bookList = book
        notifyDataSetChanged()
    }

}