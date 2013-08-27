package net.node3.freelancerdatabase.entities

import android.database.Cursor
import net.node3.freelancerdatabase.db.tables._

case class StarSystem(id: Int, name: String, sectorId: Int) {
  def toContentValues() =
	  (StarSystemTable.id, id.toString) ~ (StarSystemTable.name, name) ~ (StarSystemTable.sector_id, sectorId.toString)
}

object StarSystem {
  def apply(cursor: Cursor) = new StarSystem(cursor.getInt(0), cursor.getString(1), cursor.getInt(2))
}
