package com.behnamuix.karsaz.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Tasks(
    @PrimaryKey(autoGenerate = true) val id:Int=0,
    val title:String,
    val tags:String,
    val parity:String,
    val time:String,
    val date:String,
    val category:String,
    val desc:String
)
