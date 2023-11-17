package com.example.tp5http

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class MainActivity : AppCompatActivity() {
    lateinit var showResult: TextView
    lateinit var editTextText: EditText
    lateinit var button: Button
    lateinit var AddBtn:Button
    lateinit var UpdateBtn:Button
    lateinit var DeleteBtn:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showResult = findViewById(R.id.showResult)
        editTextText = findViewById(R.id.editTextText)
        button = findViewById(R.id.button)
        AddBtn=findViewById(R.id.AddBtn)
        UpdateBtn=findViewById(R.id.UpdateBtn)
        DeleteBtn=findViewById(R.id.DeleteBtn)
        val scope = CoroutineScope(Dispatchers.Main)
        scope.launch {
            getAllOffres()
            button.setOnClickListener {
                lifecycleScope.launch {
                    getOffreById(editTextText.text.toString().toInt())
                }
            }
            AddBtn.setOnClickListener {
                lifecycleScope.launch {
                      addOffre()
                }
            }
            UpdateBtn.setOnClickListener {
                lifecycleScope.launch {
                    updateOffre(editTextText.text.toString().toInt())
                }
            }
            DeleteBtn.setOnClickListener {
                lifecycleScope.launch {
                    deleteOffre(editTextText.text.toString().toInt())
                }
            }
        }
    }
    private suspend fun getAllOffres() {
        try {
            val response = ApiClient.apiService.getOffres()
            if (response.isSuccessful && response.body() != null) {
                Log.i("Success", response.body().toString())
                showResult.text = response.body().toString()
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
                showResult.text = response.body().toString()
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
}
