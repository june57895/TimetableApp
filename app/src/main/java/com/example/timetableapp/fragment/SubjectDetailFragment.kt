
package com.example.timetableapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.timetableapp.R
import com.example.timetableapp.TimetableApplication
import com.example.timetableapp.data.Subject
import com.example.timetableapp.databinding.FragmentSubjectDetailBinding
import com.example.timetableapp.model.TimetableViewModel
import com.example.timetableapp.model.TimetableViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class SubjectDetailFragment : Fragment() {
    private val navigationArgs: SubjectDetailFragmentArgs by navArgs()
    lateinit var subject: Subject

    private val viewModel: TimetableViewModel by activityViewModels {
        TimetableViewModelFactory(
            (activity?.application as TimetableApplication).database.subjectDao()
        )
    }

    private var _binding: FragmentSubjectDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSubjectDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Binds views with the passed in subject data.
     */
    private fun bind(subject: Subject) {
        binding.apply {
            subjectName.text = subject.subjectName
            subjectStartTime.text = subject.subjectStartTime.toString()
            subjectEndTime.text = subject.subjectEndTime.toString()
            deleteSubject.setOnClickListener { showConfirmationDialog() }
            editSubject.setOnClickListener { editSubject() }
        }
    }

    /**
     * Navigate to the Edit subject screen.
     */
    private fun editSubject() {
        val action = SubjectDetailFragmentDirections.actionSubjectDetailFragmentToAddSubjectFragment(
            getString(R.string.edit_fragment_title),
            subject.id
        )
        this.findNavController().navigate(action)
    }

    /**
     * Displays an alert dialog to get the user's confirmation before deleting the subject.
     */
    private fun showConfirmationDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(android.R.string.dialog_alert_title))
            .setMessage(getString(R.string.delete_question))
            .setCancelable(false)
            .setNegativeButton(getString(R.string.no)) { _, _ -> }
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                deleteSubject()
            }
            .show()
    }

    /**
     * Deletes the current subject and navigates to the list fragment.
     */
    private fun deleteSubject() {
        viewModel.deleteSubject(subject)
        findNavController().navigateUp()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = navigationArgs.subjectId
        // Retrieve the subject details using the subjectId.
        // Attach an observer on the data (instead of polling for changes) and only update the
        // the UI when the data actually changes.
        viewModel.retrieveSubject(id).observe(this.viewLifecycleOwner) { selectedSubject ->
            subject = selectedSubject
            bind(subject)
        }
    }

    /**
     * Called when fragment is destroyed.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
