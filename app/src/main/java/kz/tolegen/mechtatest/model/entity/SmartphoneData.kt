package kz.tolegen.mechtatest.model.entity

data class SmartphoneData(
    val code: String,
    val id: Int,
    val photos: List<String>,
    val price: Int,
    val title: String,
)