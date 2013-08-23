package net.node3.freelancerdatabase

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import net.node3.freelancerdatabase.db.StarSystemRepository
import net.node3.freelancerdatabase.entities.StarSystem

class SystemAdapter(val activity: Activity, val layout: Int, val systemRepository: StarSystemRepository) extends BaseAdapter {
  val viewModels = systemRepository.getAll.map(system => new StarSystemModel(system, activity)).sortBy(system => system.name)
  
  override def getCount = viewModels.size
  override def getItemId(position: Int) : Long = position
  override def getItem(position: Int) = viewModels(position)
  
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

class StarSystemModel(val starSystem: StarSystem, val context: Context) {
  def name : String = {
    val resources = context.getResources
    
    val resId = resources.getIdentifier(starSystem.name, "string", context.getPackageName)
    
    resources.getString(resId)
  }
}
