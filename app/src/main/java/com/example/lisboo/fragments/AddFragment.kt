package com.example.lisboo.fragments

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
    }

    //adding data to the database
    private fun onButtonSaveClicked() {
        val bookName = binding.editTextBookName.text.toString()
        val bookRate = binding.editTextBookScore.text
        val bookDate = binding.editTextBookDate.text.toString()

        if (inputCheck(bookName, bookDate, bookRate)){
            //create book object
            val book = Book(0, Integer.parseInt(bookRate.toString()), bookName, bookDate)
            //adding data to the database
            mBookViewModel.addBook(book)
            Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_LONG).show()
            //navigating back to the main fragment
            findNavController().navigate(R.id.action_addFragment_to_mainFragment)
        } else{
            Toast.makeText(requireContext(), "Please enter the data.", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(bookName: String, bookDate: String, bookRate: Editable): Boolean{
        return !(TextUtils.isEmpty(bookName) && TextUtils.isEmpty(bookDate) && TextUtils.isEmpty(bookRate))
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