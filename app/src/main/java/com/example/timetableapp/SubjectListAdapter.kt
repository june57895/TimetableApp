/*
 * Copyright (C) 2021 The Android Open Source Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.timetableapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.timetableapp.data.Subject
import com.example.timetableapp.databinding.SubjectListBinding


class SubjectListAdapter(private val onSubjectClicked: (Subject) -> Unit) :
    ListAdapter<Subject, SubjectListAdapter.SubjectViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectViewHolder {
        return SubjectViewHolder(
            SubjectListBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: SubjectViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onSubjectClicked(current)
        }
        holder.bind(current)
    }

    class SubjectViewHolder(private var binding: SubjectListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(subject: Subject) {
            binding.subjectName.text = subject.subjectName
            binding.subjectStartTime.text = subject.subjectStartTime.toString()
            binding.subjectEndTime.text = subject.subjectEndTime.toString()
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Subject>() {
            override fun areItemsTheSame(oldItem: Subject, newItem: Subject): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Subject, newItem: Subject): Boolean {
                return oldItem.subjectName == newItem.subjectName
            }




        }
    }
}
