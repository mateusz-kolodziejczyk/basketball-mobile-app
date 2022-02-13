package org.mk.basketballmanager.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.github.ajalt.timberkt.Timber
import org.mk.basketballmanager.R
import org.mk.basketballmanager.databinding.ActivityMainBinding
import timber.log.Timber.i

class MainActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    lateinit var mainBinding : ActivityMainBinding
    lateinit var navController: NavController
    private lateinit var appBarConfiguration : AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        drawerLayout = mainBinding.drawerLayout

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)


        val navHostFragment: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.main_nav_fragment) as NavHostFragment


        navController = navHostFragment.navController
        navController.setGraph(R.navigation.main_navigation, intent.extras)

        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)

//        intent.extras?.let {
//            val username = MainActivityArgs.fromBundle(it).username
//            i("Stuff")
//        }


        val navView = mainBinding.navView
        //navView.setupWithNavController(navController)

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.rosterFragment,
                R.id.teamHomeFragment,
                R.id.updateTeamFragment,
                R.id.listAllPlayersFragment,), drawerLayout
        )

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
        NavigationUI.setupWithNavController(navView, navController)


    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }

    // from https://stackoverflow.com/questions/28389841/change-actionbar-title-using-fragments/38224963
    fun setActionBarTitle(title: String) {
        supportActionBar!!.title = title
    }
}