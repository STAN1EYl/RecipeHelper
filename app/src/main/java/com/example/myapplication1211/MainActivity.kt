package com.example.myapplication1211

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {


    private lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_main)


        bottomNav = findViewById(R.id.bottomNavView)
        bottomNav.setOnNavigationItemSelectedListener(bottomNavListener)

        
        if (savedInstanceState == null) {
            loadFragment(ExploreFragment())
        }
    }

    // BottomNavigationView item selection listener
    private val bottomNavListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.nav_explore -> {
                loadFragment(ExploreFragment())
                true
            }
            R.id.nav_search -> {
                loadFragment(SearchFragment())
                true
            }
            R.id.nav_favorites -> {
                loadFragment(FavoritesFragment())
                true
            }
            else -> false
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
