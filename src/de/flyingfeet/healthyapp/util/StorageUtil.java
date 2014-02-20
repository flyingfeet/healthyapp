package de.flyingfeet.healthyapp.util;

import java.io.File;
import java.io.RandomAccessFile;

import android.os.Environment;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;

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
			try
			{
				File myFile = getPressureFile();
				long fileLength = myFile.length();
				RandomAccessFile raf = new RandomAccessFile( myFile, "rw" );
				raf.seek( fileLength );
				raf.writeBytes( data );
				raf.close();
			}
			catch ( Exception e )
			{
				return false;
			}

			return true;
		}

		return false;
	}

	public static File getPressureFile()
	{
		return new File( Environment.getExternalStorageDirectory().getPath() + "/mysdfile.csv" );
	}
}
