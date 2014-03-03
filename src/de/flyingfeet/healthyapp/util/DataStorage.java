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
import de.flyingfeet.healthyapp.models.Sugar;

public class DataStorage
{
	private static final DataStorage instance = new DataStorage();
	private Pressure[] pressures;
	private Sugar[] sugars;

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

	private Sugar[] parseSugar()
	{
		ArrayList<Sugar> list = new ArrayList<Sugar>();

		BufferedReader bufferedReader = null;
		try
		{
			String currentLine;
			FileReader fileReader = new FileReader( StorageUtil.getSugarFile() );
			bufferedReader = new BufferedReader( fileReader );
			while ( ( currentLine = bufferedReader.readLine() ) != null )
			{
				list.add( new Sugar( currentLine ) );
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

		Sugar[] values = new Sugar[list.size()];

		for ( int i = 0; i < values.length; i++ )
		{
			values[i] = list.get( i );
		}
		Arrays.sort( values, Sugar.SugarDateComparator );

		return values;
	}

	public void reloadPressures()
	{
		pressures = parsePressure();
	}

	public void reloadSugars()
	{
		sugars = parseSugar();
	}

	public Pressure[] getPressures()
	{
		if ( isPressuresListNullOrEmpty() )
		{
			pressures = parsePressure();
		}

		return pressures;
	}

	public Sugar[] getSugars()
	{
		if ( isSugarsListNullOrEmpty() )
		{
			sugars = parseSugar();
		}

		return sugars;
	}

	public Pressure getLatestPressure()
	{
		if ( isPressuresListNullOrEmpty() )
		{
			pressures = parsePressure();
		}

		if ( pressures.length > 0 )
		{
			return pressures[0];
		}
		else
		{
			return null;
		}
	}

	public Sugar getLatestSugar()
	{
		if ( isSugarsListNullOrEmpty() )
		{
			sugars = parseSugar();
		}

		if ( sugars.length > 0 )
		{
			return sugars[0];
		}
		else
		{
			return null;
		}
	}

	private boolean isPressuresListNullOrEmpty()
	{
		if ( pressures == null || pressures.length == 0 )
		{
			return true;
		}

		return false;
	}

	private boolean isSugarsListNullOrEmpty()
	{
		if ( sugars == null || sugars.length == 0 )
		{
			return true;
		}

		return false;
	}
}
