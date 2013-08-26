package net.node3.freelancerdatabase.db

import android.content.Context
import net.node3.freelancerdatabase.db.tables._

import net.node3.freelancerdatabase.entities.StarSystem

trait StarSystemRepositoryComponent {
  def systemRepository(context: Context) : StarSystemRepository  
}

trait StarSystemRepository {
  def getAll: Seq[StarSystem]
  def getById(id: Int): Option[StarSystem]
  def add(starSystem: StarSystem): Option[Long]
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
      
      cursor.map(StarSystem(_)).toSeq
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
        case true => Some(StarSystem(cursor))
        case false => None
      }
    }
    
    def add(starSystem: StarSystem) = 
      Some(database.getWritableDatabase.insert(StarSystemTable.tableName, null, StarSystem.unapply(starSystem)))
  }
}

object StarSystemRegistry extends StarSystemRepositoryComponentImpl
