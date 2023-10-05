package com.apk.electriccar.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.apk.electriccar.R
import com.apk.electriccar.domain.Car

class CarAdapter(private val cars: List<Car>): RecyclerView.Adapter<CarAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.car_iten, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = cars.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.price.text = cars[position].price
        holder.battery.text = cars[position].battery
        holder.power.text = cars[position].power
        holder.recharge.text = cars[position].recharge
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val price: TextView
        val battery: TextView
        val power: TextView
        val recharge: TextView

        init {
            view.apply {
                price = findViewById(R.id.tv_value_price)
                battery = findViewById(R.id.tv_value_battery)
                power = findViewById(R.id.tv_value_power)
                recharge = findViewById(R.id.tv_value_recharge)
            }
        }
    }
}


