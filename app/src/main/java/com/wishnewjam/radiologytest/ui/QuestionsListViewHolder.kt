package com.wishnewjam.radiologytest.ui

import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView
import com.wishnewjam.radiologytest.db.QuestionsEntity

class QuestionsListViewHolder(private val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {

    fun bind(question: QuestionsEntity) {
        binding.setVariable(BR.questionEntity, question)
        binding.executePendingBindings()
    }
}