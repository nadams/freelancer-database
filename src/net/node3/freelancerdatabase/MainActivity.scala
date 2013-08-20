package net.node3.freelancerdatabase

import android.app.Activity
import android.os.Bundle
import android.view.Menu
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import net.node3.freelancerdatabase.db.StarSystemRegistry

class MainActivity extends Activity with StarSystemRegistry {
  lazy val navLayout = findViewById(R.id.main_drawer_layout)
  lazy val navList = findViewById(R.id.main_nav_list).asInstanceOf[ListView]
  lazy val searchBox = findViewById(R.id.main_system_filter).asInstanceOf[EditText]
  
  override def onCreate(savedInstanceState: Bundle) = {
    super.onCreate(savedInstanceState)
    
    setContentView(R.layout.activity_main)
    
    navList.setAdapter(new ArrayAdapter(this, R.layout.drawer_list_item, systemRepository.getAll.toArray))
    navList.onItemClick { (parent, view, position, id) => Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show }
  }

  override def onCreateOptionsMenu(menu: Menu) = {
    getMenuInflater.inflate(R.menu.main, menu)
    
    true
  }
}
