package de.flyingfeet.healthyapp.datetime;

import java.io.Serializable;
import java.util.Calendar;
import java.util.TimeZone;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.view.View;
import android.widget.TextView;

public class CalenderUtil implements Serializable
{
	private static final long serialVersionUID = -4881485466493661454L;

	private Calendar calendar;

	public CalenderUtil()
	{
		calendar = Calendar.getInstance();
		calendar.setTimeZone( TimeZone.getTimeZone( "GMT+1" ) );
	}

	public DatePickerDialog getDatePickerDialog( Activity activity, DatePickerDialog.OnDateSetListener listener )
	{
		int year = getYear();
		int month = getMonth();
		int day = getDay();
		return new DatePickerDialog( activity, listener, year, month, day );
	}

	public TimePickerDialog getTimePickerDialog( Activity activity, TimePickerDialog.OnTimeSetListener listener )
	{
		int hour = getHour();
		int minute = getMinute();
		return new TimePickerDialog( activity, listener, hour, minute, true );
	}

	public int getYear()
	{
		return calendar.get( Calendar.YEAR );
	}

	public int getMonth()
	{
		return calendar.get( Calendar.MONTH );
	}

	public int getDay()
	{
		return calendar.get( Calendar.DAY_OF_MONTH );
	}

	public int getHour()
	{
		return calendar.get( Calendar.HOUR_OF_DAY );
	}

	public int getMinute()
	{
		return calendar.get( Calendar.MINUTE );
	}

	public void setTextOnView( View view )
	{
		setTextOnView( view, getDay(), getMonth(), getYear() );
	}

	public void setTextOnView( View view, int day, int month, int year )
	{
		if ( view instanceof TextView )
		{
			TextView editText = (TextView) view;
			if ( month < 10 )
			{
				editText.setText( day + ".0" + ( month + 1 ) + "." + year );
			}
			else
			{
				editText.setText( day + "." + ( month + 1 ) + "." + year );
			}
		}
	}

	public void setTextOnTimeView( View view )
	{
		setTextOnTimeView( view, getHour(), getMinute() );
	}

	public void setTextOnTimeView( View view, int hour, int minute )
	{
		if ( view instanceof TextView )
		{
			TextView editText = (TextView) view;
			if ( minute < 10 )
			{
				editText.setText( hour + ":0" + minute + " Uhr" );
			}
			else if ( hour < 10 )
			{
				editText.setText( "0" + hour + ":" + minute + " Uhr" );
			}
			else if ( hour < 10 && minute < 10 )
			{
				editText.setText( "0" + hour + ":0" + minute + " Uhr" );
			}
			else
			{
				editText.setText( hour + ":" + minute + " Uhr" );
			}
		}
	}
}
