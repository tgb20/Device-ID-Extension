package haus.orange.DeviceIdentification;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

import com.firebase.tubesock.Base64;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.annotations.UsesLibraries;
import com.google.appinventor.components.annotations.UsesPermissions;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.runtime.AndroidNonvisibleComponent;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.PermissionResultHandler;
import com.google.appinventor.components.runtime.util.MediaUtil;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewManager;
import android.widget.Button;
import android.widget.LinearLayout;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/*
Jacob Bashista 5/27/19

Link is a component designed to allow
devices to communicate across networks.
*/

@DesignerComponent(version = 2, description = "Creates a id for a devices hardware", category = ComponentCategory.EXTENSION, nonVisible = true, iconName = "https://orange.haus/link/idicon.png")
@SimpleObject(external = true)
@UsesLibraries(libraries = "")
@UsesPermissions(permissionNames = "")
public class DeviceIdentification extends AndroidNonvisibleComponent implements Component {
	
	
	private ComponentContainer container;

	public DeviceIdentification(ComponentContainer container) {
		super(container.$form());

		this.container = container;

	}
	
	/**
	 * Gets the devices ID
	 * 
	 * @return the device ID
	 */
	@SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Get Device ID")
	public String getDeviceID() {
		
		SharedPreferences prefs = this.container.$context().getSharedPreferences("haus.orange.streamlink", Context.MODE_PRIVATE);
		
		String foundID = prefs.getString("deviceID", "none");
		
		if(foundID.equals("none")) {
			foundID = UUID.randomUUID().toString();
			
			Editor editor = prefs.edit();
			
			editor.putString("deviceID", foundID);
			editor.commit();
		}
		
		
		return foundID;
	}
}
