package pe.edu.ulima.pm.goutsidevf.ui.ranking

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
import androidx.recyclerview.widget.RecyclerView
import pe.edu.ulima.pm.goutsidevf.Adapters.EventsListAdapter
import pe.edu.ulima.pm.goutsidevf.Adapters.UserRKListAdapter
import pe.edu.ulima.pm.goutsidevf.Model.Event
import pe.edu.ulima.pm.goutsidevf.Model.EventsManager
import pe.edu.ulima.pm.goutsidevf.Model.UserRK
import pe.edu.ulima.pm.goutsidevf.Model.UserRKManager
import pe.edu.ulima.pm.goutsidevf.R
import pe.edu.ulima.pm.goutsidevf.databinding.FragmentRankingBinding

class RankingFragment : Fragment()   {
    interface  OnRankSelectedListener{
        fun OnSelect(event: Event)
    }
    private var listener: OnRankSelectedListener? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as OnRankSelectedListener
    }
    //------------------------------------------------------------
    //------ Creado por defecto por el Drawer ----------------

    private lateinit var rankingViewModel: RankingViewModel
    private var _binding: FragmentRankingBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rankingViewModel =
            ViewModelProvider(this).get(RankingViewModel::class.java)

        _binding = FragmentRankingBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        UserRKManager(requireActivity().applicationContext).getProductsFirebase({ users:List<UserRK>->
            Log.i("usuarios",users.toString())
            val rviRanking = view.findViewById<RecyclerView>(R.id.rviRanking)
            rviRanking.adapter = UserRKListAdapter(
                users,
                this
            ){user: UserRK ->
                Log.i("usuarios", user.name)
                listener?.OnSelect(user)
            }
        },{error ->
            Log.e("RankingFragment", error)
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