package net.node3.freelancerdatabase.db

import scala.collection.Iterator

import android.database.sqlite.SQLiteDatabase
import android.util.Log

abstract class TableHelper {
  private val logTag = "DBHelper"
  private val revisions = scala.collection.mutable.Map[Int, TableRevision]()
  private var newestRevision = 0
  
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
    for(version <- Array.range(oldVersion + 1, newVersion)) {
      if(revisions.contains(version) && revisions.head._1 != version) {
        database.beginTransaction
        
        try {
          revisions(version).applyRevision(database)
          database.setTransactionSuccessful
        } catch {
          case e: Throwable => Log.e(logTag, "An exception occurred while upgrading the database", e)
        } finally {
          database.endTransaction
        }
      }
    }
  }
  
  def registerRevision(revision: TableRevision) = {
    val revisionNumber = revision.revisionNumber
    
    if(revisionNumber > newestRevision) {
      newestRevision = revisionNumber
    }
    
    revisions.put(revisionNumber, revision)
  }
}
