package net.node3.freelancerdatabase.entities

import android.content.ContentValues
import net.node3.freelancerdatabase.db.tables.SolarObjectTable
import android.database.Cursor

case class SolarObject(id: Int, name: String, x: Double, y: Double, solarObjectType: SolarObjectType) {
  def toContentValues() : ContentValues = {
    val values = new ContentValues
    
    values.put(SolarObjectTable.id, id.toString)
    values.put(SolarObjectTable.name, name)
    values.put(SolarObjectTable.x, x)
    values.put(SolarObjectTable.y, y)
    values.put(SolarObjectTable.solar_object_type_id, solarObjectType.id.toString)
    
    // TODO: Take care of star system
    
    values
  }
}

object SolarObject {
  def apply(cursor: Cursor) = ???
}
