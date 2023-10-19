package com.apk.electriccar.ui.fragment

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.apk.electriccar.R
import com.apk.electriccar.R.id.fab_calculator
import com.apk.electriccar.R.id.iv_no_wifi
import com.apk.electriccar.R.id.pb_loading
import com.apk.electriccar.R.id.rv_cars_list
import com.apk.electriccar.R.id.tv_no_wifi
import com.apk.electriccar.data.Garage
import com.apk.electriccar.domain.Car
import com.apk.electriccar.ui.AutonomyCalculateActivity
import com.apk.electriccar.ui.adapter.CarAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.json.JSONArray
import org.json.JSONTokener
import java.net.HttpURLConnection
import java.net.URL

class CarFragment : Fragment() {
    private lateinit var fabGoCalculator: FloatingActionButton
    private lateinit var carList: RecyclerView
    private lateinit var loading: ProgressBar
    private lateinit var noWifiImage: ImageView
    private lateinit var noWifiText: TextView

    var carArray: ArrayList<Car> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.car_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(view)
        setupListeners()
    }

    override fun onResume() {
        super.onResume()
        if(checkForInternet(context)){
            callService()
        }else{
            emptyState()
        }
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

    private fun setupList() {
        val adapter = CarAdapter(carArray)
        carList.isVisible = true
        carList.adapter = adapter
    }

    private fun setupListeners() {
        this.fabGoCalculator.setOnClickListener {
            startActivity(Intent(context, AutonomyCalculateActivity::class.java))
        }
    }

    private fun callService() {
        val urlBase = "https://igorbag.github.io/cars-api/cars.json"
        loading.isVisible = true
        MyTask().execute(urlBase)
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

    inner class MyTask : AsyncTask<String, String, String>() {
        override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun doInBackground(vararg url: String?): String {
            var urlConnection: HttpURLConnection? = null

            try {
                val urlBase = URL(url[0])

                urlConnection = urlBase.openConnection() as HttpURLConnection
                urlConnection.connectTimeout = 60000
                urlConnection.readTimeout = 60000
                urlConnection.setRequestProperty(
                    "accept", "application/json"
                )
                val responseCode = urlConnection.responseCode

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    var response = urlConnection.inputStream.bufferedReader().use {
                        it.readText()
                    }
                    publishProgress(response)
                }else{
                    Log.e("Erro", "Service currently unavaible...")
                }
            } catch (ex: Exception) {
                Log.e("Erro", "Error when processing...")
            } finally {
                urlConnection?.disconnect()
            }
            return ""
        }

        override fun onProgressUpdate(vararg values: String?) {
            try {
                val jsonArray = JSONTokener(values[0]).nextValue() as JSONArray

                for (i in 0 until jsonArray.length()) {
                    val id = jsonArray.getJSONObject(i).getInt("id")
                    val price = jsonArray.getJSONObject(i).getString("preco")
                    val battery = jsonArray.getJSONObject(i).getString("bateria")
                    val power = jsonArray.getJSONObject(i).getString("potencia")
                    val recharge = jsonArray.getJSONObject(i).getString("recarga")
                    val urlImage = jsonArray.getJSONObject(i).getString("urlPhoto")

                    val model = Car(
                        id = id,
                        price = price,
                        battery = battery,
                        power = power,
                        recharge = recharge,
                        urlImage = urlImage
                    )
                    carArray.add(model)
                }
                loading.isVisible = false
                noWifiImage.isVisible = false
                noWifiText.isVisible = false
                setupList()
            } catch (ex: Exception) {
                Log.e("Erro ->", ex.message.toString())
            }
        }
    }
}