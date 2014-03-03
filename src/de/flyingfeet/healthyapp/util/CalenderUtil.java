package de.flyingfeet.healthyapp.util;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.view.View;
import android.widget.TextView;
import de.flyingfeet.healthyapp.HealthyConstants;

public class CalenderUtil implements Serializable
{
	private static final long	serialVersionUID	= -4881485466493661454L;

	private Calendar				calendar;
	private SimpleDateFormat	formatter;

	public CalenderUtil()
	{
		calendar = Calendar.getInstance();
		calendar.setTimeZone( TimeZone.getTimeZone( "GMT+1" ) );
		formatter = new SimpleDateFormat( HealthyConstants.DATE_FORMAT, Locale.getDefault() );
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

	public String getFormattedDateAsString( Date date )
	{
		return formatter.format( date );
	}

	public void setTextOnView( View view )
	{
		setTextOnView( view, getDay(), getMonth(), getYear() );
	}

	public void setTextOnView( View view, int day, int month, int year )
	{
		String d = String.valueOf( day );
		String m = String.valueOf( month + 1 );
		if ( day < 10 )
		{
			d = "0" + d;
		}
		if ( ( month + 1 ) < 10 )
		{
			m = "0" + m;
		}
		if ( view instanceof TextView )
		{
			TextView editText = (TextView) view;
			editText.setText( d + "." + m + "." + year );
		}
	}

	public void setTextOnTimeView( View view )
	{
		setTextOnTimeView( view, getHour(), getMinute() );
	}

	public void setTextOnTimeView( View view, int hour, int minute )
	{
		String h = String.valueOf( hour );
		String m = String.valueOf( minute );
		if ( hour < 10 )
		{
			h = "0" + h;
		}
		if ( minute < 10 )
		{
			m = "0" + m;
		}
		if ( view instanceof TextView )
		{
			TextView editText = (TextView) view;
			editText.setText( h + ":" + m + " Uhr" );
		}
	}
}
