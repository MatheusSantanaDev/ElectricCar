package com.apk.electriccar.ui

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
        val count = price / km

        result.text = count.toString()
    }
}