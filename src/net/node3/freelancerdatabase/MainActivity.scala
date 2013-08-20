package net.node3.freelancerdatabase

import android.app.Activity
import android.os.Bundle
import android.view.Menu
import android.widget.ListView
import android.widget.ListAdapter
import android.widget.ArrayAdapter

class MainActivity extends Activity {
  lazy val navLayout = findViewById(R.id.main_drawer_layout)
  lazy val navList = findViewById(R.id.main_nav_list).asInstanceOf[ListView]
  
  override def onCreate(savedInstanceState: Bundle) = {
    super.onCreate(savedInstanceState)
    
    setContentView(R.layout.activity_main)
    
    val systems = Array("Tau-37", "New York", "Omega-41", "Sovetskaya", "Gurm", "Ryssk")
    navList.setAdapter(new ArrayAdapter(this, R.layout.drawer_list_item, systems))
  }

  override def onCreateOptionsMenu(menu: Menu) = {
    getMenuInflater.inflate(R.menu.main, menu)
    
    true
  }
}
