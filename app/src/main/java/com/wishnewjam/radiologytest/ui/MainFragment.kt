package com.wishnewjam.radiologytest.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.google.firebase.analytics.FirebaseAnalytics
import com.wishnewjam.radiologytest.R
import com.wishnewjam.radiologytest.databinding.FragmentMainBinding
import com.wishnewjam.radiologytest.utilities.EventListener
import com.wishnewjam.radiologytest.utilities.EventObserver
import com.wishnewjam.radiologytest.utilities.InjectorUtils
import com.wishnewjam.radiologytest.viewmodels.MainViewModel

class MainFragment : Fragment() {

    private var navController: NavController? = null

    private val viewModel: MainViewModel by viewModels {
        InjectorUtils.provideMainViewModelFactory()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: FragmentMainBinding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        val rootView = binding.root

        registerNavigation()
        binding.viewModel = viewModel
        binding.questionsListDirection = MainFragmentDirections.actionMainFragmentToQuestionsList()
        binding.quizSettingsDirection =
                MainFragmentDirections.actionMainFragmentToQuizSettingsList()
        binding.quizDirection = MainFragmentDirections.actionMainFragmentToQuiz()
        binding.quizSettingsQuizDirection =
                MainFragmentDirections.actionMainFragmentToQuizSettingsQuiz()
        return rootView
    }

    private fun registerNavigation() {
        viewModel.getNewDestination()
                .observe(this, EventObserver(object : EventListener<Int> {
                    override fun onEvent(t: Int) {
                        navigate(t)
                    }
                }))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = view.findNavController()
        FirebaseAnalytics.getInstance(requireActivity())
                .setCurrentScreen(requireActivity(), javaClass.simpleName, null)
    }

    private fun navigate(id: Int) {
        navController?.navigate(id)
    }

}