package com.binar.sqliteapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab_add.setOnClickListener {
            val gotoAddActivity = Intent(this, AddActivity::class.java)
            startActivity(gotoAddActivity)
        }
    }

    override fun onResume() {
        super.onResume()
        fetchData()
    }


    fun fetchData(){
        val db = DatabaseHandler(this)
        val listInventory = arrayListOf<Inventory>()
        listInventory.addAll(db.readAllInventory())
        val adapter = InventoryAdapter(listInventory)
        rvContainer.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        rvContainer.adapter = adapter
    }
}