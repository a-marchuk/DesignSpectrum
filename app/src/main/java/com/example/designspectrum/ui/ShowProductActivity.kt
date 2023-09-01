package com.example.designspectrum.ui

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.designspectrum.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso


class ShowProductActivity : AppCompatActivity() {
    private lateinit var tvProductName: TextView
    private lateinit var tvProductDescription: TextView
    private lateinit var tvProductPrice: TextView
    private lateinit var imageViewBD: ImageView
    private lateinit var btProductDelete: Button

    val database = FirebaseDatabase.getInstance()
    val reference = database.getReference("Products")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_product)
        init()
        getIntentMain()

        onClickListeners()

    }


    private fun init(){
        tvProductName=findViewById(R.id.tvProductName)
        tvProductDescription=findViewById(R.id.tvProductDescription)
        tvProductPrice=findViewById(R.id.tvProductPrice)
        imageViewBD = findViewById(R.id.imageViewBD)
        btProductDelete = findViewById(R.id.btProductDelete)
    }

    private fun getIntentMain() {
        val myIntent = intent
        if (myIntent != null) {
            Picasso.get().load(intent.getStringExtra("product_image")).into(imageViewBD)
            tvProductName.setText(intent.getStringExtra("product_name"))
            tvProductDescription.setText(intent.getStringExtra("product_description"))
            tvProductPrice.setText(intent.getStringExtra("product_price"))
        }
    }
    private fun onClickListeners(){
        btProductDelete.setOnClickListener {
            val myIntent = intent
            if (myIntent != null) {
                val productImageUri = myIntent.getStringExtra("product_image")
                if (productImageUri != null) {
//                    val productRef = FirebaseDatabase.getInstance().reference.child("your_data_node").child(productId)

                    val productImageToFind = productImageUri

                    reference.orderByChild("productImageId").equalTo(productImageToFind)
                        .addListenerForSingleValueEvent(object :
                            ValueEventListener {
                            override fun onDataChange(dataSnapshot: DataSnapshot) {
                                for (productSnapshot in dataSnapshot.children) {
                                    val productId = productSnapshot.key
                                    if (productId != null) {
                                        val productRef = FirebaseDatabase.getInstance().reference.child("Products").child(productId)
                                        productRef.removeValue().addOnSuccessListener {
                                            val imageRef = FirebaseStorage.getInstance().getReferenceFromUrl(productImageToFind.toString())
                                            imageRef.delete().addOnSuccessListener {
//
                                                finish()
                                            }.addOnFailureListener { imageDeleteError ->

                                                Log.e(
                                                    "ImageDeleteError",
                                                    "Error deleting image: $imageDeleteError"
                                                )
                                            }.addOnFailureListener { productDeleteError ->

                                                Log.e(
                                                    "ProductDeleteError",
                                                    "Error deleting product: $productDeleteError"
                                                )
                                            }
                                        }
                                    }
                                }
                            }

                            override fun onCancelled(databaseError: DatabaseError) {
                                Log.e("FirebaseError", "Error - : ${databaseError.message}")
                            }
                        })
                }
            }
        }
    }

}