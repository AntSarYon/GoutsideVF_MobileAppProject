package pe.edu.ulima.pm.goutsidevf.ui.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import pe.edu.ulima.pm.goutsidevf.Adapters.LocationsListAdapter
import pe.edu.ulima.pm.goutsidevf.Model.Location
import pe.edu.ulima.pm.goutsidevf.R
import pe.edu.ulima.pm.goutsidevf.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    interface OnLocationSelectedListener{
        fun OnSelectLocation(location: Location)
    }
    private var listener: OnLocationSelectedListener?= null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as OnLocationSelectedListener
    }
    //------------------------------------------------------------
    //------ Creado por defecto por el Drawer ----------------

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root

        //-------------------------------------------------------------
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pe.edu.ulima.pm.goutsidevf.Model.LocationManager().getLocationsFirebase({ locations:List<Location>->
            val rviLocations = binding.rviLocations
            rviLocations.adapter = LocationsListAdapter(
                locations,
                this
            ){loc: Location->
                listener?.OnSelectLocation((loc))
            }
        },{error ->
            Log.e("HomeFragment", error)
            Toast.makeText(activity, "Error: " + error, Toast.LENGTH_SHORT).show()
        })
    }

    //--------------------------------------------------------------------
    //------ Creado por defecto por el Drawer ----------------------------
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    //------------------------------------------------------------------



}