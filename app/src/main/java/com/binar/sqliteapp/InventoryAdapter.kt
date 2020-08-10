package com.binar.sqliteapp

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.inventory_item.view.*

class InventoryAdapter (val listInventory: ArrayList<Inventory>): RecyclerView.Adapter<InventoryAdapter.ViewHolder>() {
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.inventory_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return listInventory.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.tvInventoryName.text = listInventory[position].name
        holder.itemView.tvInventoryTotal.text = listInventory[position].total.toString()

        holder.itemView.btnEdit.setOnClickListener {
            val intentEditInventoryActivity = Intent(it.context, EditActivity::class.java)

            intentEditInventoryActivity.putExtra("inventory",listInventory[position])

            it.context.startActivity(intentEditInventoryActivity)
        }

        holder.itemView.btnDelete.setOnClickListener {
            AlertDialog.Builder(it.context).setPositiveButton("Ya"){ dialogInterface: DialogInterface, i: Int ->
                val dbHandler = DatabaseHandler(holder.itemView.context)

                if (dbHandler.deleteInventory(listInventory[position])!=0){
                    Toast.makeText(it.context,"Data ${listInventory[position].name} berhasil dihapus",
                        Toast.LENGTH_LONG).show()
                    (it.context as MainActivity).fetchData()
                } else {
                    Toast.makeText(it.context,"Data ${listInventory[position].name} Gagal dihapus",
                        Toast.LENGTH_LONG).show()
                }
            }.setNegativeButton("Tidak"){ dialogInterface: DialogInterface, i: Int ->
                dialogInterface.dismiss()
            }
                .setMessage("Apakah kamu yakin ingin menghapus data ${listInventory[position].name}?")
                .setTitle("Konfirmasi Hapus")
                .create()
                .show()
        }
    }
}