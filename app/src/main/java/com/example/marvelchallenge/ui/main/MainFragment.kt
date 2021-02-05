package com.example.marvelchallenge.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.marvelchallenge.MainApplication
import com.example.marvelchallenge.R
import com.example.marvelchallenge.databinding.FragmentMainBinding
import com.example.marvelchallenge.di.provideViewModel
import com.example.marvelchallenge.network.Character
import com.example.marvelchallenge.uniflow.data.ViewEvent
import com.example.marvelchallenge.uniflow.data.ViewState
import com.example.marvelchallenge.uniflow.onEvents
import com.example.marvelchallenge.uniflow.onStates
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber
import javax.inject.Inject

class MainFragment : Fragment(), CharacterViewHolder.CharacterCallback {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: MainViewModel

    private lateinit var characterAdapter: CharacterAdapter
    private lateinit var binding: FragmentMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().application as MainApplication).appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = GridLayoutManager(activity, 2)
        characterAdapter = CharacterAdapter(this)
        binding.recyclerView.adapter = characterAdapter
        binding.recyclerView.layoutManager = layoutManager
        binding.retry.setOnClickListener { viewModel.loadCharacters() }

        viewModel = provideViewModel(viewModelFactory)
        onStates(viewModel) { viewState ->
            when (viewState) {
                is ViewState.Loading -> renderLoading()
                is MainViewModel.MainViewState -> renderCharacters(viewState.characters)
                is ViewState.Failed -> renderFailed()
            }
        }
        onEvents(viewModel) { viewEvent ->
            when (viewEvent) {
                is ViewEvent.ApiError -> Snackbar.make(view, viewEvent.message, Snackbar.LENGTH_LONG).show()
            }
        }

        viewModel.loadCharacters()
    }

    private fun renderFailed() {
        binding.progressBar.isVisible = false
        binding.retry.isVisible = true
    }

    private fun renderCharacters(characters: List<Character>) {
        binding.progressBar.isVisible = false
        binding.retry.isVisible = false
        characterAdapter.setItems(characters)
    }

    private fun renderLoading() {
        binding.progressBar.isVisible = true
        binding.retry.isVisible = false
    }

    override fun onCharacterClicked(character: Character) {
        findNavController().navigate(MainFragmentDirections.actionMainFragmentToDetailFragment(character.id))
    }
}