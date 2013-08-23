package net.node3.freelancerdatabase.db.tables

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import net.node3.freelancerdatabase.R
import net.node3.freelancerdatabase.db.TableHelper
import net.node3.freelancerdatabase.db.TableRevision

object StarSystemTable extends TableHelper {
  val tableName = "star_system"
  val id = "id"
  val name = "name"
  val x = "x"
  val y = "y"
  val sector_id = "sector_id"
    
  lazy val sql = 
    f""" 
    	CREATE TABLE $tableName (
    		$id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    		$name TEXT NOT NULL,
    		$sector_id INTEGER NOT NULL,
    		$x REAL NOT NULL,
    		$y REAL NOT NULL,
    		CONSTRAINT fk_star_system_sector FOREIGN KEY($sector_id) REFERENCES ${SectorTable.name}(${SectorTable.id}) 
    	);
    """
    		
  def apply(context: Context) = 
    registerRevision(new TableRevision {
      val revisionNumber = 1
      def applyRevision(database: SQLiteDatabase) = {
        database.execSQL(sql)
        
        val xml = getXml(R.raw.star_system_1, context)
        (xml \ "star_system").foreach { element =>
          val idValue = (element \ "@id").text
          val nameValue = (element \ "@name").text
          val xValue = (element \ "@x").text
          val yValue = (element \ "@y").text
          val sectorIdValue = (element \ "@sector_id").text
          
          database.insert(tableName, null, (id, idValue) ~ (name, nameValue) ~ (x, xValue) ~ (y, yValue) ~ (sector_id, sectorIdValue))
        }
      }
    })
}
