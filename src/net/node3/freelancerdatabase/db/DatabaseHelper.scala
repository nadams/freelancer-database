package net.node3.freelancerdatabase.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import net.node3.freelancerdatabase.db.tables.SectorTable
import net.node3.freelancerdatabase.db.tables.StarSystemTable
import net.node3.freelancerdatabase.db.tables.SolarObjectTypeTable
import net.node3.freelancerdatabase.db.tables.SolarObjectTable
import net.node3.freelancerdatabase.db.tables.SystemConnectionTable

class DatabaseHelper(val context: Context)
  extends SQLiteOpenHelper(context, DatabaseHelper.databaseName, null, DatabaseHelper.databaseVersion) {
  
  private val tables = List[TableHelper](
    SectorTable,
    SolarObjectTypeTable,
    StarSystemTable,
    SolarObjectTable,
    SystemConnectionTable
  )
  
  override def onCreate(db: SQLiteDatabase) = {
    if (!db.isReadOnly()) {
      db.execSQL("PRAGMA foreign_keys=ON;");
    }
    
    tables.foreach(table => table.onCreate(db))
  }

  override def onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) =
    tables.foreach(table => table.onUpgrade(db, oldVersion, newVersion))
}

object DatabaseHelper {
  val databaseName = "freelancer.db"
  val databaseVersion = 1
}
