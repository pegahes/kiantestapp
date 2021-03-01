package com.example.kiantest.ui.list

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.example.kiantest.data.MovieDBRepositiry

class MovieListViewModel @ViewModelInject constructor(
    private val repository: MovieDBRepositiry,
    @Assisted state: SavedStateHandle
) : ViewModel() {

    private val current_date_gte = state.getLiveData(current_value_date_gte, default_value_date_gte)
    private val current_date_lte = state.getLiveData(current_value_date_lte, default_value_date_lte)

    val movielist = Transformations.switchMap(
        DoubleTrigger(
            current_date_gte,
            current_date_lte
        )
    ) {
        repository.getSearchResults(it.first, it.second).cachedIn(viewModelScope)

    }

    fun searchMovieList(date_gte: String?, date_lte: String?) {
        current_date_gte.value = date_gte
        current_date_lte.value = date_lte
    }

    companion object {

        // these are the default date values for the movie list,
        // at first run, the app will show movies in a range of time
        const val default_value_date_gte = "1920-01-01"
        const val current_value_date_gte = "current_value_date_gte"
        const val default_value_date_lte = "2021-01-01"
        const val current_value_date_lte = "current_value_date_lte"
    }
}

class DoubleTrigger<A, B>(a: LiveData<A>, b: LiveData<B>) : MediatorLiveData<Pair<A?, B?>>() {
    init {
        addSource(a) { value = it to b.value }
        addSource(b) { value = a.value to it }
    }
}