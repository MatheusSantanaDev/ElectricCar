package com.apk.electriccar.data.local

import android.provider.BaseColumns

object ContractCar {
    object CarEntry: BaseColumns{
        const val TABLE_NAME = "Car"
        const val COLUMN_NAME_PRICE = "price"
        const val COLUMN_NAME_BATTERY = "battery"
        const val COLUMN_NAME_POWER = "power"
        const val COLUMN_NAME_RECHARGE = "recharge"
        const val COLUMN_NAME_URL_IMAGE = "url_image"
    }

    const val TABLE_CAR =
        "CREATE_TABLE ${CarEntry.TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${CarEntry.COLUMN_NAME_PRICE} TEXT," +
                "${CarEntry.COLUMN_NAME_BATTERY} TEXT," +
                "${CarEntry.COLUMN_NAME_POWER} TEXT," +
                "${CarEntry.COLUMN_NAME_RECHARGE} TEXT," +
                "${CarEntry.COLUMN_NAME_URL_IMAGE} TEXT)"

    const val SQL_DELETE_ENTRIES =
        "DROP TABLES IS EXISTS ${CarEntry.TABLE_NAME}"
}