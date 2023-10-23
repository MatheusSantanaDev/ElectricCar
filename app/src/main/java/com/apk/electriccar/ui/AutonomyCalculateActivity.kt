package com.apk.electriccar.ui

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.apk.electriccar.R
import com.apk.electriccar.R.layout.activity_autonomy_calculate

class AutonomyCalculateActivity : AppCompatActivity() {

    private lateinit var price: EditText
    private lateinit var kmTraveled: EditText
    private lateinit var bCalculate: Button
    private lateinit var result: TextView
    private lateinit var bBack: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_autonomy_calculate)
        setupView()
        setupListeners()
        setupCachedCalc()
    }

    private fun setupCachedCalc(){
        val calculatedValue = getSharedValue()
        result.text = calculatedValue.toString()
    }

    private fun setupView() {
        price = findViewById(R.id.et_kwh_price)
        kmTraveled = findViewById(R.id.et_km_traveled)
        bCalculate = findViewById(R.id.b_calculate)
        result = findViewById(R.id.tv_result)
        bBack = findViewById(R.id.iv_back)
    }

    private fun setupListeners() {
        bCalculate.setOnClickListener {
            calculate()
        }

        bBack.setOnClickListener {
            finish()
        }
    }

    private fun calculate() {
        val price = price.text.toString().toFloat()
        val km = kmTraveled.text.toString().toFloat()
        val calc = price / km

        result.text = calc.toString()
        saveSharedValue(calc)
    }

    private fun saveSharedValue(result: Float){
        val sharedPref = getPreferences(Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()){
            putFloat(getString(R.string.saved_value), result)
            apply()
        }
    }

    private fun getSharedValue(): Float{
        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        return sharedPref.getFloat(getString(R.string.saved_value), 0.0f)
    }
}