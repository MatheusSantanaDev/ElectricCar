package com.apk.electriccar.data.local

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.apk.electriccar.R
import com.apk.electriccar.domain.Car

class CarRepository(private val context: Context) {
    fun save(car: Car): Boolean{
        var isSaved = false

        try {
            val dbHelper = CarDBHelper(context)
            val db = dbHelper.writableDatabase

            val values = ContentValues().apply {
                put(ContractCar.CarEntry.COLUMN_NAME_PRICE, car.price)
                put(ContractCar.CarEntry.COLUMN_NAME_BATTERY, car.battery)
                put(ContractCar.CarEntry.COLUMN_NAME_POWER, car.power)
                put(ContractCar.CarEntry.COLUMN_NAME_RECHARGE, car.recharge)
                put(ContractCar.CarEntry.COLUMN_NAME_URL_IMAGE, car.urlImage)
            }
            val inserted = db?.insert(ContractCar.CarEntry.TABLE_NAME, null, values)

            if (inserted != null){
                isSaved = true
            }

        } catch (ex: Exception){
            ex.message?.let {
                Log.e(context.getString(R.string.error_data), it)
            }
        }
        return isSaved
    }
}