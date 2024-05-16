package kz.tolegen.mechtatest.ui.feature.home

import androidx.annotation.WorkerThread
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kz.tolegen.mechtatest.model.data.local.FavoriteDao
import kz.tolegen.mechtatest.model.data.remote.MechtaApi
import kz.tolegen.mechtatest.model.data.local.dto.FavoriteData
import kz.tolegen.mechtatest.model.entity.SmartphoneData
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val api: MechtaApi,
    private val favoriteDao: FavoriteDao,
) {

    @WorkerThread
    fun getSmartphones(
    ): Flow<PagingData<SmartphoneData>> {
        return Pager(
            config = PagingConfig(pageSize = 100, prefetchDistance = 8),
            pagingSourceFactory = {
                SmartphonePagingSource(api)
            }
        ).flow
    }

    @WorkerThread
    suspend fun getFavorites() = favoriteDao.getAll()

    @WorkerThread
    suspend fun toggleFavorite(isFavorite: Boolean, smartphone: SmartphoneData) {
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
