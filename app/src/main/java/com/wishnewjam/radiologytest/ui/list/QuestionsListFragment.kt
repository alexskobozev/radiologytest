@file:Suppress("UnusedImport")

package com.wishnewjam.radiologytest.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

    val args: QuestionsListFragmentArgs by navArgs()

    private val viewModel: QuestionsListViewModel by viewModels {
        InjectorUtils.provideQuestionsListViewModelFactory(requireContext())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding: FragmentQuestionslistBinding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_questionslist, container, false)
        val rootView = binding.root
        registerNavigation()
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        val adapter = QuestionsListAdapter()
        rootView.rv_questions.adapter = adapter
        viewModel.paramsList = args.paramsList
        viewModel.questions.observe(this, Observer<List<QuestionsEntity>> { it ->
            it?.let {
                adapter.questions = it
                viewModel.restorePosition(requireActivity())
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        (view?.rv_questions?.layoutManager as LinearLayoutManager?)?.findFirstVisibleItemPosition()
                ?.let {
                    if (it >= 0) {
                        viewModel.saveCurrentPosition(it, requireContext())
                    }
                }
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

    companion object {
        @BindingAdapter("app:adapterPosition")
        @JvmStatic
        fun adapterPosition(rv: RecyclerView, i: Int?) {
            if (i != null && i > 0 && rv.adapter?.itemCount ?: -1 > i) {
                rv.scrollToPosition(i)
            }
        }
    }
}