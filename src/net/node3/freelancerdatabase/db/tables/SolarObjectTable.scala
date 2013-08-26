package net.node3.freelancerdatabase.db.tables

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import net.node3.freelancerdatabase.db.TableHelper
import net.node3.freelancerdatabase.db.TableRevision
import net.node3.freelancerdatabase.R

object SolarObjectTable extends TableHelper {
  val tableName = "solar_object"
  val id = "id"
  val name = "name"
  val x = "x"
  val y = "y"
  val solar_object_type_id = "solar_object_type_id"
  val star_system_id = "star_system_id"
  val fk_solar_object_type = "fk_solar_object_type"
  val fk_solar_object_star_system = "fk_solar_object_star_system"

  lazy val sql = 
    f"""
    	CREATE TABLE $tableName (
    		$id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    		$name TEXT NOT NULL,
    		$x REAL NOT NULL,
    		$y REAL NOT NULL,
    		$solar_object_type_id INTEGER NOT NULL,
    		$star_system_id INTEGER NOT NULL,
    		
    		CONSTRAINT $fk_solar_object_type 
	    		FOREIGN KEY($solar_object_type_id) 
	    		REFERENCES ${SolarObjectTable.tableName}(${SolarObjectTable.id}),
	    		
    		CONSTRAINT $fk_solar_object_star_system
    			FOREIGN KEY($star_system_id)
    			REFERENCES ${StarSystemTable.tableName}(${StarSystemTable.id})
    	);
    """
    			
  def apply(context: Context) =
    registerRevision(new TableRevision {
      val revisionNumber = 1
      def applyRevision(database: SQLiteDatabase) = {
        database.execSQL(sql)
        
        val xml = getXml(R.raw.solar_object_1, context)
        
        (xml \ "solar_object") foreach { element =>
          database.insert(
            tableName, 
            null, 
            (id, (element \ "@id").text) ~ 
            (name, (element \ "@name").text) ~
            (x, (element \ "@x").text) ~
            (y, (element \ "@y").text) ~
            (solar_object_type_id, (element \ "@solar_object_type_id").text) ~
            (star_system_id, (element \ "@star_system_id").text)
          )
        }
      }
    })
}
