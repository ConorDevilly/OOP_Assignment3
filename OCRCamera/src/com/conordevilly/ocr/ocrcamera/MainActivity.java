package com.conordevilly.ocr.ocrcamera;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends Activity{
	
	private final String url = "http://192.168.1.124:8080/TestingREST";
	Context context;
	int duration;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context = getApplicationContext();
		duration = Toast.LENGTH_LONG;
		Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(intent, 0);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode, data);
		//Bitmap img = (Bitmap) data.getExtras().get("data");
		//File toSend = new File("toSend");
		Bitmap img = (Bitmap) data.getExtras().get("data");
		Log.i("OCRCamera", "Got data");
		
		try {
			File toSend = File.createTempFile("OCR", "png");
			FileOutputStream writer = new FileOutputStream(toSend);
			img.compress(Bitmap.CompressFormat.PNG, 80, writer);
			writer.close();

			new RestTask(url, MainActivity.this).execute(toSend);
		} catch (FileNotFoundException e) {
			Toast r = Toast.makeText(context, "File fucked", duration);
			r.show();
			e.printStackTrace();
		} catch (IOException e) {
			Toast r = Toast.makeText(context, "Writer not lcose", duration);
			r.show();
			e.printStackTrace();
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
