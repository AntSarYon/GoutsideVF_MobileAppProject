package pe.edu.ulima.pm.goutsidevf.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import pe.edu.ulima.pm.goutsidevf.Model.Event
import pe.edu.ulima.pm.goutsidevf.Model.UserRK
import pe.edu.ulima.pm.goutsidevf.R

class UserRKListAdapter(
    private val usersList : List<UserRK>,
    private val fragment : Fragment,
    private val listener : (UserRK) ->Unit) :

    RecyclerView.Adapter<UserRKListAdapter.ViewHolder>(){

        class ViewHolder(
            view : View, val listener : (UserRK) ->Unit,
            val usersList: List<UserRK>) : RecyclerView.ViewHolder(view),
            View.OnClickListener {

            val tviUsernameRK : TextView
            val tviNameRK : TextView
            val iviUser : ImageView

            init {
                tviUsernameRK = view.findViewById(R.id.tviRKUsername)
                tviNameRK = view.findViewById(R.id.tviRKName)
                iviUser = view.findViewById(R.id.iviUser)
                view.setOnClickListener(this)
            }

            override fun onClick(v: View?) {
                listener(usersList[adapterPosition])   //Posicion relativa -> usado para obtener la lista de productos almacenado
            }

        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_event, parent, false) //obtenemos el view padre
        val viewHolder = ViewHolder(view, listener, usersList)

        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tviUsernameRK.text = usersList[position].username
        holder.tviNameRK.text = usersList[position].name
        Glide.with(fragment)
            .load(usersList[position].photo)
            .fitCenter()
            .into(holder.iviUser)
    }

    override fun getItemCount(): Int {
        return usersList.size
    }

}