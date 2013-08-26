package net.node3.freelancerdatabase.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.ViewGroup
import net.node3.freelancerdatabase.R
import net.node3.freelancerdatabase.db.StarSystemRegistry
import android.view.SurfaceView

class SystemFragment extends Fragment {
  lazy val systemRepository = StarSystemRegistry.systemRepository(this.getActivity)
  lazy val systemView = getView.findViewById(R.id.system_view).asInstanceOf[SurfaceView]
  
  override def onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle) = {
    val system = systemRepository.getById(1)
    
    inflater.inflate(R.layout.fragment_system, container, false)  
  }
}