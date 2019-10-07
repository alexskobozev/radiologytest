package com.wishnewjam.radiologytest.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.wishnewjam.radiologytest.R
import com.wishnewjam.radiologytest.databinding.FragmentQuizBinding
import com.wishnewjam.radiologytest.utilities.InjectorUtils
import com.wishnewjam.radiologytest.viewmodels.QuizViewModel

class QuizFragment : Fragment() {

    private var navController: NavController? = null
    private val viewModel: QuizViewModel by viewModels {
        InjectorUtils.provideQuizViewModelFactory(requireContext())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: FragmentQuizBinding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_quiz, container, false)
        val rootView = binding.root
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.allQuestions.observe(this, Observer {
            viewModel.listOfQuestions = it
            viewModel.randomizeQuestion()
        })
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = view.findNavController()
    }

    companion object {
        @BindingAdapter("app:clear")
        @JvmStatic
        fun clear(rb: RadioButton, b: Boolean?) {
            if (b == null) {
                rb.isChecked = false
            }
        }
    }
}