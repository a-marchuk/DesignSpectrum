package com.example.designspectrum.data

class Product(
    var productName: String = "",
    var productDescription: String = "",
    var productPrice: Int = 0,
    var productCategory: String = "",
    var productCountry: String = "",
    var quantityInStock: Int = 0,
    var ordersQuantity: Int = 0,
    var productImageId: String = ""
) {
    constructor() : this("", "", 0, "", "", 0, 0, "")
}

data class ProductItem(val productName: String, val productImageResId: Int)

