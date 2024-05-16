package kz.tolegen.mechtatest.ui.feature.detail

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.flow
import kz.tolegen.mechtatest.model.data.local.FavoriteDao
import kz.tolegen.mechtatest.model.data.remote.MechtaApi
import kz.tolegen.mechtatest.model.data.local.dto.FavoriteData
import kz.tolegen.mechtatest.model.entity.SmartphoneDetailData
import kz.tolegen.mechtatest.model.mapToSmartphoneDetailData
import javax.inject.Inject


class DetailRepository @Inject constructor(
    private val api: MechtaApi,
    private val favoriteDao: FavoriteDao,
) {

    @WorkerThread
    fun getSmartphoneDetail(
        code: String,
    ) = flow {
        val smartphoneDetail = api.getSmartphoneDetail(code = code).mapToSmartphoneDetailData()
        emit(smartphoneDetail)
    }

    @WorkerThread
    fun getByCode(
        code: String,
    ) =
        favoriteDao.getByCode(code)

    @WorkerThread
    suspend fun toggleFavorite(isFavorite: Boolean, smartphone: SmartphoneDetailData) {
        if (isFavorite) {
            favoriteDao.delete(smartphone.id.toLong())
        } else {
            favoriteDao.insert(
                FavoriteData(
                    id = smartphone.id.toLong(),
                    code = smartphone.code,
                    title = smartphone.title
                )
            )
        }
    }
}