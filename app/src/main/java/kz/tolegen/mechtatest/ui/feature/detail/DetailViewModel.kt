package kz.tolegen.mechtatest.ui.feature.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import kz.tolegen.mechtatest.NavScreen
import kz.tolegen.mechtatest.model.entity.SmartphoneDetailData
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val stateHandle: SavedStateHandle,
    private val detailRepository: DetailRepository,
) : ViewModel() {
    private val smartphoneCodeSharedFlow: MutableSharedFlow<String> = MutableSharedFlow(replay = 1)

    @OptIn(ExperimentalCoroutinesApi::class)
    val smartphoneDetailFlow = smartphoneCodeSharedFlow.flatMapLatest {
        detailRepository.getSmartphoneDetail(code = it)
    }
    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val smartphoneCode = stateHandle.get<String>(NavScreen.Detail.smartphone_code)
            smartphoneCode?.let {
                getSmartphoneDetail(it)
                _isFavorite.value = getFavoriteByCode(it) != null
            }
        }
    }

    fun getSmartphoneDetail(code: String) = smartphoneCodeSharedFlow.tryEmit(code)
    fun getFavoriteByCode(code: String) = detailRepository.getByCode(code)
    fun toggleFavorite(smartphone: SmartphoneDetailData) =
        viewModelScope.launch(Dispatchers.IO) {
            detailRepository.toggleFavorite(_isFavorite.value, smartphone)
            _isFavorite.value = !_isFavorite.value
        }
}