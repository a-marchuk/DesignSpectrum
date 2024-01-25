package com.example.designspectrum.data.product

import android.net.Uri
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class ProductRepository(
    private val mDataBase: DatabaseReference,
    private val mStorageRef: StorageReference
) {
    fun getProducts(): Flow<List<Product>> = callbackFlow {
        val productList = mutableListOf<Product>()

        val vListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                productList.clear()

                for (ds in snapshot.children) {
                    val product = ds.getValue(Product::class.java)
                    product?.let { productList.add(it) }
                }

                trySend(productList)
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }

        mDataBase.addValueEventListener(vListener)

        awaitClose {
            mDataBase.removeEventListener(vListener)
        }
    }

    fun saveProduct(product: Product) {
        val id = mDataBase.push().key
        id?.let {
            mDataBase.child(id).setValue(product)
        }
    }

    suspend fun uploadImage(byteArray: ByteArray): Uri? {
        return withContext(Dispatchers.IO) {
            val mRefer = mStorageRef.child("${System.currentTimeMillis()}my image")
            val up = mRefer.putBytes(byteArray).await()
            val imageUrl = mRefer.downloadUrl.await()
            imageUrl
        }
    }
}
