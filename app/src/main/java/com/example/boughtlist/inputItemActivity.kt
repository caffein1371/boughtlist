package com.example.boughtlist

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_input_item.*

class inputItemActivity : AppCompatActivity() {
    private lateinit var realm: Realm

    var Genrename = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_item)
        realm = Realm.getDefaultInstance()
        var genre = intent.getLongExtra("item_genre", -1)
        var itemid = intent.getLongExtra("item_ID", -1)
        var priceflag = intent.getIntExtra("price_flag", 1)
        var gram = intent.getIntExtra("gram", 0)
        var gramyen = intent.getIntExtra("gramyen", 0)
        var amount = intent.getIntExtra("amount", 0)
        var amountyen = intent.getIntExtra("amountyen", 0)
        var otheryen = intent.getIntExtra("otheryen", 0)
        var saleflag = intent.getIntExtra("sale_flag", 1)
        var itemname = intent.getStringExtra("item_name")
        var placename = intent.getStringExtra("place_name")
        println("-----inputItemActivity Log-----")
        println("Genre = " + genre.toString())
        println("itemid = " + itemid.toString())
        println("itemname = " + itemname.toString())

        setSupportActionBar(findViewById(R.id.toolbar))
        Genrename = intent.getStringExtra("genre_name")
        if (Genrename.isEmpty()) {
            supportActionBar?.setTitle("新規登録")
        } else {
            supportActionBar?.setTitle(Genrename + "のジャンル")
        }
        inputitembar.setText(itemname)
        inputPlace.setText(placename)

        var chooseinput = findViewById<RadioGroup>(R.id.choose_how_input)
        var inputid = chooseinput.checkedRadioButtonId
        var chooseprice = findViewById<RadioGroup>(R.id.choose_price)
        //chooseprice.check(R.id.normalPrice)
        var priceid = chooseprice.checkedRadioButtonId

        //初期状態の設定
        if (priceflag.toInt() == -1) {
            chooseinput.check(R.id.chooseGram)
            visibleswitch(-1)
        } else if (priceflag.toInt() == 1) {
            chooseinput.check(R.id.chooseGram)
            visibleswitch(1)
            //inputitembar.setText(itemname)
            //inputPlace.setText(placename)
            if(gram.toString().toInt()!=0) {
                gramInput.setText(gram.toString())
                gramYen.setText(gramyen.toString())
            }
        } else if (priceflag.toInt() == 2) {
            chooseinput.check(R.id.chooseAmount)
            visibleswitch(2)
            //inputitembar.setText(itemname)
            //inputPlace.setText(placename)
            koInput.setText(amount.toString())
            koYen.setText(amountyen.toString())

        } else if (priceflag.toInt() == 3) {
            chooseinput.check(R.id.chooseOther)
            visibleswitch(3)
            //inputitembar.setText(itemname)
            //inputPlace.setText(placename)
            otherYenInput.setText(otheryen.toString())
        }
        if (saleflag.toInt() == -1) {
            chooseprice.check(R.id.normalPrice)
        } else if (saleflag.toInt() == 1) {
            chooseprice.check(R.id.normalPrice)
        } else if (saleflag.toInt() == 2) {
            chooseprice.check(R.id.forSale)
        } else if (saleflag.toInt() == 3) {
            chooseprice.check(R.id.tokubai)
        }

        chooseinput.setOnCheckedChangeListener { group, checkedId ->
            val howtoinput = findViewById<RadioButton>(checkedId)
            println(howtoinput.toString() + "が選択されています")
            //フラグから
            if (howtoinput == chooseGram) {
                visibleswitch(1)
                priceflag = 1
            } else if (howtoinput == chooseAmount) {
                visibleswitch(2)
                priceflag = 2
            } else if (howtoinput == chooseOther) {
                visibleswitch(3)
                priceflag = 3
            }
        }

        chooseprice.setOnCheckedChangeListener { group, checkedId ->
            val howtoprice = findViewById<RadioButton>(checkedId)
            if (howtoprice == normalPrice) {
                saleflag = 1
            } else if (howtoprice == forSale) {
                saleflag = 2
            } else if (howtoprice == tokubai) {
                saleflag = 3
            }
        }

        registerbutton.setOnClickListener { view: View ->
            if (inputitembar.text.toString() == "") {
                AlertDialog.Builder(this)
                    .setTitle("Error")
                    .setMessage("商品名を入力してください")
                    .setPositiveButton("OK") { dialog, which -> }
                    .show()
            } else if (inputPlace.text.toString() == "") {
                AlertDialog.Builder(this)
                    .setTitle("Error")
                    .setMessage("購入店を入力してください")
                    .setPositiveButton("OK") { dialog, which -> }
                    .show()
            } else if (itemid.toInt() == -1) {
                realm.executeTransaction { db: Realm ->
                    val maxId = db.where<InputItem>().max("id")
                    val nextId = (maxId?.toLong() ?: 0L) + 1
                    val inputItem = db.createObject<InputItem>(nextId)
                    //val date = inputdate.text.toString().toDate("yyyy/MM/dd")
                    //if (date!= null) addItem.date = date
                    inputItem.itemname = inputitembar.text.toString()
                    inputItem.genreId = genre
                    inputItem.priceflag = priceflag
                    if (priceflag == 1) {
                        inputItem.gram = gramInput.text.toString().toInt()
                        inputItem.gramyen = gramYen.text.toString().toInt()
                    } else if (priceflag == 2) {
                        inputItem.amount = koInput.text.toString().toInt()
                        inputItem.amountyen = koYen.text.toString().toInt()
                    } else if (priceflag == 3) {
                        inputItem.otheryen = otherYenInput.text.toString().toInt()
                    }
                    inputItem.saleflag = saleflag
                    inputItem.place = inputPlace.text.toString()
                    finish()
//                    Snackbar.make(view, "追加しました", Snackbar.LENGTH_LONG)
//                        .setAction("戻る") { finish() }
//                        .setActionTextColor(Color.YELLOW)
//                        .show()
                }
            } else {
                realm.executeTransaction { db: Realm ->
                    var inputItem = db.where<InputItem>().equalTo("id", itemid)
                        ?.findFirst()
                    inputItem?.itemname = inputitembar.text.toString()
                    inputItem?.genreId = genre
                    inputItem?.priceflag = priceflag
                    if (priceflag == 1) {
                        inputItem?.gram = gramInput.text.toString().toInt()
                        inputItem?.gramyen = gramYen.text.toString().toInt()
                    } else if (priceflag == 2) {
                        inputItem?.amount = koInput.text.toString().toInt()
                        inputItem?.amountyen = koYen.text.toString().toInt()
                    } else if (priceflag == 3) {
                        inputItem?.otheryen = otherYenInput.text.toString().toInt()
                    }
                    inputItem?.saleflag = saleflag
                    inputItem?.place = inputPlace.text.toString()
                    Snackbar.make(view, "更新しました", Snackbar.LENGTH_LONG)
                        .setAction("戻る") { finish() }
                        .setActionTextColor(Color.YELLOW)
                        .show()
                }
                println("登録名は" + inputitembar.text.toString())
                println("登録ジャンルは" + genre)
                println("購入場所は" + inputPlace.text.toString())
            }
        }
        deletebutton.setOnClickListener { view: View ->
            if (itemid.toInt() == -1) {
                AlertDialog.Builder(this)
                    .setTitle("Error")
                    .setMessage("登録しないと削除できません")
                    .setPositiveButton("OK") { dialog, which -> }
                    .show()
            } else if (itemid.toInt() != -1) {
                AlertDialog.Builder(this)
                    .setTitle("警告")
                    .setMessage("商品を削除しますか？")
                    .setPositiveButton("Yes") { dialog, which ->
                        realm.executeTransaction { db: Realm ->
                            db.where<InputItem>().equalTo("id", itemid)
                                ?.findFirst()
                                ?.deleteFromRealm()
                            finish()
                        }
                    }
                    .setNegativeButton("No") { dialog, which -> }
                    .show()
//                realm.executeTransaction { db: Realm ->
//                    db.where<InputItem>().equalTo("id", itemid)
//                        ?.findFirst()
//                        ?.deleteFromRealm()
//                    finish()
            }
        }
    }
         private fun visibleswitch(switch: Int?) {
            when (switch) {
                1, -1 -> {
                    gramInput.visibility = View.VISIBLE
                    gramtext.visibility = View.VISIBLE
                    gramYen.visibility = View.VISIBLE
                    gramyentext.visibility = View.VISIBLE
                    koInput.visibility = View.INVISIBLE
                    kotext.visibility = View.INVISIBLE
                    koYen.visibility = View.INVISIBLE
                    koyentext.visibility = View.INVISIBLE
                    otherYenInput.visibility = View.INVISIBLE
                    otheryen.visibility = View.INVISIBLE
                }
                2 -> {
                    gramInput.visibility = View.INVISIBLE
                    gramtext.visibility = View.INVISIBLE
                    gramYen.visibility = View.INVISIBLE
                    gramyentext.visibility = View.INVISIBLE
                    koInput.visibility = View.VISIBLE
                    kotext.visibility = View.VISIBLE
                    koYen.visibility = View.VISIBLE
                    koyentext.visibility = View.VISIBLE
                    otherYenInput.visibility = View.INVISIBLE
                    otheryen.visibility = View.INVISIBLE

                }
                3 -> {
                    gramInput.visibility = View.INVISIBLE
                    gramtext.visibility = View.INVISIBLE
                    gramYen.visibility = View.INVISIBLE
                    gramyentext.visibility = View.INVISIBLE
                    koInput.visibility = View.INVISIBLE
                    kotext.visibility = View.INVISIBLE
                    koYen.visibility = View.INVISIBLE
                    koyentext.visibility = View.INVISIBLE
                    otherYenInput.visibility = View.VISIBLE
                    otheryen.visibility = View.VISIBLE
                }
            }
            return
        }
    }
