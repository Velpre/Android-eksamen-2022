package com.example.andoridlifecycle

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager

class MainActivity : AppCompatActivity() {

    private lateinit var fragmentManager: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    fun switchFragment(v: View) {
        fragmentManager = supportFragmentManager

        if (Integer.parseInt(v.getTag().toString()) == 1) {
            fragmentManager
                .beginTransaction()
                .replace(
                    R.id.fragment_main,
                    Fragment1(),
                    "Fragment1"
                )
                .commit()
        } else {
            fragmentManager
                .beginTransaction()
                .replace(
                    R.id.fragment_main,
                    Fragment2(),
                    "Fragment2"
                )
                .commit()
        }
    }

    fun submit(view: View){
        var imageUri = (fragmentManager.findFragmentByTag("Fragment1") as Fragment1).imageUri.toString()
        //var rect = (fragmentManager.findFragmentByTag("Fragment1") as Fragment1).actualCropRect!!
        //var imgW = (fragmentManager.findFragmentByTag("Fragment1") as Fragment1).image.width
        //var imgH = (fragmentManager.findFragmentByTag("Fragment1") as Fragment1).image.height
        Log.i("fileImg", imageUri)
        Toast.makeText(this, "Added", Toast.LENGTH_SHORT).show()
    }
}