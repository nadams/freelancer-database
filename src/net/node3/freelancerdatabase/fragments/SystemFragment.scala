package net.node3.freelancerdatabase.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.ViewGroup
import net.node3.freelancerdatabase.R
import net.node3.freelancerdatabase.db.StarSystemRegistry
import android.view.SurfaceView
import net.node3.freelancerdatabase.db.SolarObjectRepository
import net.node3.freelancerdatabase.db.SolarObjectRepositoryComponent
import net.node3.freelancerdatabase.db.SolarObjectRegistry
import android.graphics.Canvas
import android.graphics.Paint
import android.view.View
import android.content.Context
import net.node3.freelancerdatabase.entities.SolarObject
import android.widget.FrameLayout

class SystemFragment extends Fragment {
  lazy val solarObjectRepository = SolarObjectRegistry.solarObjectRepository(this.getActivity)
  lazy val systemView = getView.findViewById(R.id.system_view).asInstanceOf[StarSystemView]
  
  override def onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle) =
    inflater.inflate(R.layout.fragment_system, container, false)
    
  override def onViewCreated(view: View, savedInstanceState: Bundle) = {
    super.onViewCreated(view, savedInstanceState)
    
    systemView.setSolarObjects(solarObjectRepository.getAllBySystemId(1))
  }
}

class StarSystemView(val context: Context) extends View(context) {
  private var solarObjects: Seq[SolarObject] = Seq()
  private val paint = {
    val paint = new Paint
    paint.setARGB(255, 255, 255, 255)
    paint
  }
  
  override def onDraw(canvas: Canvas) = {
    super.onDraw(canvas)
    
    canvas.save
    
    solarObjects foreach { solarObject => 
      canvas.drawText(solarObject.name, 50, 100, solarObject.x, solarObject.y, paint)      
    }
    
    canvas.restore
    invalidate
  }
  
  def setSolarObjects(objects: Seq[SolarObject]) = solarObjects = objects
}
