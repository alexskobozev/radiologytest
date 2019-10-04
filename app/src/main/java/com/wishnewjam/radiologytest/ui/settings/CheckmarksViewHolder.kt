package com.wishnewjam.radiologytest.ui.settings

import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView

class CheckmarksViewHolder(private val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {

    fun bind(param: Param) {
        binding.setVariable(BR.param, param)
        binding.executePendingBindings()
    }

}