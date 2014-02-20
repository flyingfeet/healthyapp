package de.flyingfeet.healthyapp.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import android.widget.Toast;
import de.flyingfeet.healthyapp.MainActivity;
import de.flyingfeet.healthyapp.models.Pressure;

public class DataStorage
{
	private static final DataStorage instance = new DataStorage();
	private Pressure[] pressures;

	private DataStorage()
	{
	}

	public static DataStorage getInstance()
	{
		return instance;
	}

	private Pressure[] parsePressure()
	{
		ArrayList<Pressure> list = new ArrayList<Pressure>();

		BufferedReader bufferedReader = null;
		try
		{
			String currentLine;
			FileReader fileReader = new FileReader( StorageUtil.getPressureFile() );
			bufferedReader = new BufferedReader( fileReader );
			while ( ( currentLine = bufferedReader.readLine() ) != null )
			{
				list.add( new Pressure( currentLine ) );
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
		finally
		{
			try
			{
				if ( bufferedReader != null )
					bufferedReader.close();
			}
			catch ( IOException ex )
			{
				Toast.makeText( MainActivity.activity, "Ein Fehler ist aufgetreten: " + ex.getLocalizedMessage(),
						Toast.LENGTH_SHORT ).show();
			}
		}

		Pressure[] values = new Pressure[list.size()];

		for ( int i = 0; i < values.length; i++ )
		{
			values[i] = list.get( i );
		}
		Arrays.sort( values, Pressure.PressureDateComparator );

		return values;
	}

	public Pressure[] getPressures( boolean forceReload )
	{
		if ( isListNullOrEmpty() || forceReload )
		{
			pressures = parsePressure();
		}

		return pressures;
	}

	public Pressure getLatestPressure()
	{
		if ( isListNullOrEmpty() )
		{
			pressures = parsePressure();
		}

		return pressures[0];
	}

	private boolean isListNullOrEmpty()
	{
		if ( pressures == null || pressures.length == 0 )
		{
			return true;
		}

		return false;
	}
}
