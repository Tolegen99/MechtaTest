package kz.tolegen.mechtatest.model.response

import com.google.gson.annotations.SerializedName

data class GetSmartphonesResponse(
    @SerializedName("data") val data: DataResponse,
    @SerializedName("errors") val errors: List<String>,
    @SerializedName("result") val result: Boolean,
) {
    data class DataResponse(
        @SerializedName("all_items_count") val allItemsCount: Int,
        @SerializedName("items") val items: List<ItemResponse>,
        @SerializedName("page_items_count") val pageItemsCount: Int,
        @SerializedName("page_number") val pageNumber: Int,
    ) {
        data class ItemResponse(
            @SerializedName("code") val code: String,
            @SerializedName("id") val id: Int,
            @SerializedName("photos") val photos: List<String>,
            @SerializedName("price") val price: Int,
            @SerializedName("title") val title: String,
        )
    }
}
