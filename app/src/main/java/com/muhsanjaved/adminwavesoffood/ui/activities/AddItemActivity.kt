package com.muhsanjaved.adminwavesoffood.ui.activities

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.muhsanjaved.adminwavesoffood.databinding.ActivityAddItemBinding
import com.muhsanjaved.adminwavesoffood.models.AllMenu

class AddItemActivity : AppCompatActivity() {

    // Food item Details
    private lateinit var foodName :String
    private lateinit var foodPrice :String
    private lateinit var foodDescription :String
    private lateinit var foodIngredient :String
    private var foodImageUri : Uri? = null

    // Firebase
    private lateinit var auth :FirebaseAuth
    private lateinit var database:FirebaseDatabase

    private val binding: ActivityAddItemBinding by lazy {
        ActivityAddItemBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        //Initialize Firebase
        auth = FirebaseAuth.getInstance()
        // Initialize Firebaase database Instnace
        database = FirebaseDatabase.getInstance()

        // Finish Activity
        binding.backButton.setOnClickListener {
            finish()
        }
        ///
        binding.addItemButton.setOnClickListener {
            // GET Data form Filed EditText
            foodName = binding.editTextFoodName.text.toString().trim()
            foodPrice = binding.edittextFoodPrice.text.toString().trim()
            foodDescription= binding.editTextDescription.text.toString().trim()
            foodIngredient = binding.editTextIngredients.text.toString().trim()

            if (!(foodName.isBlank() || foodPrice.isBlank() || foodDescription.isBlank()|| foodIngredient.isBlank())){
                uploadData()
                Toast.makeText(this,"Item Add Successfully",Toast.LENGTH_SHORT).show()
                finish()
            }else {
                Toast.makeText(this,"Fill All the Details",Toast.LENGTH_SHORT).show()
            }
        }

        // Image
        binding.selectImage.setOnClickListener {
            pickImage.launch("image/*")
        }

    }

    private val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            binding.selectedImageView.setImageURI(uri)
            foodImageUri = uri
        }
    }

    private fun uploadData() {

        // get a reference to the "Menu" node in the database
        val menuRef = database.getReference("menu")
        // Genrate a unqique key for the new menu item
        val newItemKey = menuRef.push().key

        if (foodImageUri != null){

            val storageRef = FirebaseStorage.getInstance().reference
            val imageRef = storageRef.child("menu_images/${newItemKey}.jpg")
            val uploadTask = imageRef.putFile(foodImageUri!!)

            uploadTask.addOnSuccessListener {
                imageRef.downloadUrl.addOnSuccessListener {
                    downloadUrl->
                    // Create  a new mneu item
                    val newItem = AllMenu(
                        foodName,
                        foodPrice,
                        foodDescription,
                        foodIngredient,
                        downloadUrl.toString()
                    )
                    newItemKey?.let {
                        key->
                        menuRef.child(key).setValue(newItem).addOnSuccessListener {
                            Toast.makeText(this,"Data Uploaded Successfully",Toast.LENGTH_SHORT).show()
                        }.addOnFailureListener {
                            Toast.makeText(this,"Data Uploaded Failed",Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }.addOnFailureListener {
                Toast.makeText(this,"Image Uploaded Failed",Toast.LENGTH_SHORT).show()
            }
        }else {
            Toast.makeText(this,"Please Select an Image",Toast.LENGTH_SHORT).show()
        }
    }


}