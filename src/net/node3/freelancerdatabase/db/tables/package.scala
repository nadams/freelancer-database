package net.node3.freelancerdatabase.db

import android.content.ContentValues

class ContentValueCombiner(value1: ContentValues) {
  def ~(value2: ContentValues) = {
    value1.putAll(value2)
    value1
  }
}

class PairCombiner(value1: Pair[String, String]) {
  def ~(value2: Pair[String, String]) = {
    val contentValues = new ContentValues
    contentValues.put(value1._1, value1._2)
    contentValues.put(value2._1, value2._2)

    contentValues
  }
}

package object tables {
  implicit def putString(value: Pair[String, String]) = {
    val values = new ContentValues
    values.put(value._1, value._2)

    values
  }

  implicit def combine(value1: ContentValues) = new ContentValueCombiner(value1)
  implicit def combine(value1: Pair[String, String]) = new PairCombiner(value1)
}
