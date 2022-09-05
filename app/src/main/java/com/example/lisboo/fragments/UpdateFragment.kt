package com.example.lisboo.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.lisboo.R
import com.example.lisboo.databinding.FragmentUpdateBinding
import com.example.lisboo.model.Book
import com.example.lisboo.viewmodel.BookViewModel

class UpdateFragment : Fragment() {

    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<UpdateFragmentArgs>()

    private lateinit var mBookViewModel: BookViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBookViewModel = ViewModelProvider(this).get(BookViewModel::class.java)

        //update button handle
        binding.updateButton.setOnClickListener { updateItem() }

        //cancel button handle
        binding.cancelButtonUpdate.setOnClickListener { onCancelButtonClicked() }

        //delete button handle
        binding.deleteButton.setOnClickListener { deleteItem() }

        //setting properties equal to book being updated
        binding.editTextBookScoreUpdate.setText(args.currentBook.score.toString())
        binding.editTextBookNameUpdate.setText(args.currentBook.title)
        binding.editTextBookDateUpdate.setText(args.currentBook.date)
    }

    private fun deleteItem() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") {_, _ ->
            mBookViewModel.deleteBook(args.currentBook)
            Toast.makeText(requireContext(), "Deleted Successfully!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_mainFragment)
        }
        builder.setNegativeButton("No") {_, _ ->}
        builder.setTitle("Delete ${args.currentBook.title}?")
        builder.setMessage("Are you sure you want to delete ${args.currentBook.title}?")
        builder.create().show()
    }

    private fun onCancelButtonClicked() {
        //navigating back to the main fragment'
        findNavController().navigate(R.id.action_updateFragment_to_mainFragment)
    }

    private fun updateItem() {
        val bookRate = Integer.parseInt(binding.editTextBookScoreUpdate.text.toString())
        val bookName = binding.editTextBookNameUpdate.text.toString()
        val bookDate = binding.editTextBookDateUpdate.text.toString()

        if (inputCheck(bookName, bookDate, bookRate)){
            val updatedBook = Book(args.currentBook.id, bookRate, bookName, bookDate)

            mBookViewModel.updateBook(updatedBook)
            //navigating back
            findNavController().navigate(R.id.action_updateFragment_to_mainFragment)
            Toast.makeText(requireContext(), "Updated Successfully!", Toast.LENGTH_SHORT).show()
        } else{
            Toast.makeText(requireContext(), "Please enter the data.", Toast.LENGTH_LONG).show()
        }
    }




    private fun inputCheck(bookName: String, bookDate: String, bookRate: Int): Boolean{
        return !(TextUtils.isEmpty(bookName) && TextUtils.isEmpty(bookDate) && TextUtils.isEmpty(
            bookRate.toString()))
    }
}