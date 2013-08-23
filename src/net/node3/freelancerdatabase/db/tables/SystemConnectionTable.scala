package net.node3.freelancerdatabase.db.tables

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import net.node3.freelancerdatabase.db.TableHelper
import net.node3.freelancerdatabase.db.TableRevision
import net.node3.freelancerdatabase.R

object SystemConnectionTable extends TableHelper {
  val tableName = "system_connection"
  val from_system = "from_system"
  val to_system = "to_system"
  val type_id = "type_id"
  val object_id = "object_id"
    
  val pk_system_connection = "pk_system_connection"
  val fk_system_connection_from = "fk_system_connection_from"
  val fk_system_connection_to = "fk_system_connection_to"
  val fk_system_connection_solar_object = "fk_system_connection_solar_object"
  val fk_system_connection_object_id = "fk_system_connection_object_id"
    
  lazy val createTable = 
  f"""
  	CREATE TABLE $tableName (
  		$from_system INTEGER NOT NULL,
  		$to_system INTEGER NOT NULL,
  		$type_id INTEGER NOT NULL,
  		$object_id INTEGER NOT NULL,
  			
  		CONSTRAINT $fk_system_connection_from
  			FOREIGN KEY($from_system)
  			REFERENCES ${StarSystemTable.tableName}(${StarSystemTable.id}),
  			
  		CONSTRAINT $fk_system_connection_to
  			FOREIGN KEY($to_system)
  			REFERENCES ${StarSystemTable.tableName}(${StarSystemTable.id}),
  			
  		CONSTRAINT $fk_system_connection_solar_object
  			FOREIGN KEY($type_id)
  			REFERENCES ${SolarObjectTable.tableName}(${SolarObjectTable.id}),
  			
  		CONSTRAINT $fk_system_connection_object_id
  			FOREIGN KEY($object_id)
  			REFERENCES ${SolarObjectTable.tableName}(${SolarObjectTable.id})
  	);
  """
    			
  lazy val createIndex =
  f"""
  	CREATE UNIQUE INDEX ix_${tableName}_primary ON $tableName($from_system, $to_system, $type_id);
  """
    			
  def apply(context: Context) = 
    registerRevision(new TableRevision {
      val revisionNumber = 1
      def applyRevision(database: SQLiteDatabase) = {
        database.execSQL(createTable)
        database.execSQL(createIndex)
        
        val xml = getXml(R.raw.system_connection_1, context)
        (xml \ "system_connection") foreach { element =>
          val fromSystemValue = (element \ "@from_system").text
          val toSystemValue = (element \ "@to_system").text
          val typeIdValue = (element \ "@type_id").text
          val objectIdValue = (element \ "@object_id").text
          
          database.insert(tableName, null, (from_system, fromSystemValue) ~ (to_system, toSystemValue) ~ (type_id, typeIdValue) ~ (object_id, objectIdValue))
        }
      }
    })
}
