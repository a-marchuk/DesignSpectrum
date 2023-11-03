package com.example.designspectrum.di

import com.example.designspectrum.data.product.ProductRepository
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProductModule {

    @Singleton
    @Provides
    fun provideDatabaseReference() : DatabaseReference{
        return FirebaseDatabase.getInstance().getReference("Products")
    }
    @Singleton
    @Provides
    fun provideStorageReference() : StorageReference{
        return FirebaseStorage.getInstance().getReference("Products images")
    }

    @Singleton
    @Provides
    fun provideProductRepository(
        mDataBase: DatabaseReference,
        mStorageRef: StorageReference
    ) : ProductRepository{
        return ProductRepository(mDataBase, mStorageRef)
    }
}