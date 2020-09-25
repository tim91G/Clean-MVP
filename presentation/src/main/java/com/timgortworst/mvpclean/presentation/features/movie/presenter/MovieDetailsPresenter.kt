package com.timgortworst.mvpclean.presentation.features.movie.presenter

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.timgortworst.mvpclean.domain.model.state.State
import com.timgortworst.mvpclean.domain.usecase.moviedetail.GetMovieDetailsUseCase
import com.timgortworst.mvpclean.domain.usecase.moviedetail.GetMovieDetailsUseCaseImpl
import com.timgortworst.mvpclean.presentation.extension.cancelIfActive
import com.timgortworst.mvpclean.presentation.features.movie.view.MovieDetailsView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MovieDetailsPresenter(
    private val view: MovieDetailsView,
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase
) : CoroutineScope, DefaultLifecycleObserver {

    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    init {
        if (view is LifecycleOwner) {
            (view as LifecycleOwner).lifecycle.addObserver(this)
        }
    }

    override fun onCreate(owner: LifecycleOwner) {
        job = Job()
    }

    override fun onDestroy(owner: LifecycleOwner) {
        job.cancel()
    }

    fun fetchMovieDetails(movieId: Int) {
        job.cancelIfActive()

        job = launch {
            when (val res = getMovieDetailsUseCase.execute(GetMovieDetailsUseCaseImpl.Params(movieId))) {
                State.Loading -> {
                    view.showLoading()
                    view.hideError()
                    view.hideResult()
                }
                is State.Success -> {
                    view.hideLoading()
                    view.hideError()
                    view.showResult(res.data)
                }
                is State.Error -> {
                    view.hideLoading()
                    view.showError("Check your internet")
                    view.hideResult()
                }
            }
        }
    }
}
