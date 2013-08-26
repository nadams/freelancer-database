package net.node3.freelancerdatabase

import android.database.Cursor
import scala.collection.Iterable
import scala.collection.immutable.Map
import android.content.ContentValues
import android.content.ContentValues
import scala.language.implicitConversions

package object db {
  
  class ScalaCursor(val cursor: Cursor) extends Iterable[Cursor] {
    def iterator = new Iterator[Cursor] {
      def hasNext = cursor.getCount > 0 && !cursor.isLast
      def next = { 
        if(!cursor.moveToNext) {
          cursor.close
        }
        
        cursor
      }
    }
  }
  
  implicit def niceCursor(cursor: Cursor) = new ScalaCursor(cursor)
}
