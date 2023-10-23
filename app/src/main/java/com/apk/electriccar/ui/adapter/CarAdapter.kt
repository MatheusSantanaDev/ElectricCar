package com.apk.electriccar.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.apk.electriccar.R
import com.apk.electriccar.domain.Car

class CarAdapter(private val cars: List<Car>): RecyclerView.Adapter<CarAdapter.ViewHolder>() {

    var carItemListener: (Car) -> Unit = {}

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
        holder.favorite.setOnClickListener() {
            val car = cars[position]
            carItemListener(car)
            setupFavorite(car, holder)
        }
    }
    private fun setupFavorite(car: Car, holder: ViewHolder){
        car.isFavorite = !car.isFavorite
        if(car.isFavorite)
            holder.favorite.setImageResource(R.drawable.ic_baseline_star_selected)
        else
            holder.favorite.setImageResource(R.drawable.ic_baseline_star)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val price: TextView
        val battery: TextView
        val power: TextView
        val recharge: TextView
        val favorite: ImageView

        init {
            view.apply {
                price = findViewById(R.id.tv_value_price)
                battery = findViewById(R.id.tv_value_battery)
                power = findViewById(R.id.tv_value_power)
                recharge = findViewById(R.id.tv_value_recharge)
                favorite = findViewById(R.id.iv_favorite)
            }
        }
    }
}


