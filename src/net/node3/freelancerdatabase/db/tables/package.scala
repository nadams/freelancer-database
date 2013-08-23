package net.node3.freelancerdatabase.db

import android.content.ContentValues
import scala.io.Source
import android.content.Context
import scala.xml.XML

class ContentValueCombiner(value1: ContentValues) {
  def ~(value2: ContentValues) = {
    value1.putAll(value2)
    value1
  }
}

class StringPairCombiner(value1: Pair[String, String]) {
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
  implicit def combine(value1: Pair[String, String]) = new StringPairCombiner(value1)
  
  def getXml(resourceId: Int, context: Context) = {
    val source = Source.fromInputStream(context.getResources.openRawResource(resourceId), "UTF-8")
    XML.loadString(source.mkString)
  }
}
