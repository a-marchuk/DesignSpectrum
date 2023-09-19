package com.example.designspectrum.ui.fragments.shopp.maison

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.designspectrum.R
import com.example.designspectrum.data.Product
import com.example.designspectrum.ui.screens.old.products.ShowProductActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MaisonFragment : Fragment(R.layout.fragment_maison) {

    private lateinit var listView: ListView
    private var adapter: ArrayAdapter<String>? = null
    private var listData: MutableList<String>? = null
    private var listTemp: MutableList<Product>? = null

    private var mDataBase: DatabaseReference = FirebaseDatabase.getInstance().getReference("Products")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_maison, container, false)
        listView = view.findViewById(R.id.item_list_maison)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()

        getDataFromDB()

        clickListeners()

    }

    private fun clickListeners(){
        listView.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, i, _ ->
                val product: Product = listTemp!![i]
                val intent = Intent(requireActivity(), ShowProductActivity::class.java)
                    .setAction("transition to show product")
                intent.putExtra("product_name", product.productName)
                intent.putExtra("product_description", product.productDescription)
                intent.putExtra("product_price", product.productPrice.toString())
                intent.putExtra("product_image", product.productImageId)
                startActivity(intent)
            }
    }
    fun init() {
        listData = ArrayList()
        listTemp = ArrayList()
        adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, listData as ArrayList<String>)
        listView.adapter = adapter
    }

    private fun getDataFromDB() {
        val vListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (listData!!.size > 0) listData!!.clear()
                if (listTemp!!.size > 0) listTemp!!.clear()

                for (ds in snapshot.children) {
                    val product = ds.getValue(Product::class.java)
                    requireNotNull(product)
                    listData!!.add(product.productName)
                    listTemp!!.add(product)
                }
                adapter?.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }
        mDataBase.addValueEventListener(vListener)
    }
}

