package com.runetopic.api.tools.shop

import com.runetopic.mongodb.AsyncService
import com.runetopic.mongodb.AsyncStorage

/**
 * @author Jordan Abraham
 */
class ShopService(
    storage: AsyncStorage
) : AsyncService(storage) {

    suspend fun getShops(): List<Shop> {
        return getCollection<Shop>().find().batchSize(100).descendingSort(Shop::released).toList()
    }
}
