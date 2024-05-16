package kz.tolegen.mechtatest.ui.feature.home

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kz.tolegen.mechtatest.model.data.remote.MechtaApi
import kz.tolegen.mechtatest.model.entity.SmartphoneData
import kz.tolegen.mechtatest.model.mapToSmartphonesPagingData
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SmartphonePagingSource @Inject constructor(
    private val api: MechtaApi,
) : PagingSource<Int, SmartphoneData>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SmartphoneData> {
        return try {
            val currentPage = params.key ?: 1
            val smartphones = api.getSmartphonesByPagination(
                page = currentPage
            ).mapToSmartphonesPagingData()
            LoadResult.Page(
                data = smartphones.items,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (smartphones.items.isEmpty()) null else smartphones.pageNumber + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, SmartphoneData>): Int? {
        return state.anchorPosition
    }
}