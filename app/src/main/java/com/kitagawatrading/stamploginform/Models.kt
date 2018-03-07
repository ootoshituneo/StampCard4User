package com.kitagawatrading.stamploginform

/**
 * Created by ootoshi on 2018/02/27.
 */

class FromServer(val user: User)
class User(val id: Int, val username: String, val email: String, val uid: String)
class Errors(val error: Boolean, val message: String)
class Shop(val shopData: List<ShopData>)
class ShopData(val shopId: String, val shopName: String, val stampCount: String)
class StampShop(val user: Array<String>)
class StampShopData(val shopName: String)
