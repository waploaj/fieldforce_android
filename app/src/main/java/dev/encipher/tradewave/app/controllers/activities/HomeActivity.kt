package dev.encipher.tradewave.app.controllers.activities

import android.content.Intent
import android.os.Bundle
import com.google.android.material.navigation.NavigationView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import dev.encipher.tradewave.app.R
import dev.encipher.tradewave.app.controllers.fragments.*
import dev.encipher.tradewave.app.interfaces.OnFragmentInteractionListener
import dev.encipher.tradewave.app.models.Employee
import dev.encipher.tradewave.app.utils.api.response.LoginResponse
import dev.encipher.tradewave.app.utils.config.App
import com.github.siyamed.shapeimageview.CircularImageView
import com.google.gson.Gson

//import com.google.android.gms.maps.model.Dash

class HomeActivity :
    AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener,
    OnFragmentInteractionListener {

    private lateinit var fragment: androidx.fragment.app.Fragment
    private lateinit var fragmentTag: String
    private lateinit var mEmployee: Employee
    private lateinit var loginResponse: LoginResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.title = "Trade Tool"
        setSupportActionBar(toolbar)

        val drawerLayout: androidx.drawerlayout.widget.DrawerLayout = findViewById(R.id.drawer_layout)
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
        val drawerLayout: androidx.drawerlayout.widget.DrawerLayout = findViewById(R.id.drawer_layout)
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
        val drawerLayout: androidx.drawerlayout.widget.DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun changeFragment(fragment: androidx.fragment.app.Fragment, fragmentTag: String?){

        if (isFinishing)
            return
        val fragmentManager = supportFragmentManager.findFragmentById(R.id.contentContainer)
        if (fragmentManager is DashboardFragment && fragment is DashboardFragment)
            return
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.contentContainer, fragment, fragmentTag)
            .setTransitionStyle(androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commitAllowingStateLoss()
    }
}
