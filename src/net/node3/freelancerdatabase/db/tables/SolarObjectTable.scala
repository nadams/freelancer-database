package net.node3.freelancerdatabase.db.tables

import android.database.sqlite.SQLiteDatabase
import net.node3.freelancerdatabase.db.TableHelper
import net.node3.freelancerdatabase.db.TableRevision

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
    			
  def apply =
    registerRevision(new TableRevision {
      val revisionNumber = 1
      def applyRevision(database: SQLiteDatabase) = database.execSQL(sql)
    })
}
