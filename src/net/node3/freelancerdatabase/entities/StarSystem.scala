package net.node3.freelancerdatabase.entities

import android.database.Cursor
import android.content.ContentValues
import net.node3.freelancerdatabase.db.tables._

class StarSystem(val id: Int, val name: String, val sectorId: Int) {
  override def toString() = f"id: $id, name: $name, sectorId: $sectorId"
}

object StarSystem {
  def apply(id: Int, name: String, sectorId: Int) = new StarSystem(id, name, sectorId)
  def apply(cursor: Cursor) = new StarSystem(cursor.getInt(0), cursor.getString(1), cursor.getInt(2))
  def unapply(system: StarSystem): ContentValues = 
    (StarSystemTable.id, system.id.toString) ~ (StarSystemTable.name, system.name) ~ (StarSystemTable.sector_id, system.sectorId.toString)
}
