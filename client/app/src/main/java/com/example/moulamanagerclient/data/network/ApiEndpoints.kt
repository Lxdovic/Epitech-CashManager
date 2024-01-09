package com.example.moulamanagerclient.data.network

object ApiEndpoints {
    const val BASE_URL = "http://10.0.2.2:8080"
    const val LOGIN = "auth/sign-in"
    const val REGISTER = "auth/sign-up"
    const val PRODUCTS = "products"
    const val CART_ITEMS_BARCODE = "carts/items/barcode/{barcode}"
    const val PRODUCTS_BY_NAME = "products/name/{name}"
    const val BARCODE = "products/barcode/{barcode}"
    const val CREATE_PAYMENT_INTENT = "create-payment-intent"
    const val GET_CART_ITEM = "carts/items/me"
    const val UPDATE_CART_ITEM = "/carts/items/{productId}"
    const val DELETE_CART_ITEM = "/carts/items/{productId}"
}