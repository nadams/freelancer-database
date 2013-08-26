package net.node3.freelancerdatabase.db

import android.content.Context
import net.node3.freelancerdatabase.entities.SolarObject

trait SolarObjectRepositoryComponent {
  def solarObjectRepository(context: Context) : SolarObjectRepository
}

trait SolarObjectRepository {
  def getById(id: Int): Option[SolarObject]
  def getAllBySystemId(id: Int): Seq[SolarObject]
  def add(solarObject: SolarObject): Option[Long]
  def update(id: Int, newSolarObject: SolarObject): Boolean
  def delete(id: Int): Boolean
}

trait SolarObjectRepositoryComponentImpl extends SolarObjectRepositoryComponent {
  def solarObjectRepository(context: Context) = new SolarObjectRepositoryImpl(context)
  
  class SolarObjectRepositoryImpl(val context: Context) extends SolarObjectRepository {
    def getById(id: Int) = ???
    def getAllBySystemId(id: Int) = ???
    def add(solarObject: SolarObject) = ???
    def update(id: Int, newSolarObject: SolarObject) = ???
    def delete(id: Int) = ???
  }
}

object SolarObjectRegistry extends SolarObjectRepositoryComponentImpl
