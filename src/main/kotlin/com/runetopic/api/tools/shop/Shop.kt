package com.runetopic.api.tools.shop

import com.fasterxml.jackson.annotation.JsonFormat
import com.runetopic.api.tools.obj.Obj
import com.runetopic.mongodb.Document
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id
import org.litote.kmongo.newId
import java.util.*

/**
 * @author Jordan Abraham
 */
data class Shop(
    @BsonId
    val uuid: Id<Obj> = newId(),
    var title: String,
    @JsonFormat(pattern = "dd MMMM yyyy")
    var released: Date,
    var isMembers: Boolean,
    var owner: String,
    var location: String,
    var specialty: String,
    var stock: MutableList<ShopItem>
) : Document
