package net.node3.freelancerdatabase.entities

import android.content.ContentValues
import net.node3.freelancerdatabase.db.tables.SolarObjectTable
import android.database.Cursor

case class SolarObject(id: Int, name: String, x: Float, y: Float, solarObjectType: SolarObjectType) {
  def toContentValues(): ContentValues = {
    val values = new ContentValues

    values.put(SolarObjectTable.id, id.toString)
    values.put(SolarObjectTable.name, name)
    values.put(SolarObjectTable.x, x.toDouble)
    values.put(SolarObjectTable.y, y.toDouble)
    values.put(SolarObjectTable.solar_object_type_id, solarObjectType.id.toString)

    // TODO: Take care of star system

    values
  }
}

object SolarObject {
  def apply(cursor: Cursor) =
    if (cursor.moveToFirst()) {
      Some(new SolarObject(
        cursor.getInt(0),
        cursor.getString(1),
        cursor.getFloat(2),
        cursor.getFloat(3),
        SolarObjectType(
          cursor.getInt(4),
          cursor.getString(5))))
    } else {
      None
    }
}
