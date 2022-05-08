package org.mk.basketballmanager.activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.squareup.picasso.Picasso
import org.mk.basketballmanager.R
import org.mk.basketballmanager.databinding.ActivityMainBinding
import org.mk.basketballmanager.databinding.NavHeaderBinding
import org.mk.basketballmanager.ui.auth.LoggedInViewModel
import org.mk.basketballmanager.ui.team.TeamViewModel
import org.mk.basketballmanager.utils.customTransformation
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    lateinit var mainBinding : ActivityMainBinding
    lateinit var navController: NavController
    private lateinit var navHeaderBinding: NavHeaderBinding
    private lateinit var appBarConfiguration : AppBarConfiguration
    private lateinit var loggedInViewModel: LoggedInViewModel
    private lateinit var teamViewModel: TeamViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        drawerLayout = mainBinding.drawerLayout
        initNavHeader()
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)


        val navHostFragment: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.main_nav_fragment) as NavHostFragment


        navController = navHostFragment.navController
        navController.setGraph(R.navigation.main_navigation, intent.extras)

        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)


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

        loggedInViewModel.loggedOut.observe(this, Observer {
            if(it == true){
                goToLogin()
            }
        })

        teamViewModel = ViewModelProvider(this)[TeamViewModel::class.java]

        // Modify navheader based on team data
        teamViewModel.observableTeam.observe(this, Observer {
            if(it == null){
                return@Observer
            }
            navHeaderBinding.teamName.text = it.name
            if(it.image.isNotEmpty()){
                Picasso.get()
                    .load(it.image)
                    .resize(50, 50)
                    .transform(customTransformation())
                    .into(navHeaderBinding.navHeaderImage)
            }
        })

        loggedInViewModel.liveFirebaseUser.observe(this, Observer {
            it.email?.let { email ->
                navHeaderBinding.userEmail.text = email
            }
        })


    }

    private fun initNavHeader() {
        Timber.i("DX Init Nav Header")
        val headerView = mainBinding.navView.getHeaderView(0)
        navHeaderBinding = NavHeaderBinding.bind(headerView)
    }
    fun signOut(item: MenuItem) {
        loggedInViewModel.logOut(applicationContext)

    }

    fun goToLogin(){
        val intent = Intent(this, Login::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    fun toggleNightMode(item: MenuItem){
        val mode =
            if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) AppCompatDelegate.MODE_NIGHT_NO else AppCompatDelegate.MODE_NIGHT_YES
        AppCompatDelegate.setDefaultNightMode(mode)
//                val mode =
//            if (AppCompatDelegate.getLocalNightMode() == AppCompatDelegate.MODE_NIGHT_YES) AppCompatDelegate.MODE_NIGHT_NO else AppCompatDelegate.MODE_NIGHT_YES
//        AppCompatDelegate.setDefaultNightMode(mode)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }

    // from https://stackoverflow.com/questions/28389841/change-actionbar-title-using-fragments/38224963
    fun setActionBarTitle(title: String) {
        supportActionBar!!.title = title
    }
}