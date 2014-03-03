package de.flyingfeet.healthyapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Toast;
import de.flyingfeet.healthyapp.util.DataStorage;
import de.flyingfeet.healthyapp.util.StorageUtil;

public class Preferences extends PreferenceFragment implements OnPreferenceChangeListener
{
	private static boolean initialized = false;

	public static String dataLocation;
	public static String pressureFile;
	public static String sugarFile;

	private static final String DATA_LOCATION_KEY = "dataLocation";
	private static final String PRESSURE_FILE_KEY = "pressureFile";
	private static final String SUGAR_FILE_KEY = "sugarFile";

	private ListPreference dataLocationPref;
	private EditTextPreference pressureFilePref;
	private EditTextPreference sugarFilePref;

	@Override
	public void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		addPreferencesFromResource( R.xml.preferences );
	}

	@Override
	public void onViewCreated( View view, Bundle savedInstanceState )
	{
		init( getActivity() );

		dataLocationPref = (ListPreference) findPreference( DATA_LOCATION_KEY );
		dataLocationPref.setOnPreferenceChangeListener( this );

		pressureFilePref = (EditTextPreference) findPreference( PRESSURE_FILE_KEY );
		pressureFilePref.setOnPreferenceChangeListener( this );

		sugarFilePref = (EditTextPreference) findPreference( SUGAR_FILE_KEY );
		sugarFilePref.setOnPreferenceChangeListener( this );
	}

	public static void init( Context ctx )
	{
		if ( initialized )
		{
			return;
		}

		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences( ctx );

		dataLocation = prefs.getString( DATA_LOCATION_KEY, HealthyConstants.DATA_LOCATION_DEFAULT );
		pressureFile = prefs.getString( PRESSURE_FILE_KEY, HealthyConstants.PRESSURE_FILE_DEFAULT );
		sugarFile = prefs.getString( SUGAR_FILE_KEY, HealthyConstants.SUGAR_FILE_DEFAULT );

		initialized = true;
	}

	@Override
	public boolean onPreferenceChange( Preference preference, Object newValue )
	{
		final Editor editor = PreferenceManager.getDefaultSharedPreferences( getActivity() ).edit();
		File fileSrc = null;
		File fileDest = null;
		if ( preference.equals( dataLocationPref ) )
		{
			File pressureFileSrc = StorageUtil.getPressureFile();
			File sugarFileSrc = StorageUtil.getSugarFile();
			dataLocation = (String) newValue;
			File pressureFileDest = StorageUtil.getPressureFile();
			File sugarFileDest = StorageUtil.getSugarFile();
			copyFile( pressureFileSrc, pressureFileDest );
			copyFile( sugarFileSrc, sugarFileDest );
			editor.putString( DATA_LOCATION_KEY, dataLocation );
		}
		else if ( preference.equals( pressureFilePref ) )
		{
			fileSrc = StorageUtil.getPressureFile();
			pressureFile = (String) newValue;
			fileDest = StorageUtil.getPressureFile();
			editor.putString( PRESSURE_FILE_KEY, pressureFile );
		}
		else if ( preference.equals( sugarFilePref ) )
		{
			fileSrc = StorageUtil.getSugarFile();
			sugarFile = (String) newValue;
			fileDest = StorageUtil.getSugarFile();
			editor.putString( SUGAR_FILE_KEY, sugarFile );
		}
		if ( fileSrc != null && fileDest != null )
		{
			copyFile( fileSrc, fileDest );
		}
		editor.commit();

		DataStorage.getInstance().reloadPressures();

		// Fragment neu laden, damit die Aenderung sofort sichtbar ist
		getFragmentManager().beginTransaction().replace( R.id.content_frame, new Preferences() ).addToBackStack( null )
				.commit();

		return false;
	}

	private void copyFile( File src, File dest )
	{
		InputStream in = null;
		OutputStream out = null;
		try
		{
			in = new FileInputStream( src );
			out = new FileOutputStream( dest );

			// Transfer bytes from in to out
			byte[] buf = new byte[1024];
			int len;
			while ( ( len = in.read( buf ) ) > 0 )
			{
				out.write( buf, 0, len );
			}
			if ( in != null )
			{
				in.close();
				src.delete();
			}
			if ( out != null )
			{
				out.close();
			}
		}
		catch ( FileNotFoundException e )
		{
			Toast.makeText( MainActivity.activity, "Datei konnte nicht gefunden werden.", Toast.LENGTH_SHORT ).show();
		}
		catch ( IOException e )
		{
			Toast.makeText( MainActivity.activity, "Ein Fehler ist aufgetreten: " + e.getLocalizedMessage(),
					Toast.LENGTH_SHORT ).show();
		}
	}
}
