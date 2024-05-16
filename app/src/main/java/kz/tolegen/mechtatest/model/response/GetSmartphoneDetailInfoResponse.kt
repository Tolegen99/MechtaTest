package kz.tolegen.mechtatest.model.response

import com.google.gson.annotations.SerializedName

data class GetSmartphoneDetailInfoResponse(
    @SerializedName("data") val data: DataResponse,
    @SerializedName("errors") val errors: List<String>,
    @SerializedName("result") val result: Boolean,
) {
    data class DataResponse(
        @SerializedName("code") val code: String,
        @SerializedName("id") val id: Int,
        @SerializedName("main_properties") val mainProperties: List<MainPropertyResponse>,
        @SerializedName("photos") val photos: List<String>,
        @SerializedName("price") val price: Int,
        @SerializedName("title") val title: String,
    ) {
        data class MainPropertyResponse(
            @SerializedName("prop_id") val propId: Int,
            @SerializedName("prop_name") val propName: String,
            @SerializedName("prop_value") val propValue: String,
        )
    }
}