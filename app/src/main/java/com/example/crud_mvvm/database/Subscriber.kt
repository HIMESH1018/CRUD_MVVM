package com.example.crud_mvvm.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "subscriber_data_table")
data class Subscriber(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "subscriber_ID")
    var id:Int,
    @ColumnInfo(name = "subscriber_name")
    var name:String,
    @ColumnInfo(name = "subscriber_email")
    var email:String
)
