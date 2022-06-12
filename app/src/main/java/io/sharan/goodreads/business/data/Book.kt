package io.sharan.goodreads.business.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class Book(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val title: String,
    val author: String,
    val price: Float,
    val amount: Int,
    var curImageUrl: String = ""
)