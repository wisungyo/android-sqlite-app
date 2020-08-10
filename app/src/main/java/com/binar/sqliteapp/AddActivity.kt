package com.binar.sqliteapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add.*

class AddActivity : AppCompatActivity() {
    private lateinit var db: DatabaseHandler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        db = DatabaseHandler(this)

        btn_add.setOnClickListener {
            if (et_name.text.isNotEmpty() && et_total.text.isNotEmpty()) {

                val student = Inventory(null, et_name.text.toString(), et_total.text.toString())
                val rowInserted = db.insertInventory(student)
                Log.d("INSERT DATA", "TOTAL : $rowInserted")
                if (rowInserted!! > 0) {
                    Toast.makeText(this, "Data Inserted", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "Data not Inserted", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, "Data cannot be empty", Toast.LENGTH_LONG).show()
            }
        }
    }
}