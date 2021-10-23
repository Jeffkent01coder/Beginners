package com.steve.beginners

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.steve.beginners.databinding.ActivityMainBinding
import com.steve.beginners.ui.LoginActivity
import com.steve.beginners.ui.ProfileActivity
import com.steve.beginners.ui.RegisterActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var navController: NavController
    lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Beginners)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.register.setOnClickListener{
            val intent =Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.loginBtn.setOnClickListener{
            val intent =Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }


        navController = findNavController(R.id.nav_host_fragment)
        //bottom nav
        binding.bottomNavView.setupWithNavController(navController)
        //drawer
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            ),
            binding.drawerLayout
        )
        //menu item click handle
        binding.navView.setupWithNavController(navController)

        //
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.Home -> Toast.makeText(applicationContext,"selected home",Toast.LENGTH_LONG).show()
                R.id.Cart -> setProfile()

            }
            true
        }


    }

    private fun setProfile() {
        val intent= Intent(applicationContext, ProfileActivity::class.java)
        startActivity(intent)
    }

    //bottom nav
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(navController) ||
                super.onOptionsItemSelected(item)
    }

    //open drawer when drawer icon clicked and back btn presse
    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment).navigateUp(appBarConfiguration)
    }
}