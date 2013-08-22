package net.node3.freelancerdatabase.db.tables

import android.database.sqlite.SQLiteDatabase
import net.node3.freelancerdatabase.db.TableHelper
import net.node3.freelancerdatabase.db.TableRevision

object StarSystemTable extends TableHelper {
  val tableName = "star_system"
  val id = "id"
  val name = "name"
  val sector_id = "sector_id"
    
  lazy val sql = 
    f""" 
    	CREATE TABLE $tableName (
    		$id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    		$name TEXT NOT NULL,
    		$sector_id INTEGER NOT NULL,
    		
    		CONSTRAINT fk_star_system_sector FOREIGN KEY($sector_id) REFERENCES ${SectorTable.name}(${SectorTable.id}) 
    	);
    """
    		
  def apply() = 
    registerRevision(new TableRevision {
      val revisionNumber = 1
      def applyRevision(database: SQLiteDatabase) = database.execSQL(sql)
    })
}
