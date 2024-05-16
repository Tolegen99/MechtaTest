package kz.tolegen.mechtatest.model.entity

data class SmartphoneDetailData(
    val code: String,
    val id: Int,
    val mainProperties: List<MainPropertyData>,
    val photos: List<String>,
    val price: Int,
    val title: String,
)