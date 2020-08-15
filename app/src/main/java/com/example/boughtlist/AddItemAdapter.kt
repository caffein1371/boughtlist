package com.example.boughtlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter

class AddItemAdapter(data: OrderedRealmCollection<AddItem>) :
    RealmRecyclerViewAdapter<AddItem,AddItemAdapter.ViewHolder>(data,true){

    private var listener: ((AddItem?)-> Unit)? = null

    fun setOnItemClickListener(listener: (AddItem?)-> Unit){
        this.listener = listener
    }

    init {
        setHasStableIds(true)
    }

    class ViewHolder(cell: View) : RecyclerView.ViewHolder(cell){
        //val date : TextView = cell.findViewById(android.R.id.text1)
        //val item : TextView = cell.findViewById(android.R.id.text2)
        val item : TextView = cell.findViewById(android.R.id.text1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddItemAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(android.R.layout.simple_list_item_1,parent,false)
        //val view = inflater.inflate(android.R.layout.simple_list_item_2,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: AddItemAdapter.ViewHolder, position: Int) {
        val addItem: AddItem? = getItem(position)
        //holder.date.text = DateFormat.format("yyyy/MM/dd",addItem?.date)
        holder.item.text = addItem?.item
        holder.itemView.setOnClickListener {
            listener?.invoke(addItem)
            //listener?.invoke(addItem?.id)
        }
        //リストの項目が作られるたびに呼び出されるメソッド
    }

    override fun getItemId(position: Int): Long {
        return getItem(position)?.id ?:0
    }

}