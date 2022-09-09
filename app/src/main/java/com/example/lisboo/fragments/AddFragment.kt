package com.example.lisboo.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextUtils.isEmpty
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.lisboo.R
import com.example.lisboo.databinding.FragmentAddBinding
import com.example.lisboo.model.Book
import com.example.lisboo.viewmodel.BookViewModel

class AddFragment : Fragment() {
    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    private lateinit var mBookViewModel: BookViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBookViewModel = ViewModelProvider(this).get(BookViewModel::class.java)

        //cancel button
        binding.cancelButton.setOnClickListener { onButtonCancelClicked() }

        //save button
        binding.addButton.setOnClickListener { onButtonSaveClicked() }

        //description field error
        binding.editTextBookName.doOnTextChanged { text, start, before, count ->
            if (text!!.isNotEmpty()){
                binding.textInputLayoutName.helperText = null
            } else {
                binding.textInputLayoutName.helperText = "required*"
            }
        }

        binding.editTextBookRate.doOnTextChanged { text, start, before, count ->
            if (text!!.isNotEmpty()){
                binding.textInputLayoutRate.helperText = null
            } else {
                binding.textInputLayoutRate.helperText = "required* (1-5)"
            }
        }

        binding.editTextBookDate.doOnTextChanged { text, start, before, count ->
            if (text!!.isNotEmpty()){
                binding.textInputLayoutDate.helperText = null
            } else {
                binding.textInputLayoutDate.helperText = "required*"
            }
        }

        binding.editTextBookDetails.doOnTextChanged { text, start, before, count ->
            if (text!!.length > 100) {
                binding.textInputLayoutDetails.error = "No More!"
            } else if (text.length <= 100) {
                binding.textInputLayoutDetails.error = null
            }
            //displaying helper text whether the field is empty
            if (text.isNotEmpty()){
                binding.textInputLayoutDetails.helperText = null
            } else {
                binding.textInputLayoutDetails.helperText = "required*"
            }
        }
    }

    //adding data to the database
    private fun onButtonSaveClicked() {
        val bookName = binding.editTextBookName.text.toString()
        val bookRate = binding.editTextBookRate.text.toString()
        val bookDate = binding.editTextBookDate.text.toString()
        val bookDetails = binding.editTextBookDetails.text.toString()

        if (inputCheck(bookName, bookDate, bookRate, bookDetails)) {
            //create book object
            val book = Book(0, Integer.parseInt(bookRate), bookName, bookDate, bookDetails)
            //adding data to the database
            mBookViewModel.addBook(book)
            Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_LONG).show()
            //navigating back to the main fragment
            findNavController().navigate(R.id.action_addFragment_to_mainFragment)
        } else {
            //displaying the error message in particular field
            if (TextUtils.isEmpty(bookName)) {
                binding.textInputLayoutName.error = "Fill out the field!"
            } else {
                binding.textInputLayoutName.error = null
            }

            if (TextUtils.isEmpty(bookRate)) {
                binding.textInputLayoutRate.error = "Fill out the field!"
            } else {
                binding.textInputLayoutRate.error = null
            }

            if (TextUtils.isEmpty(bookDate)) {
                binding.textInputLayoutDate.error = "Fill out the field!"
            } else {
                binding.textInputLayoutDate.error = null
            }

            if (TextUtils.isEmpty(bookDetails)) {
                binding.textInputLayoutDetails.error = "Fill out the field!"
            } else {
                binding.textInputLayoutDetails.error = null
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
            bookRate) || TextUtils.isEmpty(bookDetails))
    }

    private fun onButtonCancelClicked() {
        //navigating back to the main fragment
        findNavController().navigate(R.id.action_addFragment_to_mainFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}