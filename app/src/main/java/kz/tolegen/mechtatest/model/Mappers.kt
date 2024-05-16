package kz.tolegen.mechtatest.model

import kz.tolegen.mechtatest.model.entity.MainPropertyData
import kz.tolegen.mechtatest.model.entity.SmartphoneData
import kz.tolegen.mechtatest.model.entity.SmartphoneDetailData
import kz.tolegen.mechtatest.model.entity.SmartphonesPagingData
import kz.tolegen.mechtatest.model.response.GetSmartphoneDetailInfoResponse
import kz.tolegen.mechtatest.model.response.GetSmartphonesResponse

fun GetSmartphonesResponse.mapToSmartphonesPagingData(): SmartphonesPagingData {
    return SmartphonesPagingData(
        allItemsCount = this.data.allItemsCount,
        items = this.data.items.map { it.mapToSmartphoneData() },
        pageItemsCount = this.data.pageItemsCount,
        pageNumber = this.data.pageNumber,
    )
}

private fun GetSmartphonesResponse.DataResponse.ItemResponse.mapToSmartphoneData(): SmartphoneData {
    return SmartphoneData(
        code = this.code,
        id = this.id,
        photos = this.photos,
        price = this.price,
        title = this.title,
    )
}

fun GetSmartphoneDetailInfoResponse.mapToSmartphoneDetailData(): SmartphoneDetailData {
    return SmartphoneDetailData(
        code = this.data.code,
        id = this.data.id,
        mainProperties = this.data.mainProperties.map { it.mapToMainPropertyData() },
        photos = this.data.photos,
        price = this.data.price,
        title = this.data.title,
    )
}

private fun GetSmartphoneDetailInfoResponse.DataResponse.MainPropertyResponse.mapToMainPropertyData(): MainPropertyData {
    return MainPropertyData(
        propId = this.propId,
        propName = this.propName,
        propValue = this.propValue,
    )
}