package net.node3.freelancerdatabase.db.tables

import scala.io.Source
import scala.xml.XML

import android.content.Context
import android.database.sqlite.SQLiteDatabase

import net.node3.freelancerdatabase.R
import net.node3.freelancerdatabase.db.TableHelper
import net.node3.freelancerdatabase.db.TableRevision

object SectorTable extends TableHelper {
  val tableName = "sector"
  val id = "id"
  val name = "name"
  val logTag = tableName

  lazy val sql =
    f""" 
  	  CREATE TABLE $tableName (
  		  $id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  		  $name TEXT NOT NULL
  	  )
    """
  		  
  def apply(context: Context) = {
    registerRevision(new TableRevision {
      val revisionNumber = 1
	  
      def applyRevision(database: SQLiteDatabase) = {
        database.execSQL(sql)
        
        val xml = getXml(R.raw.sector_1, context)
        
        (xml \ "sector").foreach { element =>
          val idValue = (element \ "@id").text
          val nameValue = (element \ "@name").text
          
          database.insert(tableName, null, (id, idValue) ~ (name, nameValue))
        }
      }
    })
  }
}
