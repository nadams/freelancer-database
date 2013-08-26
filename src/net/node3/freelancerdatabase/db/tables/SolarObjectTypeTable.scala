package net.node3.freelancerdatabase.db.tables

import android.database.sqlite.SQLiteDatabase
import android.content.Context
import net.node3.freelancerdatabase.db.TableHelper
import net.node3.freelancerdatabase.db.TableRevision
import net.node3.freelancerdatabase.R

object SolarObjectTypeTable extends TableHelper {
  val tableName = "solar_object_type"
  val id = "id"
  val name = "name"
    
  lazy val sql = 
    f"""
    	CREATE TABLE $tableName (
    		$id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    		$name TEXT NOT NULL
    	);
    """
    		
  def apply(context: Context) = 
    registerRevision(new TableRevision {
      val revisionNumber = 1
      def applyRevision(database: SQLiteDatabase) = {
        database.execSQL(sql)
        
        val xml = getXml(R.raw.solar_object_type_1, context)
        (xml \ "solar_object_type") foreach { element => 
          val idValue = (element \ "@id").text
          val nameValue = (element \ "@name").text
          
          database.insert(tableName, null, (id, idValue) ~ (name, nameValue))	
        }
      }
    })
}
