package net.node3.freelancerdatabase.db.tables

import net.node3.freelancerdatabase.db.TableHelper
import net.node3.freelancerdatabase.db.TableRevision

object StarSystemTable extends TableHelper {
  val tableName = "star_system"
  val id = "id"
  val name = "name"
  val sector_id = "sector_id"
    
  val allColumns = List(id, name, sector_id)
  
  lazy val sql = 
    f""" 
    	CREATE TABLE IF NOT EXISTS $tableName (
    		$id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    		$name TEXT NOT NULL,
    		$sector_id INTEGER NOT NULL,
    		
    		CONSTRAINT fk_star_system_sector FOREIGN KEY($sector_id) REFERENCES ${SectorTable.name}(${SectorTable.id}) 
    	);
    """
}
