package com.bytcode.tradetool.app.controllers.activities

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import com.bytcode.tradetool.app.R
import com.bytcode.tradetool.app.controllers.fragments.*
import com.bytcode.tradetool.app.interfaces.OnFragmentInteractionListener
import com.bytcode.tradetool.app.models.Employee
import com.bytcode.tradetool.app.utils.api.response.LoginResponse
import com.bytcode.tradetool.app.utils.config.App
import com.github.siyamed.shapeimageview.CircularImageView
import com.google.gson.Gson

//import com.google.android.gms.maps.model.Dash

class HomeActivity :
    AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener,
    OnFragmentInteractionListener {

    private lateinit var fragment: Fragment
    private lateinit var fragmentTag: String
    private lateinit var mEmployee: Employee
    private lateinit var loginResponse: LoginResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.title = "Trade Tool"
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        loginResponse = Gson().fromJson(App.sharedPrefs.user, LoginResponse::class.java)
        mEmployee = loginResponse.employee

        val headerView = navView.getHeaderView(0)
        val userPhoneNumber = headerView.findViewById<TextView>(R.id.userPhoneNumber)
        val userFullName = headerView.findViewById<TextView>(R.id.userFullName)
        val userProfileImageView = headerView.findViewById<CircularImageView>(R.id.userImageView)

        userPhoneNumber.text = mEmployee.phoneNumber
        userFullName.text = mEmployee.getFullName()

        navView.setNavigationItemSelectedListener(this)

        if(savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                .add(R.id.contentContainer, DashboardFragment.newInstance())
                .commit()
        }
    }

    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onFragmentCreated(title: String?) {
        supportActionBar?.title = title
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> {
                fragment = DashboardFragment.newInstance()
                fragmentTag = "dashboardFragment"

                changeFragment(fragment, fragmentTag)
            }
            R.id.nav_schedules -> {
                fragment = ScheduleFragment.newInstance()
                fragmentTag = "scheduleFragment"

                changeFragment(fragment, fragmentTag)
            }
            R.id.nav_reports -> {
                fragment = ReportsFragment.newInstance()
                fragmentTag = "reportsFragment"

                changeFragment(fragment, fragmentTag)
            }
            R.id.nav_stock -> {
                fragment = DashboardFragment.newInstance()
                fragmentTag = "dashboardFragment"

                changeFragment(fragment, fragmentTag)
            }
            R.id.nav_profile -> {
                fragment = ProfileFragment.newInstance()
                fragmentTag = "profileFragment"

                changeFragment(fragment, fragmentTag)
            }
            R.id.nav_settings -> {
                fragment = SettingsFragment.newInstance()
                fragmentTag = "settingsFragment"

                changeFragment(fragment, fragmentTag)
            }
            R.id.nav_help -> {
                fragment = HelpFragment.newInstance()
                fragmentTag = "helpFragment"

                changeFragment(fragment, fragmentTag)
            }
            R.id.nav_logout -> {
                App.sharedPrefs.isLoggedIn = false
                val intent = Intent(this@HomeActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun changeFragment(fragment: Fragment, fragmentTag: String?){

        if (isFinishing)
            return
        val fragmentManager = supportFragmentManager.findFragmentById(R.id.contentContainer)
        if (fragmentManager is DashboardFragment && fragment is DashboardFragment)
            return
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.contentContainer, fragment, fragmentTag)
            .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commitAllowingStateLoss()
    }
}
