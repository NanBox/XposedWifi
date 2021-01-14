package com.nanbox.xposedwifi

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private val etSSID by lazy { findViewById<EditText>(R.id.et_ssid) }
    private val etBSSID by lazy { findViewById<EditText>(R.id.et_bssid) }
    private val btnSave by lazy { findViewById<Button>(R.id.btn_save) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        SPUtil.getSSID()?.let {
            etSSID.setText(it)
        }

        SPUtil.getBSSID()?.let {
            etBSSID.setText(it)
        }

        btnSave.setOnClickListener {
            val ssid = etSSID.text.toString()
            val bssid = etBSSID.text.toString()
            SPUtil.setSSID(ssid)
            SPUtil.setBSSID(bssid)
            Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}