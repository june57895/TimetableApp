
package com.example.timetableapp.fragment

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.timetableapp.TimetableApplication
import com.example.timetableapp.data.Subject
import com.example.timetableapp.databinding.FragmentAddSubjectBinding
import com.example.timetableapp.model.TimetableViewModel
import com.example.timetableapp.model.TimetableViewModelFactory


class AddSubjectFragment : Fragment() {


    private val viewModel: TimetableViewModel by activityViewModels {
        TimetableViewModelFactory(
            (activity?.application as TimetableApplication).database
                .subjectDao()
        )
    }
    private val navigationArgs: SubjectDetailFragmentArgs by navArgs()

    lateinit var subject: Subject


    private var _binding: FragmentAddSubjectBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddSubjectBinding.inflate(inflater, container, false)
        return binding.root
    }


    private fun isEntryValid(): Boolean {
        return viewModel.isEntryValid(
            binding.subjectName.text.toString(),
            binding.subjectStartTime.text.toString(),
            binding.subjectEndTime.text.toString(),
        )
    }

    private fun bind(subject: Subject) {
        binding.apply {
            subjectName.setText(subject.subjectName, TextView.BufferType.SPANNABLE)
            subjectStartTime.setText(subject.subjectStartTime.toString(), TextView.BufferType.SPANNABLE)
            subjectEndTime.setText(subject.subjectEndTime.toString(), TextView.BufferType.SPANNABLE)
            saveAction.setOnClickListener { updateSubject() }
        }
    }

    private fun addNewSubject() {
        if (isEntryValid()) {
            viewModel.addNewSubject(
                binding.subjectName.text.toString(),
                binding.subjectStartTime.text.toString(),
                binding.subjectEndTime.text.toString(),
            )
            val action = AddSubjectFragmentDirections.actionAddSubjectFragmentToSubjectFragment()
            findNavController().navigate(action)
        }
    }

    /**
     * Updates an existing Subject in the database and navigates up to list fragment.
     */
    private fun updateSubject() {
        if (isEntryValid()) {
            viewModel.updateSubject(
                this.navigationArgs.subjectId,
                this.binding.subjectName.text.toString(),
                this.binding.subjectStartTime.text.toString(),
                this.binding.subjectEndTime.text.toString()
            )
            val action = AddSubjectFragmentDirections.actionAddSubjectFragmentToSubjectFragment()
            findNavController().navigate(action)
        }
    }

    /**
     * Called when the view is created.
     * The subjectId Navigation argument determines the edit subject  or add new subject.
     * If the subjectId is positive, this method retrieves the information from the database and
     * allows the user to update it.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = navigationArgs.subjectId
        if (id > 0) {
            viewModel.retrieveSubject(id).observe(this.viewLifecycleOwner) { selectedSubject ->
                subject = selectedSubject
                bind(subject)
            }
        } else {
            binding.saveAction.setOnClickListener {
                addNewSubject()
            }
        }
    }

    /**
     * Called before fragment is destroyed.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        // Hide keyboard.
        val inputMethodManager = requireActivity().getSystemService(INPUT_METHOD_SERVICE) as
                InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
        _binding = null
    }
}
