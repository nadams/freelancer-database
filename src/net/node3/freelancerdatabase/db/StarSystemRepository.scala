package net.node3.freelancerdatabase.db

import net.node3.freelancerdatabase.entities.StarSystem
import android.content.Context

trait StarSystemRepositoryComponent {
  def systemRepository(context: Context) : StarSystemRepository
  
  trait StarSystemRepository {
    def getAll : Iterable[StarSystem]
    def getById(id: Int) : Option[StarSystem]
  }
}

trait StarSystemRepositoryComponentImpl extends StarSystemRepositoryComponent {
  def systemRepository(context: Context) = new StarSystemRepositoryImpl(context)
  
  class StarSystemRepositoryImpl(val context: Context) extends StarSystemRepository {
    def getAll = Array(
        StarSystem(1, "Tau-37"), 
        StarSystem(2, "New York"), 
        StarSystem(3, "Omega-41"), 
        StarSystem(4, "Sovetskaya"),
        StarSystem(5, "Gurm"), 
        StarSystem(6, "Ryssk")
    )
    
    def getById(id: Int) = Some(StarSystem(1, "Tau-37"))
  }
}

object StarSystemRegistry extends StarSystemRepositoryComponentImpl
