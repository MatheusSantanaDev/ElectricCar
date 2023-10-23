package com.apk.electriccar.data

import com.apk.electriccar.domain.Car

object Garage {
    val list = listOf<Car>(
        Car(
            id = 1,
            price = "R$ 79.000,00",
            battery = "200 kW",
            power = "600 cv",
            recharge = "110 min",
            urlImage = "www.peganobreu.com",
            isFavorite = false
        ),

    )
}