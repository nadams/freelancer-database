package net.node3.freelancerdatabase.db.tables

import android.database.sqlite.SQLiteDatabase
import net.node3.freelancerdatabase.db.TableHelper
import net.node3.freelancerdatabase.db.TableRevision

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
    		
  def apply = registerRevision(new TableRevision {
    val revisionNumber = 1
    def applyRevision(database: SQLiteDatabase) = database.execSQL(sql)
  })
}