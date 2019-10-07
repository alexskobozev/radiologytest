package com.wishnewjam.radiologytest.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.wishnewjam.radiologytest.R
import com.wishnewjam.radiologytest.databinding.FragmentQuizBinding
import com.wishnewjam.radiologytest.utilities.InjectorUtils
import com.wishnewjam.radiologytest.viewmodels.QuizViewModel

class QuizFragment : Fragment() {

    private var navController: NavController? = null
    val args: QuizFragmentArgs by navArgs()

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
        viewModel.paramsList.value = args.paramsList
        viewModel.allQuestions.observe(this, Observer {
            viewModel.listOfQuestions = it
            viewModel.init()
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

        @BindingAdapter("app:knowledge")
        @JvmStatic
        fun knowledge(tv: TextView, know: Int?) {
            val s: String
            val color: Int
            if (know == null || know == 0) {
                s = tv.resources.getString(R.string.knowledge_0)
                color = tv.resources.getColor(R.color.knowledge_0, null)
            }
            else if (know < 4) {
                s = tv.resources.getString(R.string.knowledge_1_3)
                color = tv.resources.getColor(R.color.knowledge_1_3, null)
            }
            else if (know < 7) {
                s = tv.resources.getString(R.string.knowledge_3_6)
                color = tv.resources.getColor(R.color.knowledge_3_6, null)
            }
            else if (know < 10) {
                s = tv.resources.getString(R.string.knowledge_6_9)
                color = tv.resources.getColor(R.color.knowledge_6_9, null)
            }
            else {
                s = tv.resources.getString(R.string.knowledge_awesome)
                color = tv.resources.getColor(R.color.knowledge_master, null)
            }
            tv.text = s
            tv.setTextColor(color)
        }
    }
}