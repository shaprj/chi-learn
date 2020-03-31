package com.shaprj.chi.learn.models.menu

/*
 * Created by O.Shalaevsky on 5/3/2018
 */
open class MenuItemModel {
    var itemName: String? = null
    var itemIcon: Int = 0

    constructor() {}

    constructor(itemName: String, itemIcon: Int) {
        this.itemName = itemName
        this.itemIcon = itemIcon
    }
}