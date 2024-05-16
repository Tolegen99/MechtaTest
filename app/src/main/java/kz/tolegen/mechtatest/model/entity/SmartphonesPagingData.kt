package kz.tolegen.mechtatest.model.entity

data class SmartphonesPagingData(
    val allItemsCount: Int,
    val items: List<SmartphoneData>,
    val pageItemsCount: Int,
    val pageNumber: Int,
)