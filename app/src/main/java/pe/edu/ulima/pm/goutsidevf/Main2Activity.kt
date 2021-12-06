package pe.edu.ulima.pm.goutsidevf

import android.hardware.SensorManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import pe.edu.ulima.pm.goutsidevf.ui.ranking.RankingFragment
import pe.edu.ulima.pm.goutsidevf.databinding.ActivityMain2Binding
import pe.edu.ulima.pm.goutsidevf.ui.events.EventsFragment
import pe.edu.ulima.pm.goutsidevf.ui.profile.ProfileFragment

class Main2Activity : AppCompatActivity() {

    private var fragmentHome : Fragment = Fragment()
    private var fragmentEvents : Fragment = Fragment()
    private var fragmentProfile : Fragment = Fragment()
    private var fragmentRanking : Fragment = Fragment()

    private lateinit var sensorManager : SensorManager

    //----------------------------------------------------------------
    //------ Creado por defecto por el Drawer ------------------------

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMain2Binding
    //------------------------------------------------------------

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fragmentEvents = EventsFragment()
        fragmentProfile = ProfileFragment()
        fragmentRanking = RankingFragment()
        fragmentHome = RankingFragment()

        //------------------------------------------------------------
        //------ Creado por defecto por el Drawer ----------------

        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        binding.appBarMain.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        appBarConfiguration = AppBarConfiguration(
            setOf( //ID de los fragments (del Drawe) mostrados
                R.id.nav_home, R.id.nav_FProfile, R.id.nav_FEvents, R.id.nav_FRanking
            ), drawerLayout
        )

        //-- Establecer llamado a Pantallas
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        //------------------------------------------------------------
        //-------------------------------------------------------------


        navView.setNavigationItemSelectedListener { menuItem : MenuItem ->

            if(menuItem.itemId==R.id.navProfile){
                changeProfileFragment()
            }
            else if(menuItem.itemId==R.id.navEvents){
                changeEventsFragment()
            }
            else if(menuItem.itemId==R.id.navRanking){
                changeRankingFragment()
            }

            menuItem.setChecked(true)
            drawerLayout.closeDrawers()
            true
        }

        //Carga el fragment por Defecto
        //val ft = supportFragmentManager.beginTransaction()       //supportFragmentManager --> manager gestor de fragments DE ESTE ACTIVITY <-- ES UNA TRANSACCION
        //ft.replace(R.id.nav_host_fragment_content_main, fragmentProfile)                        //Nosotros podemos hacer diversas acciones con fragments;
        //ft.commit()

    }

    //-----------------------------------------------------------------------------
    // -- FUNCIONES USADAS USADAS POR EL DRAWER

    // -- Menu Conceptual
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main2, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    //---------------------------------------------------------------------------
    //--------------------------------------------------------------------------



    //-- Abrir el Menu cada vez que se haga Click ---------------------------------
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.action_settings){
            return true
        }
        return super.onOptionsItemSelected(item)
    }




    //-----------------------------------------------------------------------------
    // -- FUNCIONES DE CAMBIO DE FRAGMENT

    // -- CAMBIAR AL FRAGMENT DE RANKING -------------------
    private fun changeRankingFragment() {
        //...
    }

    // -- CAMBIAR AL FRAGMENT DE EVENTOS -------------------
    private fun changeEventsFragment() {
        //...
    }

    // -- CAMBIAR AL FRAGMENT DE PERFIL -------------------
    private fun changeProfileFragment() {
        //...
    }

}