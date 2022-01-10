package com.example.timetableapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.timetableapp.R
import com.example.timetableapp.SubjectListAdapter
import com.example.timetableapp.TimetableApplication
import com.example.timetableapp.databinding.ActivityMainBinding.inflate
import com.example.timetableapp.databinding.FragmentStartBinding
import com.example.timetableapp.databinding.FragmentSubjectBinding
import com.example.timetableapp.model.TimetableViewModel
import com.example.timetableapp.model.TimetableViewModelFactory



class SubjectFragment : Fragment() {
    private val viewModel: TimetableViewModel by activityViewModels {
        TimetableViewModelFactory(
            (activity?.application as TimetableApplication).database.subjectDao()
        )
    }

    private var _binding: FragmentSubjectBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSubjectBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = SubjectListAdapter {
            val action =
                SubjectFragmentDirections.actionSubjectFragmentToSubjectDetailFragment(it.id)
            this.findNavController().navigate(action)
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)
        binding.recyclerView.adapter = adapter

        viewModel.allSubjects.observe(this.viewLifecycleOwner) { subjects ->
            subjects.let {
                adapter.submitList(it)
            }
        }

        binding.floatingActionButton.setOnClickListener {
            val action = SubjectFragmentDirections.actionSubjectFragmentToAddSubjectFragment(
                getString(R.string.add_fragment_title)
            )
            this.findNavController().navigate(action)
        }
    }
}