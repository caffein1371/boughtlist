package com.example.boughtlist

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class InputItem : RealmObject() {
    @PrimaryKey
    var id :Long = 0
    var genreId:Long = 0
    var itemname :String =""
    var priceflag :Int = 0
    var gram :Int = 0
    var gramyen : Int =0
    var amount:Int = 0
    var amountyen:Int = 0
    var otheryen :Int = 0
    var place:String =""
    var saleflag:Int = 0
}