package hu.bme.aut.android.myideas.ui.newIdea

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.android.myideas.NavigationHost
import hu.bme.aut.android.myideas.R
import hu.bme.aut.android.myideas.models.domain.Idea
import hu.bme.aut.android.myideas.ui.newIdea.dataState.NewIdeaDataState
import kotlinx.android.synthetic.main.fragment_new_idea.*
import kotlinx.android.synthetic.main.layout_new_idea.*
import kotlinx.android.synthetic.main.layout_new_idea.view.*

@AndroidEntryPoint
class NewIdeaFragment : Fragment() {

    companion object {
        const val LOADING = 0
        const val NEW_IDEA_SCREEN = 1
        const val ERROR_SCREEN = 2

        private const val IDEA = "IDEA"

        @JvmStatic
        fun newInstance(
            idea: Idea
        ) =
            NewIdeaFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(IDEA, idea)
                }
            }
    }

    private val viewModel: NewIdeaViewModel by viewModels()
    private var idea: Idea? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            idea = it.getParcelable(IDEA)
        }
        subscribeObservers()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_idea, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()

        view.newIdeaTitleEditText.setText(idea?.title)
        view.newIdeaShortDescriptionEditText.setText(idea?.shortDescription)

        view.newIdeaSaveButton.setOnClickListener {
            viewModel.setStateEvent(
                NewIdeaStateEvent.SaveIdea(
                    Idea(
                        id = idea?.id ?: "",
                        title = view.newIdeaTitleEditText.text.toString(),
                        shortDescription = view.newIdeaShortDescriptionEditText.text.toString(),
                        description = ""
                    )
                )
            )
        }

        view.newIdeaCancelButton.setOnClickListener {
            view.newIdeaTitleEditText.setText("")
            view.newIdeaShortDescriptionEditText.setText("")
        }

        viewModel.setStateEvent(NewIdeaStateEvent.LoadNewIdeaScreen)
    }

    private fun setupToolbar() {
        newIdeaToolbar.setNavigationOnClickListener {
            (activity as NavigationHost).navigateBack()
        }
    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(this) { dataState ->
            when (dataState) {
                is NewIdeaDataState.Success<Unit> -> {
                    setViewFlipper(NEW_IDEA_SCREEN)
                }
                is NewIdeaDataState.SuccessfulCreation<Unit> -> {
                    (activity as NavigationHost).navigateBack()
                }
                is NewIdeaDataState.SuccessfulUpdate<Unit> -> {
                    (activity as NavigationHost).navigateBack()
                }
                is NewIdeaDataState.Error -> {
                    setViewFlipper(ERROR_SCREEN)
                }
                is NewIdeaDataState.Loading -> {
                    setViewFlipper(LOADING)
                }
            }
        }
    }

    private fun setViewFlipper(displayedState: Int) {
        fragment_new_idea_view_flipper.displayedChild = displayedState
    }
}