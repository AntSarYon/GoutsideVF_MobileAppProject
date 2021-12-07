package pe.edu.ulima.pm.goutsidevf.ui.ranking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import pe.edu.ulima.pm.goutsidevf.databinding.FragmentRankingBinding

class RankingFragment : Fragment()   {









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

    //--------------------------------------------------------------------
    //------ Creado por defecto por el Drawer ----------------------------

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    //------------------------------------------------------------------



}