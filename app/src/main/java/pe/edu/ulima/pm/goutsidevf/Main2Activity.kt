package pe.edu.ulima.pm.goutsidevf


import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu

import android.widget.Button

import android.widget.TextView
import android.widget.Toast

import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import pe.edu.ulima.pm.goutsidevf.Model.Event
import pe.edu.ulima.pm.goutsidevf.Model.Location
import pe.edu.ulima.pm.goutsidevf.Model.UserRK
import pe.edu.ulima.pm.goutsidevf.databinding.ActivityMain2Binding
import pe.edu.ulima.pm.goutsidevf.ui.events.EventsFragment
import pe.edu.ulima.pm.goutsidevf.ui.home.HomeFragment
import pe.edu.ulima.pm.goutsidevf.ui.ranking.RankingFragment


class Main2Activity : AppCompatActivity(), EventsFragment.OnEventSelectedListener,
    RankingFragment.OnRankSelectedListener, HomeFragment.OnLocationSelectedListener,
    SensorEventListener {
    private lateinit var fragment: Fragment
    private lateinit var mSensorManager: SensorManager
    private lateinit var mSensor: Sensor
    private var isSensorPresent = false
    private var mStepsSinceReboot: String = ""

    //----------------------------------------------------------------
    //------ Creado por defecto por el Drawer ------------------------

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMain2Binding
    //------------------------------------------------------------


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. ------ Creado por defecto por el Drawer -----------//
        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_DENIED){
            //ask for permission
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(arrayOf(Manifest.permission.ACTIVITY_RECOGNITION), 1)
            };
        }
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        mSensorManager = this.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
            != null
        ) {
            mSensor =
                mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            isSensorPresent = true;
        } else {
            isSensorPresent = false;
        }



        binding.appBarMain.fab.setOnClickListener { view ->
            Snackbar.make(view, "Pasos: ${mStepsSinceReboot}", Snackbar.LENGTH_LONG)
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

        //-- Establecer llamado a Pantallas--//
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        //------------------------------------------------------------
        //-------------------------------------------------------------


        binding.appBarMain.map.setOnClickListener {
            val intent = Intent()
            intent.setClass(this, MapActivity::class.java)
            startActivity(intent)
        }
        binding.appBarMain.camara.setOnClickListener {

        }

    }

    //-----------------------------------------------------------------------------
    // -- FUNCIONES USADAS USADAS POR EL DRAWER

    // -- Menu Conceptual
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        val sp = getSharedPreferences("LOGIN_INFO", Context.MODE_PRIVATE)
        val usuario = sp.getString("LOGIN_USERNAME", "")!!
        val nombre = sp.getString("LOGIN_NAME", "")!!
        val photo = sp.getString("LOGIN_PHOTO", "")!!
        findViewById<TextView>(R.id.tviNVName).setText(nombre)
        findViewById<TextView>(R.id.tviNVUsername).setText(usuario)
        Glide.with(this)
            .load(photo)
            .fitCenter()
            .into(findViewById(R.id.iviNVPhoto))
        menuInflater.inflate(R.menu.main2, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun OnSelect(event: Event) {
        val bundle = Bundle()
        bundle.putString("latitud", event.latitud.toString())
        bundle.putString("longitud", event.longitud.toString())
        bundle.putString("nombre", event.name)
        val intent = Intent(this, MapActivity::class.java)
        intent.putExtra("location_data", bundle)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        if(isSensorPresent)
        {
            mSensorManager.registerListener(this, mSensor,
                SensorManager.SENSOR_DELAY_NORMAL);
        }
    }
    override fun onPause() {
        super.onPause()
        if (isSensorPresent) {
            mSensorManager.unregisterListener(this)
        }
    }
    override fun OnSelecte(user: UserRK) {

    }

    override fun OnSelectLocation(location: Location) {
        val bundle = Bundle()
        bundle.putString("latitud",location.latitud.toString())
        bundle.putString("longitud",location.longitud.toString())
        val intent = Intent(this, MapActivity::class.java)
        intent.putExtra("location_selected", bundle)
        startActivity(intent)
    }

    override fun onSensorChanged(p0: SensorEvent?) {
        mStepsSinceReboot = p0!!.values[0].toString()

    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

}