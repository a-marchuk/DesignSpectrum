package com.example.designspectrum.presentation.ui.fragments.shopp.catalog

import androidx.fragment.app.Fragment
import com.example.designspectrum.R


class CatalogFragment : Fragment(R.layout.fragment_catalog) {

//    private var adapter: ProductAdapter? = null
//    private val listData: MutableList<Product> = mutableListOf()
//
//    private val mDataBase: DatabaseReference = FirebaseDatabase.getInstance().getReference("Products")
//
//override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//    super.onViewCreated(view, savedInstanceState)
//
//    val itemsList: RecyclerView = view.findViewById(R.id.item_list_catalog)
//
//    adapter = ProductAdapter(listData)
//
//    val spanCount = 2
//    val layoutManager = GridLayoutManager(context, spanCount)
//    itemsList.layoutManager = layoutManager
//    val spacingInPixels = resources.getDimensionPixelSize(R.dimen.grid_spacing)
//    itemsList.addItemDecoration(GridSpacingItemDecoration(spanCount, spacingInPixels, true))
//
//    itemsList.adapter = adapter
//
//    getDataFromDB()
//}
//
//
//    private fun getDataFromDB() {
//        val vListener = object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                listData.clear()
//
//                for (ds in snapshot.children) {
//                    val product = ds.getValue(Product::class.java)
//                    requireNotNull(product)
//                    listData.add(product)
//                }
//
//                adapter?.notifyDataSetChanged()
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//            }
//        }
//        mDataBase.addValueEventListener(vListener)
//    }
}