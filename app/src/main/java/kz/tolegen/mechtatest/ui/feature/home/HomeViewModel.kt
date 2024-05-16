package kz.tolegen.mechtatest.ui.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import kz.tolegen.mechtatest.model.entity.SmartphoneData
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
) : ViewModel() {

    var smartphonesState: MutableStateFlow<PagingData<SmartphoneData>> =
        MutableStateFlow(value = PagingData.empty())
        private set

    private val _favorites = MutableStateFlow(hashSetOf<Long>())
    val favorites: StateFlow<HashSet<Long>> = _favorites.asStateFlow()

    init {
        viewModelScope.launch {
            homeRepository.getSmartphones(
            )
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect {
                    smartphonesState.value = it
                }
        }
    }

    fun getFavorites() = viewModelScope.launch(Dispatchers.IO) {
        _favorites.value = homeRepository.getFavorites().map { it.id }.toHashSet()
    }

    fun toggleFavorite(isFavorite: Boolean, smartphone: SmartphoneData) =
        viewModelScope.launch(Dispatchers.IO) {
            homeRepository.toggleFavorite(isFavorite, smartphone)
            getFavorites()
        }
}