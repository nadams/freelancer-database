package net.node3.freelancerdatabase.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(val context: Context, val tables: List[TableHelper]) 
extends SQLiteOpenHelper(context, DatabaseHelper.databaseName, null, DatabaseHelper.databaseVersion) {
  override def onCreate(db: SQLiteDatabase) = tables.foreach(table => table.onCreate(db))
  
  override def onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) = 
    tables.foreach(table => table.onUpgrade(db, oldVersion, newVersion))
}

object DatabaseHelper {
  val databaseName = "freelancer.db"
  val databaseVersion = 1
}
