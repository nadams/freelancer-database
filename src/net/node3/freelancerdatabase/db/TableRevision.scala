package net.node3.freelancerdatabase.db

import android.database.sqlite.SQLiteDatabase

trait TableRevision {
  def applyRevision(database: SQLiteDatabase)
  def getRevisionNumber() : Int
}
