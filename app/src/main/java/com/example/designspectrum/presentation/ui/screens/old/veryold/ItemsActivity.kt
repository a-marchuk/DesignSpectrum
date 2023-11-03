package com.example.designspectrum.presentation.ui.screens.old.veryold

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.designspectrum.R
import com.example.designspectrum.data.old.Item


class ItemsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_items)


        val itemsList: RecyclerView = findViewById(R.id.itemslist)
        val items = arrayListOf<Item>()

        items.add(Item(1, "sofa", "Диван", "Lorem ipsum dolor sit amet", "cons", 290))
        items.add(Item(2, "chair", "Стілець", "Lorem ipsum dolor sit amet", "cons", 367))
        items.add(Item(3, "table", "Стіл", "Lorem ipsum dolor sit amet", "cons", 432))


        itemsList.layoutManager = LinearLayoutManager(this)
    }
}