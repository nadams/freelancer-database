package net.node3.freelancerdatabase.db

import android.content.Context
import net.node3.freelancerdatabase.entities.SolarObject
import net.node3.freelancerdatabase.db.tables.SolarObjectTable
import net.node3.freelancerdatabase.db.tables.SolarObjectTypeTable
import android.database.Cursor
import net.node3.freelancerdatabase.entities.SolarObjectType

trait SolarObjectRepositoryComponent {
  def solarObjectRepository(context: Context): SolarObjectRepository
}

trait SolarObjectRepository {
  def getById(id: Int): Option[SolarObject]
  def getAllBySystemId(id: Int): Seq[SolarObject]
  def add(solarObject: SolarObject): Option[Long]
  def update(id: Int, newSolarObject: SolarObject): Int
  def delete(id: Int): Int
}

trait SolarObjectRepositoryComponentImpl extends SolarObjectRepositoryComponent {
  def solarObjectRepository(context: Context) = new SolarObjectRepositoryImpl(context)

  class SolarObjectRepositoryImpl(val context: Context) extends SolarObjectRepository {
    val database = new DatabaseHelper(context)

    def getById(id: Int): Option[SolarObject] = {
      val sql =
        f"""
        	SELECT 
        		so.${SolarObjectTable.id},
        		so.${SolarObjectTable.name},
        		so.${SolarObjectTable.x},
        		so.${SolarObjectTable.y},
        		so.${SolarObjectTable.solar_object_type_id},
        		sat.${SolarObjectTypeTable.name}
        	FROM ${SolarObjectTable.tableName} AS so
      			INNER JOIN ${SolarObjectTypeTable.tableName} AS sat ON so.${SolarObjectTable.solar_object_type_id} = sat.${SolarObjectTypeTable.id} 
      		WHERE ${SolarObjectTable.id} = ?
        """

      SolarObject(database.getReadableDatabase.rawQuery(sql, Array(id.toString)))
    }

    def getAllBySystemId(id: Int) = ???
    def add(solarObject: SolarObject) = ???

    def update(id: Int, newSolarObject: SolarObject) =
      database.getWritableDatabase.update(
        SolarObjectTable.tableName,
        newSolarObject.toContentValues,
        f"${SolarObjectTable.id} = ?",
        Array(id.toString))

    def delete(id: Int) =
      database.getWritableDatabase.delete(
        SolarObjectTable.tableName,
        f"${SolarObjectTable.id} = ?",
        Array(id.toString))
  }
}

object SolarObjectRegistry extends SolarObjectRepositoryComponentImpl
