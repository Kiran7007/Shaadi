package com.peopleinteractive.shaadi.ui.people

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.peopleinteractive.shaadi.databinding.PeopleFragmentBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class PeopleFragment : Fragment() {

    companion object {
        fun newInstance() = PeopleFragment()
    }

    private val viewModel by viewModel<PeopleViewModel>()
    private lateinit var binding: PeopleFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PeopleFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observerState()
        showPeoples()
    }

    private fun showPeoples() {
        lifecycleScope.launch {
            viewModel.peopleIntent.send(PeopleIntent.FetchRemotePeople)
        }
    }

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
                    }
                    is PeopleState.PeopleData -> {
                    }
                }
            }
        }
    }
}