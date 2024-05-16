package kz.tolegen.mechtatest.model.data.remote

import kz.tolegen.mechtatest.model.response.GetSmartphoneDetailInfoResponse
import kz.tolegen.mechtatest.model.response.GetSmartphonesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MechtaApi {
    @GET("catalog")
    suspend fun getSmartphonesByPagination(
        @Query("page") page: Int,
        @Query("page_limit") pageLimit: Int = 10,
        @Query("section") section: String = "smartfony",
    ): GetSmartphonesResponse

    @GET("product/{code}")
    suspend fun getSmartphoneDetail(
        @Path("code") code: String,
        @Query("cache_city") cacheCity: String = "s1",
    ): GetSmartphoneDetailInfoResponse
}
