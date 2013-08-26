package net.node3.freelancerdatabase

import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.app.ActionBarDrawerToggle
import android.support.v4.app.FragmentActivity
import android.support.v4.widget.DrawerLayout
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import net.node3.freelancerdatabase.db.StarSystemRegistry

class MainActivity extends FragmentActivity {
  lazy val systemRepository = StarSystemRegistry.systemRepository(this)
  
  lazy val navLayout = findViewById(R.id.main_drawer_layout).asInstanceOf[DrawerLayout]
  lazy val navList = findViewById(R.id.main_nav_list).asInstanceOf[ListView]
  lazy val searchBox = findViewById(R.id.main_system_filter).asInstanceOf[EditText]
  lazy val filterButton = findViewById(R.id.main_system_filter_button)
  lazy val drawerToggle = new ActionBarDrawerToggle(this, navLayout, R.drawable.ic_drawer, R.string.main_drawer_open, R.string.main_drawer_close)
  lazy val adapter = new SystemAdapter(this, R.id.main_nav_list, systemRepository)
  
  override def onCreate(savedInstanceState: Bundle) = {
    super.onCreate(savedInstanceState)
    
    setContentView(R.layout.activity_main)

    navList.setAdapter(adapter)
    navList.onItemClick { (parent, view, position, id) => 
      val system = adapter.getItem(position)
      Toast.makeText(this, system.starSystem.toString, Toast.LENGTH_LONG).show
    }
    
    navLayout.setDrawerListener(drawerToggle)
    
    val actionBar = getActionBar
    actionBar.setDisplayHomeAsUpEnabled(true)
    actionBar.setHomeButtonEnabled(true)
    
    def search() = adapter.applyFilter(searchBox.getText.toString)
    
    filterButton.onClick { view => search }
    searchBox.onEditorAction { (textView: TextView, actionId: Int, event: KeyEvent) => search }
    
    if(savedInstanceState != null) {
      val filter = savedInstanceState.getString(SystemAdapter.filterKey)
      if(filter != null) {
        adapter.applyFilter(filter)
      }
    }
  }
  
  override def onSaveInstanceState(outState: Bundle) = {
    super.onSaveInstanceState(outState)
    
    outState.putString(SystemAdapter.filterKey, adapter.filter)
  }
  
  override def onPostCreate(savedInstanceState: Bundle) = {
    super.onPostCreate(savedInstanceState)
    
    drawerToggle.syncState
  }
  
  override def onConfigurationChanged(newConfig: Configuration) = {
    super.onConfigurationChanged(newConfig)
    drawerToggle.onConfigurationChanged(newConfig)
  }
  
  override def onOptionsItemSelected(item: MenuItem) = 
    if(drawerToggle.onOptionsItemSelected(item)) true 
    else super.onOptionsItemSelected(item)

  override def onCreateOptionsMenu(menu: Menu) = {
    getMenuInflater.inflate(R.menu.main, menu)
    
    true
  }
}
