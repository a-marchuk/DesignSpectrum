package com.example.designspectrum.data.product

import java.io.Serializable

data class Product(
    var productName: String = "",
    var productDescription: String = "",
    var productPrice: Double = 0.0,
    var productCategory: String = "",
    var productCountry: String = "",
    var quantityInStock: Int = 0,
    var ordersQuantity: Int = 0,
    var productImageId: String = ""
) :Serializable{
    constructor() : this("", "", 0.0, "", "", 0, 0, "")
}



