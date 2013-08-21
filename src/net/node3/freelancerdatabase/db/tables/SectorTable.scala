package net.node3.freelancerdatabase.db.tables

import net.node3.freelancerdatabase.db.TableHelper
import net.node3.freelancerdatabase.db.TableRevision
import android.database.sqlite.SQLiteDatabase

object SectorTable extends TableHelper {
  val tableName = "sector"
  val id = "id"
  val name = "name"
    
  lazy val sql = 
    f""" 
  	  CREATE TABLE $tableName (
  		  $id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  		  $name TEXT NOT NULL
  	  );
    """
  		  
  def apply = 
    registerRevision(new TableRevision {
      val revisionNumber = 1
      def applyRevision(database: SQLiteDatabase) = database.execSQL(sql)
    })
}
