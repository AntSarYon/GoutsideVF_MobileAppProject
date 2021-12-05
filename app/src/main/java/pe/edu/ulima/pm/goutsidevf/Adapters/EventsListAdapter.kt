package pe.edu.ulima.pm.goutsidevf.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import pe.edu.ulima.pm.goutsidevf.Model.Event
import pe.edu.ulima.pm.goutsidevf.R

class EventsListAdapter(
    private val eventsList : List<Event>,
    private val fragment : Fragment,
    private val listener : (Event) ->Unit) :

    RecyclerView.Adapter<EventsListAdapter.ViewHolder>() {

    class ViewHolder(view : View, val listener : (Event) ->Unit, val eventsList: List<Event>) : RecyclerView.ViewHolder(view), View.OnClickListener {

        //...
        init {
            //...
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            listener(eventsList[adapterPosition])   //Posicion relativa -> usado para obtener la lista de productos almacenado
        }

    }

    //------------------
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false) //obtenemos el view padre
        val viewHolder = ViewHolder(view, listener, eventsList)

        return viewHolder
    }

    //------------------
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //...
    }

    //------------------
    override fun getItemCount(): Int {
        return eventsList.size
    }
}