package com.example.noteBook.layers.data.models

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("Notes")
data class Notes(
    @Id val id: String = ObjectId().toHexString().toString(),
    val title: String,
    val note: String,
    val date: String
)
