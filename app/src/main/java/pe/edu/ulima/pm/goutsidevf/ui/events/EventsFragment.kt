package pe.edu.ulima.pm.goutsidevf.ui.events

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
import pe.edu.ulima.pm.goutsidevf.Model.Event
import pe.edu.ulima.pm.goutsidevf.Model.EventsManager
import pe.edu.ulima.pm.goutsidevf.R
import pe.edu.ulima.pm.goutsidevf.databinding.FragmentEventsBinding

class EventsFragment : Fragment() {
    interface  OnEventSelectedListener{
        fun OnSelect(event: Event)
    }
    private var listener: OnEventSelectedListener? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as OnEventSelectedListener
    }
    //------------------------------------------------------------
    //------ Creado por defecto por el Drawer ----------------

    private lateinit var eventsViewModel: EventsViewModel
    private var _binding: FragmentEventsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        eventsViewModel =
            ViewModelProvider(this).get(EventsViewModel::class.java)

        _binding = FragmentEventsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        /*val textView: TextView = binding.tviEvents
        eventsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })*/
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        EventsManager(requireActivity().applicationContext).getProductsFirebase({events:List<Event>->
            Log.i("eventos",events.toString())
            val rviEvents = view.findViewById<RecyclerView>(R.id.rviEvents)
            rviEvents.adapter = EventsListAdapter(
                events,
                this
            ){event: Event ->
                Log.i("eventos",event.name)
                listener?.OnSelect(event)
            }
        },{error ->
            Log.e("EventFragment", error)
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