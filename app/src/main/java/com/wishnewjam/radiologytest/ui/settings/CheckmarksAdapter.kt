package com.wishnewjam.radiologytest.ui.settings

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.wishnewjam.radiologytest.R
import com.wishnewjam.radiologytest.ui.settings.Param.Companion.TYPE_DIVIDER
import com.wishnewjam.radiologytest.viewmodels.QuizSettingsViewModel
import kotlinx.android.synthetic.main.item_list_checkmark.view.*

class CheckmarksAdapter(val viewModel: QuizSettingsViewModel) :
        RecyclerView.Adapter<CheckmarksViewHolder>() {

    var checkBoxes: List<Param> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckmarksViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        return if (viewType == TYPE_DIVIDER) {
            val dividerBinding: ViewDataBinding =
                    DataBindingUtil.inflate(layoutInflater, R.layout.item_list_checkmark_divider,
                            parent, false)
            CheckmarksViewHolder(dividerBinding)
        }
        else {
            val checkmarksBinding: ViewDataBinding =
                    DataBindingUtil.inflate(layoutInflater, R.layout.item_list_checkmark, parent,
                            false)
            CheckmarksViewHolder(checkmarksBinding)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return checkBoxes[position].type
    }

    override fun getItemCount() = checkBoxes.size

    override fun onBindViewHolder(holder: CheckmarksViewHolder, position: Int) {
        holder.bind(checkBoxes[position])
        if (getItemViewType(position) != TYPE_DIVIDER) {
            holder.itemView.item_checkbox.setOnCheckedChangeListener { buttonView, isChecked ->
                checkBoxes[position].checked = isChecked
                viewModel.setParamsForNavigation(checkBoxes)
            }
        }
    }
}