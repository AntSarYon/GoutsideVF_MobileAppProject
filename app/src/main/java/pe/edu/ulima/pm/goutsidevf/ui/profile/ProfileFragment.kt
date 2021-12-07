package pe.edu.ulima.pm.goutsidevf.ui.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import pe.edu.ulima.pm.goutsidevf.R
import pe.edu.ulima.pm.goutsidevf.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    //------------------------------------------------------------
    //------ Creado por defecto por el Drawer ----------------

    private lateinit var profileViewModel: ProfileViewModel
    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        profileViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sp = this.activity?.getSharedPreferences("LOGIN_INFO", Context.MODE_PRIVATE)
        val usuario = sp?.getString("LOGIN_USERNAME", "")!!
        val nombre = sp.getString("LOGIN_NAME", "")!!
        val photo = sp.getString("LOGIN_PHOTO", "")!!
        binding.tviName.setText(nombre)
        binding.tviUsername.setText(usuario)
        Glide.with(this)
            .load(photo)
            .fitCenter()
            .into(view.findViewById(R.id.iviProfile))
    }
    //--------------------------------------------------------------------
    //------ Creado por defecto por el Drawer ----------------------------

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    //------------------------------------------------------------------


}