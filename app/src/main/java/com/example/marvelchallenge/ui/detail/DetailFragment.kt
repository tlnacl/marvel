package com.example.marvelchallenge.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.marvelchallenge.MainApplication
import com.example.marvelchallenge.R
import com.example.marvelchallenge.databinding.FragmentDetailBinding
import com.example.marvelchallenge.di.provideViewModel
import com.example.marvelchallenge.network.Character
import com.example.marvelchallenge.uniflow.data.ViewEvent
import com.example.marvelchallenge.uniflow.data.ViewState
import com.example.marvelchallenge.uniflow.onEvents
import com.example.marvelchallenge.uniflow.onStates
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class DetailFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: DetailViewModel
    private lateinit var binding: FragmentDetailBinding
    val args: DetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().application as MainApplication).appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = provideViewModel(viewModelFactory)
        binding.toolbar.title = getString(R.string.title_details)
        binding.toolbar.setNavigationOnClickListener { activity?.onBackPressed() }
        onStates(viewModel) { viewState ->
            when (viewState) {
                is ViewState.Loading -> renderLoading()
                is DetailViewModel.DetailViewState -> renderCharacter(viewState.character)
                is ViewState.Failed -> renderFailed()
            }
        }
        onEvents(viewModel) { viewEvent ->
            when (viewEvent) {
                is ViewEvent.ApiError -> Snackbar.make(view, viewEvent.message, Snackbar.LENGTH_LONG).show()
            }
        }

        viewModel.loadCharacter(args.characterId)
    }

    private fun renderFailed() {
        binding.progressBar.isVisible = false
        binding.retry.isVisible = true
    }

    private fun renderCharacter(character: Character) {
        binding.progressBar.isVisible = false
        binding.retry.isVisible = false
        binding.image.load(character.thumbnail.landscapeAmazing())
        binding.name.text = character.name
        binding.description.text = character.description
    }

    private fun renderLoading() {
        binding.progressBar.isVisible = true
        binding.retry.isVisible = false
    }
}