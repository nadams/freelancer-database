package net.node3.freelancerdatabase.db

import android.database.sqlite.SQLiteDatabase

abstract class TableHelper {
  val logTag = "DBHelper"
  val revisions = Map[Int, TableRevision]()
  
  def onCreate(database: SQLiteDatabase) = {
    revisions.headOption match {
      case None => Unit
      case Some(tuple) => {
        tuple._2.applyRevision(database)
        onUpgrade(database, tuple._1, DatabaseHelper.databaseVersion)
      }
    }
  }
  
  def onUpgrade(database: SQLiteDatabase, oldVersion: Int, newVersion: Int) = {
    
  }
}