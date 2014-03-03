package de.flyingfeet.healthyapp.util;

import java.io.File;
import java.io.RandomAccessFile;

import android.os.Environment;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;
import de.flyingfeet.healthyapp.Preferences;

public class StorageUtil
{
	public StorageUtil()
	{
	}

	public static boolean isExternalStorageWritable()
	{
		String state = Environment.getExternalStorageState();
		if ( Environment.MEDIA_MOUNTED.equals( state ) )
		{
			return true;
		}
		return false;
	}

	public static boolean isExternalStorageReadable()
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

	public boolean storePressure( String data )
	{
		if ( isExternalStorageWritable() )
		{
			File file = getPressureFile();

			return storeData( file, data );
		}

		return false;
	}

	public boolean storeSugar( String data )
	{
		if ( isExternalStorageWritable() )
		{
			File file = getSugarFile();

			return storeData( file, data );
		}

		return false;
	}

	private boolean storeData( File file, String data )
	{
		try
		{
			long fileLength = file.length();
			RandomAccessFile raf = new RandomAccessFile( file, "rw" );
			raf.seek( fileLength );
			raf.writeBytes( data );
			raf.close();
			return true;
		}
		catch ( Exception e )
		{
			return false;
		}
	}

	public static File getPressureFile()
	{
		String dataLocation = getDataLocation();
		File file = new File( dataLocation + Preferences.pressureFile + ".csv" );
		return file;
	}

	public static File getSugarFile()
	{
		String dataLocation = getDataLocation();
		File file = new File( dataLocation + Preferences.sugarFile + ".csv" );
		return file;
	}

	private static String getDataLocation()
	{
		String storagePath = null;
		if ( Preferences.dataLocation.equals( "sdcard0" ) )
		{
			storagePath = "/mnt/sdcard";
		}
		else
		{
			storagePath = "/mnt/extSdCard";
		}
		return storagePath + File.separator;
	}
}
