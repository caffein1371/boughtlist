package com.example.boughtlist

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import io.realm.Realm
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_buy_list_add2.*

class buyListAddActivity : AppCompatActivity() {
    private lateinit var realm: Realm
    var genreID:Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buy_list_add2)
        setSupportActionBar(findViewById(R.id.toolbar))
        realm = Realm.getDefaultInstance()
        val itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        buylist.addItemDecoration(itemDecoration)

        genreID = intent.getLongExtra("genre_ID",0)
        println("genreID = "+genreID.toString())
        var genrename = intent.getStringExtra("genre_name")
        supportActionBar?.setTitle(genrename)

        buylist.layoutManager = LinearLayoutManager(this)
        //genreidと同じものだけを表示
        val itemList = realm.where<InputItem>().equalTo("genreId",genreID).findAll()
        val adapter = InputItemAdapter(itemList)
        buylist.adapter = adapter

        adapter.setOnItemClickListener {inputitem->
            var itemID = inputitem?.id
            var priceFlag = inputitem?.priceflag
            var gram = inputitem?.gram
            var gramyen = inputitem?.gramyen
            var amount = inputitem?.amount
            var amountyen = inputitem?.amountyen
            var otheryen = inputitem?.otheryen
            var saleFlag = inputitem?.saleflag
            var itemName = inputitem?.itemname
            var placeName = inputitem?.place
            //var genre = inputitem?.genreId
            //var itemName = inputitem?.itemname
            println(genreID)
            val intent = Intent(this,inputItemActivity::class.java)
            intent.putExtra("item_ID",itemID)
            intent.putExtra("genre_name",genrename)
            intent.putExtra("item_genre",genreID)
            intent.putExtra("price_flag",priceFlag)
            intent.putExtra("gram",gram)
            intent.putExtra("gramyen",gramyen)
            intent.putExtra("amount",amount)
            intent.putExtra("amountyen",amountyen)
            intent.putExtra("otheryen",otheryen)
            intent.putExtra("sale_flag",saleFlag)
            intent.putExtra("item_name",itemName)
            intent.putExtra("place_name",placeName)
            println("itemname"+itemName)
            println("gramyen"+gramyen)
            println("amountyen"+amountyen)
            println("otheryen"+otheryen)
            startActivity(intent)
        }

        add_item.setOnClickListener { view ->
            val intent = Intent(this,inputItemActivity::class.java)
            println(genreID)
            intent.putExtra("item_ID",(-1).toLong())
            intent.putExtra("item_genre",genreID)
            intent.putExtra("genre_name",genrename)
            intent.putExtra("item_name","")
            startActivity(intent)
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_buylistadd, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            R.id.action_delete -> {
                realm.executeTransaction { db: Realm ->
                    println(genreID)
                    db.where<AddItem>().equalTo("id", genreID)
                        ?.findFirst()
                        ?.deleteFromRealm()
                    finish()
                }
//                Snackbar.make(view, "削除しました", Snackbar.LENGTH_SHORT)
//                    .setAction("戻る") { finish() }
//                    .setActionTextColor(Color.YELLOW)
//                    .show()
//            }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

