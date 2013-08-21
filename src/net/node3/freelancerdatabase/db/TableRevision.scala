package net.node3.freelancerdatabase.db

import android.database.sqlite.SQLiteDatabase

trait TableRevision {
  val revisionNumber : Int
  
  def applyRevision(database: SQLiteDatabase)
}
