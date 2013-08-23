package net.node3.freelancerdatabase

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import net.node3.freelancerdatabase.entities.StarSystem

class SystemAdapter(val activity: Activity, val layout: Int, val systems: Seq[StarSystem]) 
  extends ArrayAdapter[StarSystem](activity, layout, systems.sortBy(system => system.name).toArray) {

  
  
  override def getView(position: Int, convertView: View, parent: ViewGroup) = {
    var view = convertView
    val item = getItem(position)
    
    if(view == null) {
      val inflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE).asInstanceOf[LayoutInflater]
      view = inflater.inflate(R.layout.drawer_list_item, null)
      
      val textView = view.findViewById(android.R.id.text1).asInstanceOf[TextView]
      val holder = ViewHolder(textView)
      holder.name.setText(item.name)
      
      view.setTag(holder)
    } else {
      val holder = view.getTag().asInstanceOf[ViewHolder]
      holder.name.setText(item.name)
    }
    
    view
  }
  
  case class ViewHolder(name: TextView)
}
