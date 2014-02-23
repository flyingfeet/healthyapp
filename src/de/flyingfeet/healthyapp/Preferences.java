package de.flyingfeet.healthyapp;

import de.flyingfeet.healthyapp.util.DataStorage;
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
		pressureFile = prefs.getString( SUGAR_FILE_KEY, HealthyConstants.SUGAR_FILE_DEFAULT );

		initialized = true;
	}

	@Override
	public boolean onPreferenceChange( Preference preference, Object newValue )
	{
		final Editor editor = PreferenceManager.getDefaultSharedPreferences( getActivity() ).edit();
		if ( preference.equals( dataLocationPref ) )
		{
			dataLocation = (String) newValue;
			editor.putString( DATA_LOCATION_KEY, dataLocation );
		}
		else if ( preference.equals( pressureFilePref ) )
		{
			pressureFile = (String) newValue;
			editor.putString( PRESSURE_FILE_KEY, pressureFile );
		}
		else if ( preference.equals( sugarFilePref ) )
		{
			sugarFile = (String) newValue;
			editor.putString( SUGAR_FILE_KEY, sugarFile );
		}
		editor.commit();

		DataStorage.getInstance().reloadPressures();

		// Fragment neu laden, damit die Aenderung sofort sichtbar ist
		getFragmentManager().beginTransaction().replace( R.id.content_frame, new Preferences() ).addToBackStack( null )
				.commit();

		return false;
	}

}
