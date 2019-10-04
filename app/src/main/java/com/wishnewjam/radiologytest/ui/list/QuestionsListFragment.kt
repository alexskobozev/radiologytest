@file:Suppress("UnusedImport")

package com.wishnewjam.radiologytest.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.wishnewjam.radiologytest.R
import com.wishnewjam.radiologytest.databinding.FragmentQuestionslistBinding
import com.wishnewjam.radiologytest.db.QuestionsEntity
import com.wishnewjam.radiologytest.utilities.EventListener
import com.wishnewjam.radiologytest.utilities.EventObserver
import com.wishnewjam.radiologytest.utilities.InjectorUtils
import com.wishnewjam.radiologytest.viewmodels.QuestionsListViewModel
import kotlinx.android.synthetic.main.fragment_questionslist.view.*

class QuestionsListFragment : Fragment() {

    private var navController: NavController? = null

    private val viewModel: QuestionsListViewModel by viewModels {
        InjectorUtils.provideQuestionsListViewModelFactory(requireContext())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding: FragmentQuestionslistBinding =
                DataBindingUtil.inflate(inflater,
                        R.layout.fragment_questionslist, container, false)
        val rootView = binding.root
        registerNavigation()
        binding.viewModel = viewModel
        val adapter = QuestionsListAdapter()
        rootView.rv_questions.adapter = adapter
        viewModel.questions.observe(this, Observer<List<QuestionsEntity>> { it ->
            it?.let {
                adapter.questions = it
            }
        })
        (requireActivity() as AppCompatActivity).setSupportActionBar(rootView.toolbar_questionsList)
        rootView.search_view.setOnQueryTextListener(object :
                androidx.appcompat.widget.SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.searchForQuestion(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.searchForQuestion(newText)
                return true
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
                .observe(this, EventObserver(object : EventListener<Int> {
                    override fun onEvent(t: Int) {
                        navigate(t)
                    }
                }))
    }

    private fun navigate(id: Int) {
        navController?.navigate(id)
    }
}