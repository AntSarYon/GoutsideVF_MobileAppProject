package pe.edu.ulima.pm.goutsidevf.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import pe.edu.ulima.pm.goutsidevf.Model.Location
import pe.edu.ulima.pm.goutsidevf.R
import java.util.*

class LocationsListAdapter(
    private val locationList : List<Location>,
    private val fragment : Fragment,
    private val listener : (Location) ->Unit):
    RecyclerView.Adapter<LocationsListAdapter.ViewHolder>(){
    class ViewHolder(
        view : View, val listener : (Location) ->Unit,
        val locationsListener: List<Location>) : RecyclerView.ViewHolder(view), View.OnClickListener {

        val tviNombre : TextView
        val iviLocation : ImageView

        init {
            tviNombre = view.findViewById(R.id.tviLocation)
            iviLocation = view.findViewById(R.id.iviLocation)

            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            listener(locationsListener[adapterPosition])
        }

    }

    //------------------
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_location, parent, false) //obtenemos el view padre
        val viewHolder = ViewHolder(view, listener, locationList)

        return viewHolder
    }

    //------------------
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tviNombre.text = locationList[position].nombre
        Glide.with(fragment)
            .load(locationList[position].image)
            .fitCenter()
            .into(holder.iviLocation)

    }

    //------------------
    override fun getItemCount(): Int {
        return locationList.size
    }
}