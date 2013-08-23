package net.node3.freelancerdatabase.db

import net.node3.freelancerdatabase.entities.StarSystem
import android.content.Context
import net.node3.freelancerdatabase.db.tables.StarSystemTable
import net.node3.freelancerdatabase.entities.StarSystem
import net.node3.freelancerdatabase.entities.StarSystem
import android.database.Cursor

trait StarSystemRepositoryComponent {
  def systemRepository(context: Context) : StarSystemRepository  
}

trait StarSystemRepository {
  def getAll: Seq[StarSystem]
  def getById(id: Int): Option[StarSystem]
}

trait StarSystemRepositoryComponentImpl extends StarSystemRepositoryComponent {
  def systemRepository(context: Context) = new StarSystemRepositoryImpl(context)
  
  class StarSystemRepositoryImpl(val context: Context) extends StarSystemRepository {
    val database = new DatabaseHelper(context)
    
    def getAll = {
      val sql = 
        f"""
        	SELECT *
        	FROM ${StarSystemTable.tableName}
        """
      
      val cursor = database.getReadableDatabase.rawQuery(sql, Array())
      
      cursor.map(readStarSystem).toSeq
    }
    
    def getById(id: Int) = {
      val sql = 
        f"""
    		SELECT * 
        	FROM ${StarSystemTable.tableName}
      		WHERE ${StarSystemTable.id} = ?
        """
      
      val cursor = database.getReadableDatabase.rawQuery(sql, Array(id.toString))
      
      cursor.moveToFirst match {
        case true => Some(readStarSystem(cursor))
        case false => None
      }
    }
    
    def readStarSystem(cursor: Cursor) = StarSystem(cursor.getInt(0), cursor.getString(1), cursor.getInt(2))
  }
}

object StarSystemRegistry extends StarSystemRepositoryComponentImpl
