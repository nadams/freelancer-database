package net.node3.freelancerdatabase;

import net.node3.freelancerdatabase.entities.SolarObject;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class StarSystemView extends SurfaceView implements SurfaceHolder.Callback {
	private Paint paint = new Paint();
	
	private SolarObject[] solarObjects;
	private GraphThread thread;

	public StarSystemView(Context context) {
		super(context);
		this.getHolder().addCallback(this);
		this.paint = this.getDefaultTextPaint();
	}

	public StarSystemView(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);
		this.getHolder().addCallback(this);
		this.paint = this.getDefaultTextPaint();
	}

	public StarSystemView(Context context, AttributeSet attributeSet, int style) {
		super(context, attributeSet, style);
		this.getHolder().addCallback(this);
		this.paint = this.getDefaultTextPaint();
	}
	
	private Paint getDefaultTextPaint() {
		Paint p = new Paint();
		p.setARGB(255, 255, 255, 255);
		p.setTextSize(48);
		p.setAntiAlias(true);
		p.setDither(true);
		
		return p;
	}
	
	public void startDrawing() {
		if(this.thread == null) {
			this.thread = new GraphThread(this);
			this.thread.startThread();
		}
	}
	
	public void stopDrawing() {
		if(this.thread != null) {
			this.thread.stopThread();
			boolean retry = true;
			while(retry) {
				try {
					this.thread.join();
					retry = false;
				} catch(InterruptedException e) { }
				
				this.thread = null;
			}
		}
	}

	public void setSolarObjects(SolarObject[] solarObjects) {
		this.solarObjects = solarObjects;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(Color.argb(255, 34, 34, 34));
		for(SolarObject solarObject : this.solarObjects) {
			String name = solarObject.name();
			canvas.drawText(solarObject.name(), 0, name.length(), solarObject.x(), solarObject.y(), this.paint);
		}	
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		this.startDrawing();
	}
	
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		this.stopDrawing();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) { }
	
	public static class GraphThread extends Thread {
		private final SurfaceHolder surface;
		private final StarSystemView view;
		private boolean isRunning;
		
		public GraphThread(StarSystemView view) {
			this.view = view;
			this.surface = view.getHolder();
		}
		
		public void startThread() {
			this.isRunning = true;
			
			super.start();
		}
		
		public void stopThread() {
			this.isRunning = false;
		}
		
		@Override
		public void run() {
			Canvas c = null;
			while(this.isRunning) {
				try {
					c = this.surface.lockCanvas();
					synchronized(this.surface) {
						if(c != null) {
							view.onDraw(c);
						}
						
						sleep(40);
					}
				} catch(InterruptedException e) {
				} finally {
					if(c != null) {
						surface.unlockCanvasAndPost(c);
					}
				}
			}
		}
	}
}
