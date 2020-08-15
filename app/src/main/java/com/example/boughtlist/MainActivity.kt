package com.example.boughtlist

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import io.realm.Realm
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        val itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        addlist.addItemDecoration(itemDecoration)
        realm = Realm.getDefaultInstance()
        addlist.layoutManager = LinearLayoutManager(this)
        val itemList = realm.where<AddItem>().findAll()
        val adapter = AddItemAdapter(itemList)
        addlist.adapter = adapter

        adapter.setOnItemClickListener {additem->
            var itemID = additem?.id
            var itemName = additem?.item
            val intent = Intent(this,buyListAddActivity::class.java)
                intent.putExtra("genre_ID",itemID)
                intent.putExtra("genre_name",itemName)

            startActivity(intent)
        }

        add_item.setOnClickListener { view ->
            val intent = Intent(this,AddItemActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}