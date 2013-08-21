package net.node3.freelancerdatabase.db.tables

import net.node3.freelancerdatabase.db.TableHelper
import net.node3.freelancerdatabase.db.TableRevision
import android.database.sqlite.SQLiteDatabase

object SystemConnectionTable extends TableHelper {
  val tableName = "system_connection"
  val from_system = "from_system"
  val to_system = "to_system"
  val type_id = "type_id"
  val is_bidirectional = "is_bidirectional"
    
  val pk_system_connection = "pk_system_connection"
  val fk_system_connection_from = "fk_system_connection_from"
  val fk_system_connection_to = "fk_system_connection_to"
  val fk_system_connection_solar_object = "fk_system_connection_solar_object"
    
  lazy val sql = 
    f"""
    	CREATE TABLE $tableName (
    		$from_system INTEGER NOT NULL,
    		$to_system INTEGER NOT NULL,
    		$type_id INTEGER NOT NULL,
    		$is_bidirectional BOOLEAN NOT NULL,
    		
    		CONSTRAINT $pk_system_connection
    			PRIMARY KEY($from_system, $to_system, $type_id),
    			
    		CONSTRAINT $fk_system_connection_from
    			FOREIGN KEY($from_system)
    			REFERENCES ${StarSystemTable.tableName}(${StarSystemTable.id}),
    			
    		CONSTRAINT $fk_system_connection_to
    			FOREIGN KEY($to_system)
    			REFERENCES ${StarSystemTable.tableName}(${StarSystemTable.id}),
    			
    		CONSTRAINT $fk_system_connection_solar_object
    			FOREIGN KEY($type_id)
    			REFERENCES ${SolarObjectTable.tableName}(${SolarObjectTable.id})
    	);
    """
    			
  def apply = 
    registerRevision(new TableRevision {
      val revisionNumber = 1
      def applyRevision(database: SQLiteDatabase) = database.execSQL(sql)
    })
}
