package rankhep.com.notthatway.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import rankhep.com.notthatway.R
import rankhep.com.notthatway.model.LogModel

/**
 * Created by choi on 2018. 4. 1..
 */
class LogAdapter(var items: ArrayList<LogModel>, var onItemClickListener: OnItemClickListener) : RecyclerView.Adapter<LogAdapter.ViewHolder>() {
    interface OnItemClickListener {
        fun onItemClickListener(itemView: View, latitude: Double, longitude: Double)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.run {
            date.text = items[position].time
            address.text = items[position].address
            itemView.setOnClickListener {
                onItemClickListener.onItemClickListener(it, items[position].latitude, items[position].longitude)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_log, parent, false))

    override fun getItemCount(): Int = items.size

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var date = v.findViewById<TextView>(R.id.date)
        var address = v.findViewById<TextView>(R.id.location)
    }
}