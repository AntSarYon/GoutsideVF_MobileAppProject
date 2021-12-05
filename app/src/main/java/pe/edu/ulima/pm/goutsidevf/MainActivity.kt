package pe.edu.ulima.pm.goutsidevf

import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import pe.edu.ulima.pm.goutsidevf.Fragments.EventsFragment
import pe.edu.ulima.pm.goutsidevf.Fragments.ProfileFragment
import pe.edu.ulima.pm.goutsidevf.Fragments.RankingFragment

class MainActivity : AppCompatActivity() {

    //Gestion de Fargments
    private var fragmentEvents : Fragment = Fragment()
    private var fragmentProfile : Fragment = Fragment()
    private var fragmentRanking : Fragment = Fragment()

    private lateinit var dlaMain : DrawerLayout
    private lateinit var sensorManager : SensorManager

    //--------------------------------------------------------------------------

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragmentEvents = EventsFragment()
        fragmentProfile = ProfileFragment()
        fragmentRanking = RankingFragment()

        //var fragmentMostrado = intent.getBundleExtra("data")?.getString("fragment")

        // -- Configuracion de Menu Hamburguesa (que se muestre) ------
        val actionBar = supportActionBar
        actionBar?.setHomeAsUpIndicator(android.R.drawable.ic_menu_manage)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        // -- Configuracion de Navigate View ---------------------------
        val nviMain = findViewById<NavigationView>(R.id.nviMain)
        dlaMain = findViewById<DrawerLayout>(R.id.dlaMain)

        // -- Ejecucion de Opcion seleccionada en el Menu
        nviMain.setNavigationItemSelectedListener { menuItem : MenuItem ->
            if(menuItem.itemId==R.id.menProfile){
                changeProfileFragment()
            }
            else if(menuItem.itemId==R.id.menEvents){
                changeEventsFragment()
            }
            else if (menuItem.itemId==R.id.menRanking){
                changeRankingFragment()
            }

            menuItem.setChecked(true)
            dlaMain.closeDrawers()
            true
        }
    }

    //-- Abrir el Menu cada vez que se haga Click ---------------------------------
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home){
            dlaMain.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)
    }


    // -- CAMBIAR AL FRAGMENT DE RANKING -------------------
    private fun changeRankingFragment() {
        TODO("Not yet implemented")
    }

    // -- CAMBIAR AL FRAGMENT DE EVENTOS -------------------
    private fun changeEventsFragment() {
        TODO("Not yet implemented")
    }

    // -- CAMBIAR AL FRAGMENT DE PERFIL -------------------
    private fun changeProfileFragment() {
        TODO("Not yet implemented")
    }
}