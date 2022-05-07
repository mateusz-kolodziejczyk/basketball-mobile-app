package org.mk.basketballmanager.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
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
import org.mk.basketballmanager.ui.auth.LoggedInViewModel
import org.mk.basketballmanager.ui.auth.Login
import timber.log.Timber.i

class MainActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    lateinit var mainBinding : ActivityMainBinding
    lateinit var navController: NavController
    private lateinit var appBarConfiguration : AppBarConfiguration
    private lateinit var loggedInViewModel: LoggedInViewModel

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
                R.id.teamDetailFragment,
                R.id.playerList,
                R.id.freeAgentListFragment,
                R.id.addPlayerFragment,
                R.id.mapsFragment), drawerLayout
        )

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
        NavigationUI.setupWithNavController(navView, navController)


    }


    override fun onStart() {
        super.onStart()
        loggedInViewModel = ViewModelProvider(this)[LoggedInViewModel::class.java]

    }
    fun signOut(item: MenuItem) {
        loggedInViewModel.logOut()
        val intent = Intent(this, Login::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }


    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }

    // from https://stackoverflow.com/questions/28389841/change-actionbar-title-using-fragments/38224963
    fun setActionBarTitle(title: String) {
        supportActionBar!!.title = title
    }
}