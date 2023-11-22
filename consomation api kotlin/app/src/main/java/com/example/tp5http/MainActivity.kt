package com.example.tp5http

import MyAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class MainActivity : AppCompatActivity() {
    lateinit var showResult: RecyclerView
    lateinit var editTextText: EditText
    lateinit var button: Button
    lateinit var AddBtn:Button
    lateinit var UpdateBtn:Button
    lateinit var DeleteBtn:Button
    lateinit var OffreList:List<Offre>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        OffreList=emptyList()
        val recyclerView: RecyclerView = findViewById(R.id.recycleView)
        val myAdapter = MyAdapter(OffreList)
        val manager = LinearLayoutManager(this)
        recyclerView.adapter = myAdapter
        recyclerView.layoutManager=manager

        editTextText = findViewById(R.id.editTextText)
        button = findViewById(R.id.button)
        AddBtn=findViewById(R.id.AddBtn)
        UpdateBtn=findViewById(R.id.UpdateBtn)
        DeleteBtn=findViewById(R.id.DeleteBtn)

        val scope = CoroutineScope(Dispatchers.Main)
        scope.launch {
            getAllOffres()
            myAdapter.updateData(OffreList)
            button.setOnClickListener {
                lifecycleScope.launch {
                    getOffreById(editTextText.text.toString().toInt())
                    myAdapter.updateData(OffreList)
                }
            }
            AddBtn.setOnClickListener {
                lifecycleScope.launch {
                      addOffre()
                    myAdapter.updateData(OffreList)
                }
            }
            UpdateBtn.setOnClickListener {
                lifecycleScope.launch {
                    updateOffre(editTextText.text.toString().toInt())
                    myAdapter.updateData(OffreList)
                }
            }
            DeleteBtn.setOnClickListener {
                lifecycleScope.launch {
                    deleteOffre(editTextText.text.toString().toInt())
                    myAdapter.updateData(OffreList)
                }
            }
        }
    }
    private suspend fun getAllOffres() {
        try {
            val response = ApiClient.apiService.getOffres()
            if (response.isSuccessful && response.body() != null) {
                Log.i("Success", response.body().toString())
                OffreList = response.body()!!
            } else {
                Log.e("Error", response.message())
            }
        } catch (e: Exception) {
            Log.e("Error", e.message.toString())
        }
    }

    private suspend fun getOffreById(id: Int) {
        try {
            val response = ApiClient.apiService.getOffreById(id)
            if (response.isSuccessful && response.body() != null) {
                Log.i("Success", response.body().toString())
                getAllOffres()
            } else {
                Log.e("Error", response.message())
            }
        } catch (e: Exception) {
            Log.e("Error", e.message.toString())
        }
    }

    private suspend fun addOffre() {
        val offre_new = Offre(789, "aa", "aa", "aa", 10, "tunis")
        val response = ApiClient.apiService.AddOffre(offre_new)
        if (response.isSuccessful) {
            Log.i("Success", "Added successfully")
            Toast.makeText(this@MainActivity,"added succesfully",Toast.LENGTH_SHORT).show()
            getAllOffres()
        } else {
            Log.e("Error", response.message())
        }
    }

    private suspend fun updateOffre(id: Int) {
        val offre_new = Offre(null, "aa", "aa", "aa", 10, "tunis")
        val response = ApiClient.apiService.UpdateOffre(id, offre_new)
        if (response.isSuccessful) {
            Log.i("Success", "Updated successfully")
            Toast.makeText(this@MainActivity,"updated succesfully",Toast.LENGTH_SHORT).show()
            getAllOffres()
        } else {
            Log.e("Error", response.message())
        }
    }

    private suspend fun deleteOffre(id: Int) {
        try {
            val response = ApiClient.apiService.DeleteOffre(id)
            if (response.isSuccessful) {
                Log.i("Success", "Deleted successfully")
                Toast.makeText(this@MainActivity,"deleted succesfully",Toast.LENGTH_SHORT).show()
                getAllOffres()
            } else {
                Log.e("Error", response.message())
            }
        } catch (e: Exception) {
            Log.e("Error", e.message.toString())
        }
    }
    private fun showAddUpdateDialog(title: String, callback: (Offre) -> Unit) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_update_offre, null)
        val dialogBuilder = AlertDialog.Builder(this)
            .setTitle(title)
            .setView(dialogView)
            .setPositiveButton("Save") { dialog, which->
                val newOffre = Offre(
                    code = dialogView.findViewById<EditText>(R.id.editTextCode).text.toString().toInt(),
                    intitulé =dialogView.findViewById<EditText>(R.id.editTextintitule).text.toString(),
                    nbpostes =dialogView.findViewById<EditText>(R.id.editTextNbpostes).text.toString().toInt(),
                    specialité =dialogView.findViewById<EditText>(R.id.editTextSpecialite).text.toString(),
                    société =dialogView.findViewById<EditText>(R.id.editTextsociete).text.toString(),
                    pays = dialogView.findViewById<EditText>(R.id.editTextPays).text.toString()
                )
                callback.invoke(newOffre)
            }
            .setNegativeButton("Cancel", null)
            .create()

        dialogBuilder.show()
    }
}
