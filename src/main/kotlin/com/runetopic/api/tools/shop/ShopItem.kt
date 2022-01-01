package com.runetopic.api.tools.shop

data class ShopItem(
    var name: String,
    var numberInStock: Int,
    var restockTime: String,
    var priceSoldAt: Int,
    var priceBoughtAt: Int,
    var gePrice: Int
)
