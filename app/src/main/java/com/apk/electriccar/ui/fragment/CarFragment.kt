package com.apk.electriccar.ui.fragment

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.apk.electriccar.R
import com.apk.electriccar.R.id.fab_calculator
import com.apk.electriccar.R.id.iv_no_wifi
import com.apk.electriccar.R.id.pb_loading
import com.apk.electriccar.R.id.rv_cars_list
import com.apk.electriccar.R.id.tv_no_wifi
import com.apk.electriccar.data.CarsApi
import com.apk.electriccar.domain.Car
import com.apk.electriccar.ui.AutonomyCalculateActivity
import com.apk.electriccar.ui.adapter.CarAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CarFragment : Fragment() {
    private lateinit var fabGoCalculator: FloatingActionButton
    private lateinit var carList: RecyclerView
    private lateinit var loading: ProgressBar
    private lateinit var noWifiImage: ImageView
    private lateinit var noWifiText: TextView
    private lateinit var carsApi: CarsApi

    var carArray: ArrayList<Car> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.car_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRetrofit()
        setupView(view)
        setupListeners()
    }

    override fun onResume() {
        super.onResume()
        if(checkForInternet(context)){
            getAllCars()
        }else{
            emptyState()
        }
    }

    private fun setupRetrofit(){
        val retrofit = Retrofit.Builder()
            .baseUrl("https://igorbag.github.io/cars-api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        carsApi = retrofit.create(CarsApi::class.java)
    }

    private fun getAllCars(){
        carsApi.getAllCars().enqueue(object: Callback<List<Car>>{
            override fun onResponse(call: Call<List<Car>>, response: Response<List<Car>>) {
                if(response.isSuccessful){
                    loading.isVisible = false
                    noWifiImage.isVisible = false
                    noWifiText.isVisible = false

                    response.body()?.let {
                        setupList(it)
                    }
                }else{
                    Toast.makeText(context, R.string.response_error, Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<List<Car>>, t: Throwable) {
                Toast.makeText(context, R.string.response_error, Toast.LENGTH_LONG).show()
            }

        })
    }

    private fun emptyState(){
        loading.isVisible = false
        carList.isVisible = false
        noWifiImage.isVisible = true
        noWifiText.isVisible = true
    }

    private fun setupView(view: View) {
        view.apply {
            fabGoCalculator = findViewById(fab_calculator)
            carList = findViewById(rv_cars_list)
            loading = findViewById(pb_loading)
            noWifiText = findViewById(tv_no_wifi)
            noWifiImage = findViewById(iv_no_wifi)
        }
    }

    private fun setupList(carArray: List<Car>) {
        val adapter = CarAdapter(carArray)
        carList.isVisible = true
        carList.adapter = adapter
    }

    private fun setupListeners() {
        this.fabGoCalculator.setOnClickListener {
            startActivity(Intent(context, AutonomyCalculateActivity::class.java))
        }
    }

    private fun checkForInternet(context: Context?): Boolean{
        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            val network = connectivityManager.activeNetwork ?: return false
            val activityNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when{
                activityNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activityNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else{
            @Suppress("DEPRECATION")
            val networkInfo = connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }

}