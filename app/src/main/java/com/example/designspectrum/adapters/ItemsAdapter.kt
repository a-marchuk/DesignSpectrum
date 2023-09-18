//package com.example.designspectrum.adapters
//
//import android.content.Context
//import android.content.Intent
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Button
//import android.widget.ImageView
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//import com.example.designspectrum.R
//import com.example.designspectrum.data.Product
//import com.example.designspectrum.ui.screens.old.veryold.ItemActivity
//
//
//class ItemsAdapter(var products: List<Product>, var context: Context) : RecyclerView.Adapter<ItemsAdapter.MyViewHolder>(){
//    class MyViewHolder(view: View): RecyclerView.ViewHolder(view){
//        val image: ImageView = view.findViewById(R.id.item_list_image)
//        val title: TextView= view.findViewById(R.id.item_list_title)
//        val desc: TextView = view.findViewById(R.id.item_list_desc)
//        val price: TextView = view.findViewById(R.id.item_list_price)
//        val btn: Button = view.findViewById(R.id.item_list_button)
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_in_list,parent,false)
//        return  MyViewHolder(view)
//    }
//
//    override fun getProductCount(): Int {
//        return items.count()
//    }
//
//    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        holder.title.text = items[position]
//        holder.desc.text = items[position].desc
//        holder.price.text = items[position].price.toString() + "$"
//
//        var imageId = context.resources.getIdentifier(
//            items[position].image,"drawable",context.packageName
//        )
//
//        holder.image.setImageResource(imageId)
//
//        holder.btn.setOnClickListener{
//            val  intent = Intent(context, ItemActivity::class.java)
//
//            intent.putExtra("itemTitle",    items[position].title)
//            intent.putExtra("itemText",    items[position].text)
//
//            context.startActivities(arrayOf(intent))
//        }
//
//    }
//}