package com.wishnewjam.radiologytest.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import com.wishnewjam.radiologytest.R
import com.wishnewjam.radiologytest.databinding.FragmentQuizsettingsBinding
import com.wishnewjam.radiologytest.utilities.EventListener
import com.wishnewjam.radiologytest.utilities.EventObserver
import com.wishnewjam.radiologytest.utilities.InjectorUtils
import com.wishnewjam.radiologytest.viewmodels.QuizSettingsViewModel
import kotlinx.android.synthetic.main.fragment_quizsettings.view.*

class QuizSettingsFragment : Fragment() {

    private var navController: NavController? = null

    private val viewModel: QuizSettingsViewModel by viewModels {
        InjectorUtils.provideQuizSettingsViewModelFactory(requireContext())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: FragmentQuizsettingsBinding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_quizsettings, container, false)
        val rootView = binding.root
        registerNavigation()
        binding.viewModel = viewModel
        val actionQuizSettongsToQuestionsList =
                QuizSettingsFragmentDirections.actionQuizSettongsToQuestionsList()
        binding.questionsListDirection = actionQuizSettongsToQuestionsList

        val adapter = CheckmarksAdapter(viewModel)
        rootView.rv_quizparams.adapter = adapter

        viewModel.paramsLiveData.observe(this, Observer<List<Param>> { it ->
            it?.let {
                adapter.checkBoxes = it
            }
        })

        viewModel.checkedParamsLiveData.observe(this, Observer<Params> { it ->
            it?.let {
                actionQuizSettongsToQuestionsList.paramsList = it
                binding.questionsListDirection = actionQuizSettongsToQuestionsList
            }
        })

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = view.findNavController()
    }

    private fun registerNavigation() {
        viewModel.getNewDestination()
                .observe(this, EventObserver(object : EventListener<NavDirections> {
                    override fun onEvent(t: NavDirections) {
                        navigate(t)
                    }
                }))
    }

    private fun navigate(navDirections: NavDirections) {
        navController?.navigate(navDirections)
    }
}