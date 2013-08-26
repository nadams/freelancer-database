package net.node3

import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.TextView
import android.view.KeyEvent

package object freelancerdatabase {
  class ScalaOnClickListener(val view: View) {
    def onClick(action: View => Any) =
      view.setOnClickListener(new View.OnClickListener() {
        override def onClick(view: View) = action(view)
      })
  }
  
  class ScalaEditorActionListener(val textView: TextView) {
    def onEditorAction(action: (TextView, Int, KeyEvent) => Boolean) = 
      textView.setOnEditorActionListener(new TextView.OnEditorActionListener {
        override def onEditorAction(v: TextView, actionId: Int, event: KeyEvent) = 
          action(v, actionId, event)
      })
  }
  
  class ScalaOnItemClick(val listView: ListView) {
    def onItemClick(action: (AdapterView[_], View, Int, Long) => Any) = 
      listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        override def onItemClick(parent: AdapterView[_], view: View, pos: Int, id: Long) = 
          action(parent, view, pos, id)
      })
  }
  
  implicit def onClick(view: View) = new ScalaOnClickListener(view)
  implicit def onItemClick(listView: ListView) = new ScalaOnItemClick(listView)
  implicit def onEditorAction(textView: TextView) = new ScalaEditorActionListener(textView)
}
