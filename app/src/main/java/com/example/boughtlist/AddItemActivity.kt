package com.example.boughtlist

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import com.example.boughtlist.R.id.container
import com.google.android.material.snackbar.Snackbar
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_item_add.*
import java.lang.IllegalArgumentException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class AddItemActivity : AppCompatActivity() {
    private lateinit var realm: Realm


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_add)

        realm = Realm.getDefaultInstance()

        addbutton.setOnClickListener { view: View ->
            if (AddItemName.text.toString() == "") {
                AlertDialog.Builder(this)
                    .setTitle("Error")
                    .setMessage("入力してください")
                    .setPositiveButton("OK"){dialog,which -> }
                    .show()
            } else {
                realm.executeTransaction { db: Realm ->
                    val maxId = db.where<AddItem>().max("id")
                    val nextId = (maxId?.toLong() ?: 0L) + 1
                    val addItem = db.createObject<AddItem>(nextId)
                    //val date = inputdate.text.toString().toDate("yyyy/MM/dd")
                    //if (date!= null) addItem.date = date
                    addItem.item = AddItemName.text.toString()
                    finish()
                }
//                Snackbar.make(view, "追加しました", Snackbar.LENGTH_SHORT)
//                    .setAction("戻る") { finish() }
//                    .setActionTextColor(Color.YELLOW)
//                    .show()
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }


    private fun String.toDate(pattern: String= "yyyy/MM/dd HH:mm"): Date?{
        return try {
            SimpleDateFormat(pattern).parse(this)
        }catch (e: IllegalArgumentException){
            return null
        }catch (e:ParseException){
            return null
        }
    }

}