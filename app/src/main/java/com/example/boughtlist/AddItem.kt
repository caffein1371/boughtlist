package com.example.boughtlist

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class AddItem : RealmObject() {
    @PrimaryKey
    var id: Long =0
    var date: Date = Date()
    var item: String =""
}