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
  private var systemFilter = ""
  private var filteredModels = filteredSystems
  
  override def getCount = filteredModels.size
  override def getItemId(position: Int) : Long = position
  override def getItem(position: Int) = filteredModels(position)
  
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
  
  def applyFilter(name: String) = {
    systemFilter = name
    filteredModels = filteredSystems
    
    notifyDataSetChanged
    true
  }
  
  def filter = systemFilter
  
  private def filteredSystems =
    systemRepository.getAll map { system => 
      new StarSystemModel(system, activity) 
    } filter { system => 
      system.name.matches(f"(?i).*$systemFilter.*")
    } sortBy(system => system.name)
  
  case class ViewHolder(name: TextView)
}

object SystemAdapter {
  lazy val filterKey = "filterKey"
}

class StarSystemModel(val starSystem: StarSystem, val context: Context) {
  def name : String = {
    val resources = context.getResources
    
    val resId = resources.getIdentifier(starSystem.name, "string", context.getPackageName)
    
    resources.getString(resId)
  }
}
