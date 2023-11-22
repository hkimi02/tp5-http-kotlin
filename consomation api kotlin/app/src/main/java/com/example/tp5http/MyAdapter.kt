import android.content.ClipData.Item
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
import com.example.tp5http.Offre
import com.example.tp5http.R

class MyAdapter(private var dataSet: List<Offre>) :
    RecyclerView.Adapter<MyAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_offre, parent, false) // Replace 'R.layout.item_offre' with the actual layout resource for your list item
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataSet[position]

        holder.codeTextView.text = item.code.toString()
        holder.intituleTextView.text = item.intitulé
        holder.specialiteTextView.text=item.specialité
        holder.paysTextView.text=item.pays
        holder.nbPostesTextView.text=item.nbpostes.toString()
        holder.sociétéTextView.text=item.société

    }

    override fun getItemCount(): Int {
        return dataSet.size
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val codeTextView: TextView = itemView.findViewById<TextView>(R.id.code)
        val intituleTextView: TextView = itemView.findViewById(R.id.intitulé)
        val specialiteTextView:TextView = itemView.findViewById(R.id.specialité)
        val paysTextView : TextView=itemView.findViewById(R.id.pays)
        val nbPostesTextView : TextView=itemView.findViewById(R.id.nbpostes)
        val sociétéTextView : TextView=itemView.findViewById(R.id.société)

    }
    fun updateData(newData: List<Offre>) {
        dataSet = newData
        notifyDataSetChanged()
    }
}
