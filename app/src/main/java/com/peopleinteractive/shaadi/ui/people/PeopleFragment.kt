package com.peopleinteractive.shaadi.ui.people

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.peopleinteractive.shaadi.adapters.PeopleAdapter
import com.peopleinteractive.shaadi.data.db.entity.People
import com.peopleinteractive.shaadi.databinding.PeopleFragmentBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * PeopleFragment shows the people list.
 */
class PeopleFragment : Fragment() {

    /**
     * PeopleViewModel injected bu dependency injection.
     */
    private val viewModel by viewModel<PeopleViewModel>()

    /**
     * Binder to bind data with the view.
     */
    private lateinit var binding: PeopleFragmentBinding

    /**
     * Converts the simple data into view and set to the recycler view.
     */
    private lateinit var adapter: PeopleAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PeopleFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observerPeoples()
        observerState()
    }

    /**
     * Initialize the view.
     */
    private fun initView() {
        adapter = PeopleAdapter(viewModel)
        binding.recyclerView.adapter = adapter
    }

    /**
     * Observes the peoples data and set to the recycler view.
     */
    private fun observerPeoples() {
        viewModel.peoples.observe(viewLifecycleOwner, Observer<List<People>> {
            if (!it.isNullOrEmpty()) {
                binding.tvEmpty.visibility = View.GONE
            }
            adapter.submitList(it)
        })
    }

    /**
     * Observe the states.
     */
    private fun observerState() {
        lifecycleScope.launch {
            viewModel.state.collect {
                when (it) {
                    is PeopleState.Idle -> {
                        viewModel.peopleIntent.send(PeopleIntent.FetchLocalPeople)
                    }
                    is PeopleState.Loading -> {
                    }
                    is PeopleState.Error -> {
                        it.message?.let { message -> shoToast(message) }
                    }
                }
            }
        }
    }

    /**
     * Shows message.
     */
    private fun shoToast(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }
}