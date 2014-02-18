package de.flyingfeet.healthyapp.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;
import de.flyingfeet.healthyapp.models.Pressure;

public class StorageUtil
{
	private Activity context;

	public StorageUtil( Activity context )
	{
		this.context = context;
	}

	public boolean isExternalStorageWritable()
	{
		String state = Environment.getExternalStorageState();
		if ( Environment.MEDIA_MOUNTED.equals( state ) )
		{
			return true;
		}
		return false;
	}

	public boolean isExternalStorageReadable()
	{
		String state = Environment.getExternalStorageState();
		if ( Environment.MEDIA_MOUNTED.equals( state ) || Environment.MEDIA_MOUNTED_READ_ONLY.equals( state ) )
		{
			return true;
		}
		return false;
	}

	public String makeDataToString( View... views )
	{
		StringBuffer stringBuffer = new StringBuffer();
		for ( View view : views )
		{
			if ( view instanceof TextView )
			{
				TextView textView = (TextView) view;
				String text = (String) textView.getText();
				if ( text.contains( "Uhr" ) )
				{
					text = text.substring( 0, text.indexOf( " " ) );
				}
				stringBuffer.append( text );
			}
			if ( view instanceof NumberPicker )
			{
				NumberPicker picker = (NumberPicker) view;
				stringBuffer.append( picker.getValue() );
			}
			stringBuffer.append( ";" );
		}
		stringBuffer.append( "\n" );
		return stringBuffer.toString();
	}

	public File storePressure( String data )
	{
		File myFile = getPressureFile();
		try
		{
			long fileLength = myFile.length();
			RandomAccessFile raf = new RandomAccessFile( myFile, "rw" );
			raf.seek( fileLength );
			raf.writeBytes( data );
			raf.close();
			Log.i( "Save Pressure", "Done writing SD 'mysdfile.txt'" );
		}
		catch ( Exception e )
		{
			Toast.makeText( context, "Beim Speichern ist ein Fehler aufgetreten...", Toast.LENGTH_SHORT ).show();
		}
		Toast.makeText( context, "Speichern war erfolgreich.", Toast.LENGTH_SHORT ).show();
		return myFile;
	}

	public Pressure[] parsePressure()
	{
		ArrayList<Pressure> list = new ArrayList<Pressure>();

		BufferedReader bufferedReader = null;
		try
		{
			String currentLine;
			FileReader fileReader = new FileReader( getPressureFile() );
			bufferedReader = new BufferedReader( fileReader );
			while ( ( currentLine = bufferedReader.readLine() ) != null )
			{
				list.add( new Pressure( currentLine ) );
			}
		}
		catch ( FileNotFoundException e )
		{
			Toast.makeText( context, "Datei nicht gefunden...", Toast.LENGTH_SHORT ).show();
		}
		catch ( IOException e )
		{
			Toast.makeText( context, "Ein Fehler ist aufgetreten...", Toast.LENGTH_SHORT ).show();
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
				Toast.makeText( context, "Ein Fehler ist aufgetreten...", Toast.LENGTH_SHORT ).show();
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

	public File getPressureFile()
	{
		return new File( Environment.getExternalStorageDirectory().getPath() + "/mysdfile.csv" );
	}
}
