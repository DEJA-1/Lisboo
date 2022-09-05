package com.example.lisboo.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lisboo.R
import com.example.lisboo.adapter.MainAdapter
import com.example.lisboo.databinding.FragmentMainBinding
import com.example.lisboo.viewmodel.BookViewModel
import java.util.stream.DoubleStream.builder

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var mBookViewModel: BookViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //RecyclerView
        val adapter = MainAdapter()
        val recyclerView = binding.mainRecyclerview
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        //BookViewModel
        mBookViewModel = ViewModelProvider(this).get(BookViewModel::class.java)
        mBookViewModel.readAllData.observe(viewLifecycleOwner, Observer { book ->
            adapter.setData(book)
        })

        //fab button navigation
        binding.mainFabAdd.setOnClickListener { onButtonClicked() }

        //delete button
        binding.mainDelete.setOnClickListener { deleteItems() }

        //details button
        binding.mainDetails.setOnClickListener { onDetailsButtonClicked() }
    }

    private fun onDetailsButtonClicked() {
        findNavController().navigate(R.id.action_mainFragment_to_detailFragment)
    }

    private fun deleteItems() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            mBookViewModel.deleteAllBooks()
            Toast.makeText(requireContext(), "Successfully deleted everything!", Toast.LENGTH_SHORT)
                .show()
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete everything?")
        builder.setMessage("Are you sure you want to delete everything?")
        builder.create().show()
    }

    private fun onButtonClicked() {
        findNavController().navigate(R.id.action_mainFragment_to_addFragment)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}