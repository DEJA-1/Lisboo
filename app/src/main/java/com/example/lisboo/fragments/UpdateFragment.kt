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
import androidx.core.widget.doOnTextChanged
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
        binding.editTextBookRateUpdate.setText(args.currentBook.rate.toString())
        binding.editTextBookNameUpdate.setText(args.currentBook.title)
        binding.editTextBookDateUpdate.setText(args.currentBook.date)
        binding.editTextBookDetailsUpdate.setText(args.currentBook.details)

        //description field error
        binding.editTextBookNameUpdate.doOnTextChanged { text, start, before, count ->
            if (text!!.isNotEmpty()){
                binding.textInputLayoutNameUpdate.helperText = null
            } else {
                binding.textInputLayoutNameUpdate.helperText = "required*"
            }
        }

        binding.editTextBookRateUpdate.doOnTextChanged { text, start, before, count ->
            if (text!!.isNotEmpty()){
                binding.textInputLayoutRateUpdate.helperText = null
            } else {
                binding.textInputLayoutRateUpdate.helperText = "required* (1-5)"
            }
        }

        binding.editTextBookDateUpdate.doOnTextChanged { text, start, before, count ->
            if (text!!.isNotEmpty()){
                binding.textInputLayoutDateUpdate.helperText = null
            } else {
                binding.textInputLayoutDateUpdate.helperText = "required*"
            }
        }

        binding.editTextBookDetailsUpdate.doOnTextChanged { text, start, before, count ->
            if (text!!.length > 100) {
                binding.textInputLayoutDetailsUpdate.error = "No More!"
            } else if (text.length <= 100) {
                binding.textInputLayoutDetailsUpdate.error = null
            }
            //displaying helper text whether the field is empty
            if (text.isNotEmpty()){
                binding.textInputLayoutDetailsUpdate.helperText = null
            } else {
                binding.textInputLayoutDetailsUpdate.helperText = "required*"
            }
        }
    }

    private fun deleteItem() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            mBookViewModel.deleteBook(args.currentBook)
            Toast.makeText(requireContext(), "Deleted Successfully!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_mainFragment)
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete ${args.currentBook.title}?")
        builder.setMessage("Are you sure you want to delete ${args.currentBook.title}?")
        builder.create().show()
    }

    private fun onCancelButtonClicked() {
        //navigating back to the main fragment'
        findNavController().navigate(R.id.action_updateFragment_to_mainFragment)
    }

    private fun updateItem() {
        val bookRate = binding.editTextBookRateUpdate.text.toString()
        val bookName = binding.editTextBookNameUpdate.text.toString()
        val bookDate = binding.editTextBookDateUpdate.text.toString()
        val bookDetails = binding.editTextBookDetailsUpdate.text.toString()

        if (inputCheck(bookName, bookDate, bookRate, bookDetails)) {
            val updatedBook = Book(args.currentBook.id,
                Integer.parseInt(bookRate),
                bookName,
                bookDate,
                bookDetails)

            mBookViewModel.updateBook(updatedBook)
            //navigating back
            findNavController().navigate(R.id.action_updateFragment_to_mainFragment)
            Toast.makeText(requireContext(), "Updated Successfully!", Toast.LENGTH_SHORT).show()
        } else {
            //displaying the error message in particular field
            if (TextUtils.isEmpty(bookName)) {
                binding.textInputLayoutNameUpdate.error = "Fill out the field!"
            } else {
                binding.textInputLayoutNameUpdate.error = null
            }

            if (TextUtils.isEmpty(bookRate)) {
                binding.textInputLayoutRateUpdate.error = "Fill out the field!"
            } else {
                binding.textInputLayoutRateUpdate.error = null
            }

            if (TextUtils.isEmpty(bookDate)) {
                binding.textInputLayoutDateUpdate.error = "Fill out the field!"
            } else {
                binding.textInputLayoutDateUpdate.error = null
            }

            if (TextUtils.isEmpty(bookDetails)) {
                binding.textInputLayoutDetailsUpdate.error = "Fill out the field!"
            } else {
                binding.textInputLayoutDetailsUpdate.error = null
            }
        }
    }


    private fun inputCheck(
        bookName: String,
        bookDate: String,
        bookRate: String,
        bookDetails: String,
    ): Boolean {
        return !(TextUtils.isEmpty(bookName) || TextUtils.isEmpty(bookDate) || TextUtils.isEmpty(
            bookRate.toString()) || TextUtils.isEmpty(bookDetails))
    }
}