package net.node3.freelancerdatabase

import android.app.Activity
import android.os.Bundle
import android.view.Menu

class MainActivity extends Activity {
  lazy val navLayout = findViewById(R.id.main_drawer_layout)
  lazy val navList = findViewById(R.id.main_nav_list)
  
  override def onCreate(savedInstanceState: Bundle) = {
    super.onCreate(savedInstanceState)
    
    setContentView(R.layout.activity_main)
  }

  override def onCreateOptionsMenu(menu: Menu) = {
    getMenuInflater.inflate(R.menu.main, menu)
    
    true
  }
}
