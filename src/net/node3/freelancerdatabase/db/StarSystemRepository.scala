package net.node3.freelancerdatabase.db

import net.node3.freelancerdatabase.entities.StarSystem

trait StarSystemRepositoryComponent {
  val systemRepository : StarSystemRepository
  
  trait StarSystemRepository {
    def getAll : Iterable[StarSystem]
    def getById(id: Int) : StarSystem
  }
}

trait StarSystemRepositoryComponentImpl extends StarSystemRepositoryComponent {
  val systemRepository = new StarSystemRepositoryImpl
  
  class StarSystemRepositoryImpl extends StarSystemRepository {
    def getAll = Array(
        StarSystem(1, "Tau-37"), 
        StarSystem(2, "New York"), 
        StarSystem(3, "Omega-41"), 
        StarSystem(4, "Sovetskaya"),
        StarSystem(5, "Gurm"), 
        StarSystem(6, "Ryssk")
    )
    
    def getById(id: Int) = StarSystem(1, "Tau-37")
  }
}
