package com.example.timetableapp.model



import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.timetableapp.data.Subject
import com.example.timetableapp.data.SubjectDao
import kotlinx.coroutines.launch

class TimetableViewModel(private val subjectDao: SubjectDao) : ViewModel() {


    val allSubjects: LiveData<List<Subject>> = subjectDao.getSubjects().asLiveData()



    fun updateSubject(
        subjectId: Int,
        subjectName: String,
        subjectStartTime: String,
        subjectEndTime: String
    ) {
        val updatedSubject = getUpdatedSubjectEntry(subjectId, subjectName, subjectStartTime, subjectEndTime)
        updateSubject(updatedSubject)
    }


    private fun updateSubject(subject: Subject) {
        viewModelScope.launch {
            subjectDao.update(subject)
        }
    }

    


    fun addNewSubject(subjectName: String, subjectStartTime: String, subjectEndTime: String) {
        val newSubject = getNewSubjectEntry(subjectName, subjectStartTime, subjectEndTime)
        insertSubject(newSubject)
    }


    private fun insertSubject(subject: Subject) {
        viewModelScope.launch {
            subjectDao.insert(subject)
        }
    }


    fun deleteSubject(subject: Subject) {
        viewModelScope.launch {
            subjectDao.delete(subject)
        }
    }


    fun retrieveSubject(id: Int): LiveData<Subject> {
        return subjectDao.getSubject(id).asLiveData()
    }


    fun isEntryValid(subjectName: String, subjectStartTime: String, subjectEndTime: String): Boolean {
        if (subjectName.isBlank() || subjectStartTime.isBlank() || subjectEndTime.isBlank()) {
            return false
        }
        return true
    }


    private fun getNewSubjectEntry(subjectName: String, subjectStartTime: String, subjectEndTime: String): Subject {
        return Subject(
            subjectName = subjectName,
            subjectStartTime = subjectStartTime,
            subjectEndTime = subjectEndTime
        )
    }


    private fun getUpdatedSubjectEntry(
        subjectId: Int,
        subjectName: String,
        subjectStartTime: String,
        subjectEndTime: String
    ): Subject {
        return Subject(
            id = subjectId,
            subjectName = subjectName,
            subjectStartTime = subjectStartTime,
            subjectEndTime = subjectEndTime
        )
    }
}

class TimetableViewModelFactory(private val subjectDao: SubjectDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TimetableViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TimetableViewModel(subjectDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

