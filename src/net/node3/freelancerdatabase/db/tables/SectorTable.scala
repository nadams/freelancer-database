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
        
        val source = Source.fromInputStream(context.getResources.openRawResource(R.raw.sector_1), "UTF-8")
        val xml = XML.loadString(source.mkString)
        
        (xml \ "sector" \ "name").foreach { element => 
          database.insert(tableName, null, (element.label, element.text))
        }
      }
    })
  }
}
