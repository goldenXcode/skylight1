package net.nycjava.skylight;

import java.io.IOException;

import net.nycjava.skylight.dependencyinjection.Dependency;
import net.nycjava.skylight.dependencyinjection.DependencyInjectingObjectFactory;
import net.nycjava.skylight.view.GetReadyView;
import net.nycjava.skylight.view.Preview;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.SurfaceHolder.Callback;
import android.widget.VideoView;

/**
 * waiting for player to obscure camera; camera obscured; counting down start; camera unobscured; countdown complete
 */
public class GetReadyActivity extends SkylightActivity {
	@Dependency
	private View view;

//	@Dependency
//	private Camera camera;

	@Override
	protected void addDependencies(DependencyInjectingObjectFactory aDependencyInjectingObjectFactory) {
//		aDependencyInjectingObjectFactory.registerImplementationObject(Camera.class, Camera.open());
		aDependencyInjectingObjectFactory.registerImplementationObject(View.class, getLayoutInflater().inflate(
				R.layout.getready, null));
//		aDependencyInjectingObjectFactory.registerImplementationObject(VideoView.class, new VideoView(this));
	}

	private VideoView mVideoView;
	
	private MediaPlayer mp;  
	private SurfaceView mPreview;  
	private SurfaceHolder holder;  
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Hide the window title.
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		getWindow().setFormat(PixelFormat.TRANSLUCENT);
		setContentView(R.layout.getready);
/*
		mVideoView=(VideoView)findViewById(R.id.getready);
		mVideoView.setVideoPath("/sdcard/passthedrink.mp4");
*/		
		mPreview=(SurfaceView)findViewById(R.id.getready);
		holder = mPreview.getHolder();
		holder.addCallback(new Callback() {

			@Override
			public void surfaceChanged(SurfaceHolder holder, int format,
					int width, int height) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void surfaceCreated(SurfaceHolder holder) {
				// TODO Auto-generated method stub
				mp = new MediaPlayer();
				mp.setDisplay( mPreview.getHolder());

//				mVideoView.setOnCompletionListener(
				mp.setOnCompletionListener(
					new OnCompletionListener() {
					public void onCompletion(MediaPlayer arg0) {
						final Intent intent = new Intent(GetReadyActivity.this, SkillTestActivity.class);
						startActivity(intent);
						finish();								
					} 
				});
				
				try {
					AssetFileDescriptor afd = getAssets().openFd("passthedrink.mp4");//getResources().openRawResourceFd();
					mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
					mp.prepare();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				mp.start();
				
			}

			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				// TODO Auto-generated method stub
				
			} });
		
	}

	@Override
	protected void onDestroy() {
//		camera.release();
		super.onDestroy();
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		final Intent intent = new Intent(GetReadyActivity.this, SkillTestActivity.class);
		startActivity(intent);
		finish();
		return true;
	}
	@Override
    public boolean onTouchEvent(MotionEvent event) {
//        float x = event.getX() - CENTER_X;
//        float y = event.getY() - CENTER_Y;
//        boolean inCenter = java.lang.Math.sqrt(x*x + y*y) <= CENTER_RADIUS;
        
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            	break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
        		final Intent intent = new Intent(GetReadyActivity.this, SkillTestActivity.class);
        		startActivity(intent);
        		finish();
                break;
        }
        return true;
    }

}