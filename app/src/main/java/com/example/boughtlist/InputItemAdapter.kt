package com.example.boughtlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter

class InputItemAdapter(data: OrderedRealmCollection<InputItem>) :
    RealmRecyclerViewAdapter<InputItem, InputItemAdapter.ViewHolder>(data,true){

    private var listener: ((InputItem?)-> Unit)? = null

    fun setOnItemClickListener(listener: (InputItem?)-> Unit){
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

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InputItemAdapter.ViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(android.R.layout.simple_list_item_1,parent,false)
            //val view2=inflater.inflate(android.R.layout.sim)
            //val view = inflater.inflate(android.R.layout.simple_list_item_2,parent,false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: InputItemAdapter.ViewHolder, position: Int) {
            val inputItem: InputItem? = getItem(position)
            //holder.date.text = DateFormat.format("yyyy/MM/dd",addItem?.date)
            if(inputItem?.priceflag==1) {
                holder.item.text =
                    inputItem?.itemname + "   " + inputItem?.gram + "g" + inputItem?.gramyen + "円" + "(" + inputItem?.place + ")"
            }else if(inputItem?.priceflag==2){
                holder.item.text =
                    inputItem?.itemname + "   " + inputItem?.amount + "コ" + inputItem?.amountyen + "円" + "(" + inputItem?.place + ")"
            }else if(inputItem?.priceflag==3){
                holder.item.text =
                    inputItem?.itemname + "   " + inputItem?.otheryen +"円" + "(" + inputItem?.place + ")"
            }
            holder.itemView.setOnClickListener {
                listener?.invoke(inputItem)
                //listener?.invoke(addItem?.id)
            }
            //リストの項目が作られるたびに呼び出されるメソッド
        }
        override fun getItemId(position: Int): Long {
            return getItem(position)?.id ?:0
        }

}