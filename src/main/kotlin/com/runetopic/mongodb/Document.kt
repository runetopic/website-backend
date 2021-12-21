package com.runetopic.mongodb

import org.bson.codecs.pojo.annotations.BsonId

open class Document(
    @BsonId
    open val uuid: String
)
