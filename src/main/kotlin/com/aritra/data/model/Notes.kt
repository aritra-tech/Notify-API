package com.aritra.data.model

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class Notes (
    @BsonId
    var id: String = ObjectId().toString(),
    val title: String,
    val description: String,
)