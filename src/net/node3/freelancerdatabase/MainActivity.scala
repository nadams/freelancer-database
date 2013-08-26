package net.node3.freelancerdatabase

import android.app.Activity
import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.app.ActionBarDrawerToggle
import android.support.v4.widget.DrawerLayout
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import net.node3.freelancerdatabase.db.StarSystemRegistry
import android.widget.TextView
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo

class MainActivity extends Activity {
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
    navList.onItemClick { (parent, view, position, id) => Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show }
    navLayout.setDrawerListener(drawerToggle)
    
    val actionBar = getActionBar
    actionBar.setDisplayHomeAsUpEnabled(true)
    actionBar.setHomeButtonEnabled(true)
    
    def search = adapter.applyFilter(searchBox.getText.toString)
    
    filterButton.onClick { view => search }
    searchBox.onEditorAction { (textView: TextView, actionId: Int, event: KeyEvent) => search }
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
