package com.example.designspectrum.ui

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.designspectrum.R
import com.example.designspectrum.data.Product
import com.example.designspectrum.ui.screens.old.OtherActivity
import com.example.designspectrum.ui.screens.old.products.ShowProductActivity
import com.google.android.material.button.MaterialButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.ByteArrayOutputStream

class AdminSettingsActivity : AppCompatActivity() {
    private lateinit var imageButton: ImageButton
    private lateinit var editTextProductName: EditText
    private lateinit var editTextProductDescription: EditText
    private lateinit var editTextProductPrice: EditText
    private lateinit var editTextProductCategory: EditText
    private lateinit var editTextProductCountryOfOrigin: EditText
    private lateinit var editTextQuantityInStock: EditText
    private lateinit var editTextOrdersQuantity: EditText
    private lateinit var imageViewAdminSettings: ImageView
    private lateinit var buttonChoose: Button
    private lateinit var buttonSave: MaterialButton

    private lateinit var listView: ListView

    private var mStorageRef: StorageReference = FirebaseStorage.getInstance().getReference("Products images")
    private var uploadedImageUri: Uri? = null
    private var mDataBase: DatabaseReference = FirebaseDatabase.getInstance().getReference("Products")

    private var adapter: ArrayAdapter<String>? = null
    private var listData: MutableList<String>? = null
    private var listTemp: MutableList<Product>? = null

    private fun init() {
        imageButton = findViewById(R.id.imageButton)
        editTextProductName = findViewById(R.id.et_product_name_admin_settings)
        editTextProductDescription = findViewById(R.id.et_product_description_admin_settings)
        editTextProductPrice = findViewById(R.id.et_product_price_admin_settings)
        editTextProductCategory = findViewById(R.id.et_product_category_admin_settings)
        editTextProductCountryOfOrigin = findViewById(R.id.et_product_country_of_origin_admin_settings)
        editTextQuantityInStock = findViewById(R.id.et_quantity_in_stock_of_origin_admin_settings)
        editTextOrdersQuantity = findViewById(R.id.et_orders_quantity_of_origin_admin_settings)
        imageViewAdminSettings = findViewById(R.id.iv_admin_settings)
        buttonChoose = findViewById(R.id.btn_choose_admin_settings)
        buttonSave = findViewById(R.id.btn_save_admin_settings)
        listView = findViewById(R.id.itemslist_admin_settings)
        listData = ArrayList()
        listTemp = ArrayList()
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1,
            listData as ArrayList<String>
        )
        listView.adapter = adapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_settings)

        init()

        onClickListeners()

        getDataFromDB()
    }

    private fun onClickListeners() {
        imageButton.setOnClickListener() {
            val intent = Intent(this, OtherActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
            finish()
        }
        buttonChoose.setOnClickListener() {
            getImage()
        }
        buttonSave.setOnClickListener() {
            saveProduct()
        }
        listView.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, view, i, l ->
                val product: Product = listTemp!![i]
                val intent = Intent(this@AdminSettingsActivity, ShowProductActivity::class.java)
                intent.putExtra("product_name", product.productName)
                intent.putExtra("product_description", product.productDescription)
                intent.putExtra("product_price", product.productPrice.toString())
                intent.putExtra("product_image", product.productImageId)
                startActivity(intent)
            }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(0, 0)
        finish()
    }

    private fun getImage() {
        val intentChooser = Intent()
        intentChooser.type = "image/*"
        intentChooser.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intentChooser, 1)
    }

    private var isImageSelected = false
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            Log.d("My log", "Image URI: ${data.data}")
            imageViewAdminSettings.setImageURI(data.data)
            isImageSelected = true
            uploadImage()
        }

    }

    private fun uploadImage() {
        val bitmap = (imageViewAdminSettings.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val byteArray = baos.toByteArray()
        val mRefer = mStorageRef.child("${System.currentTimeMillis()}my image")

        val up = mRefer.putBytes(byteArray)

        val task = up.continueWithTask { taskSnapshot ->
            mRefer.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                if(uploadedImageUri != null){
                    val oldImageRef = FirebaseStorage.getInstance().getReferenceFromUrl(uploadedImageUri.toString())
                    oldImageRef.delete()
                    uploadedImageUri = task.result
                }
                uploadedImageUri = task.result
            }
        }
    }


    private fun saveProduct() {
        val id = mDataBase.push().key
        val productName = editTextProductName.text.toString()
        val productDescription = editTextProductDescription.text.toString()
        val productPrice = editTextProductPrice.text.toString().toIntOrNull() ?: 0
        val productCategory = editTextProductCategory.text.toString()
        val productCountry = editTextProductCountryOfOrigin.text.toString()
        val quantityInStock = editTextQuantityInStock.text.toString().toIntOrNull() ?: 0
        val ordersQuantity = editTextOrdersQuantity.text.toString().toIntOrNull() ?: 0

        if (!productName.isEmpty() && !productDescription.isEmpty() && productPrice > 0 &&
            !productCategory.isEmpty() && !productCountry.isEmpty() && quantityInStock > 0 && ordersQuantity >= 0) {

            if (id != null) {
                val product = Product(productName, productDescription, productPrice, productCategory, productCountry, quantityInStock, ordersQuantity, uploadedImageUri.toString())
                mDataBase.child(id).setValue(product)
                Toast.makeText(this, "Product added", Toast.LENGTH_SHORT).show()

                editTextProductName.text.clear()
                editTextProductDescription.text.clear()
                editTextProductPrice.text.clear()
                editTextProductCategory.text.clear()
                editTextProductCountryOfOrigin.text.clear()
                editTextQuantityInStock.text.clear()
                editTextOrdersQuantity.text.clear()
                uploadedImageUri = null
            }
        } else {
            Toast.makeText(this, "Please fill in all required fields", Toast.LENGTH_SHORT).show()
        }
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
                adapter!!.notifyDataSetChanged()
            }
            override fun onCancelled(error: DatabaseError) {
            }
        }
        mDataBase.addValueEventListener(vListener)
    }

}

