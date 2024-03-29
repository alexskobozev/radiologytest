package com.wishnewjam.radiologytest.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView
import com.wishnewjam.radiologytest.R
import com.wishnewjam.radiologytest.db.QuestionsEntity

class QuestionsListAdapter : RecyclerView.Adapter<QuestionsListViewHolder>(),
        FastScrollRecyclerView.SectionedAdapter {
    override fun getSectionName(position: Int): String {
        val num = questions[position].num.div(100)
        return "${num * 100}-${(num + 1) * 100}"
    }

    var questions: List<QuestionsEntity> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionsListViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding: ViewDataBinding =
                DataBindingUtil.inflate(layoutInflater, R.layout.item_list_questions, parent, false)
        return QuestionsListViewHolder(binding)
    }

    override fun getItemCount() = questions.size

    override fun onBindViewHolder(holder: QuestionsListViewHolder, position: Int) {
        holder.bind(questions[position])
    }
}