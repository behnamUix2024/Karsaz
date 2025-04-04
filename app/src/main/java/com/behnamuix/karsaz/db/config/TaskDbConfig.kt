package com.behnamuix.karsaz.db.config

import androidx.room.Database
import androidx.room.RoomDatabase
import com.behnamuix.karsaz.db.model.Tasks

@Database(entities = [Tasks::class], version = 1)
abstract class TaskDbConfig:RoomDatabase() {


}
