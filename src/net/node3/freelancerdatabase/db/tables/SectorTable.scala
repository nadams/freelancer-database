package net.node3.freelancerdatabase.db.tables

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import net.node3.freelancerdatabase.db.TableHelper
import net.node3.freelancerdatabase.db.TableRevision
import scala.collection.immutable.List

object SectorTable extends TableHelper {
  val tableName = "sector"
  val id = "id"
  val name = "name"

  lazy val sql =
    f""" 
  	  CREATE TABLE $tableName (
  		  $id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  		  $name TEXT NOT NULL
  	  )
    """
  		  
  def apply() = {
    registerRevision(new TableRevision {
      val revisionNumber = 1
	  val sectors = List[ContentValues](
	    contentValue(("name", "sirius")),
	    contentValue(("name", "altair")),
	    contentValue(("name", "inner_core")),
	    contentValue(("name", "canis"))
	  )
	  
      def applyRevision(database: SQLiteDatabase) = {
        database.execSQL(sql)
        sectors.foreach(sector => database.insert(tableName, null, sector))
      }
    })
  }
  
  def contentValue(value: Pair[String, String]) = {
    val contentValue = new ContentValues
    contentValue.put(value._1, value._2)
    contentValue
  }
}
