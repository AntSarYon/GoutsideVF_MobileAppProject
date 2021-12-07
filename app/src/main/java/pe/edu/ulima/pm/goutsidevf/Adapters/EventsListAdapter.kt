package pe.edu.ulima.pm.goutsidevf.Adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import pe.edu.ulima.pm.goutsidevf.Model.Event
import pe.edu.ulima.pm.goutsidevf.R
import java.text.SimpleDateFormat
import java.util.*

class EventsListAdapter(
    private val eventsList : List<Event>,
    private val fragment : Fragment,
    private val listener : (Event) ->Unit) :

    RecyclerView.Adapter<EventsListAdapter.ViewHolder>() {

    class ViewHolder(
        view : View, val listener : (Event) ->Unit,
        val eventsList: List<Event>) : RecyclerView.ViewHolder(view),
        View.OnClickListener {

        val tviNombre : TextView
        val iviEvent : ImageView
        val tviFecha : TextView
        init {
            tviNombre = view.findViewById(R.id.tviNombreEvento)
            iviEvent = view.findViewById(R.id.iviEvent)
            tviFecha = view.findViewById(R.id.tviFecha)
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            listener(eventsList[adapterPosition])   //Posicion relativa -> usado para obtener la lista de productos almacenado
        }

    }

    //------------------
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_event, parent, false) //obtenemos el view padre
        val viewHolder = ViewHolder(view, listener, eventsList)

        return viewHolder
    }

    //------------------
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val date = Date(eventsList[position].Date)
        val format = SimpleDateFormat("yyyy.MM.dd")
        holder.tviNombre.text = eventsList[position].name
        holder.tviFecha.text =format.format(date)
        Glide.with(fragment)
            .load(eventsList[position].image)
            .fitCenter()
            .into(holder.iviEvent)

    }

    //------------------
    override fun getItemCount(): Int {
        return eventsList.size
    }
}