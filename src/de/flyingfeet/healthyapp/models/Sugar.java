package de.flyingfeet.healthyapp.models;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;

public class Sugar implements Comparable<Sugar>, Serializable
{
	private static final long serialVersionUID = 7942961113952888564L;

	private Date date;
	private String time;
	private String sugar;

	public Sugar( String line )
	{
		parseLine( line );
	}

	private void parseLine( String line )
	{
		String[] split = line.split( ";" );
		String dateAsString = split[0];
		String[] splittedDate = dateAsString.split( "\\." );
		Calendar calendar = new GregorianCalendar( parseInt( splittedDate[2] ), parseInt( splittedDate[1] ) - 1,
				parseInt( splittedDate[0] ) );
		date = calendar.getTime();
		this.time = split[1];
		this.sugar = split[2];
	}

	private int parseInt( String s )
	{
		int i = Integer.parseInt( s );
		return i;
	}

	public static Comparator<Sugar> SugarDateComparator = new Comparator<Sugar>()
	{

		public int compare( Sugar sugar1, Sugar sugar2 )
		{

			Date date1 = sugar1.getDate();
			Date date2 = sugar2.getDate();
			String time1 = sugar1.getTime();
			String time2 = sugar2.getTime();

			int compareDate = date2.compareTo( date1 );
			if ( compareDate != 0 )
			{
				return compareDate;
			}

			// return
			// date1.compareTo(date2);
			return time2.compareTo( time1 );
		}

	};

	@Override
	public int compareTo( Sugar anotherSugar )
	{
		return this.getDate().compareTo( anotherSugar.getDate() );
	}

	public Date getDate()
	{
		return date;
	}

	public void setDate( Date date )
	{
		this.date = date;
	}

	public String getTime()
	{
		return time;
	}

	public void setTime( String time )
	{
		this.time = time;
	}

	public String getSugar()
	{
		return sugar;
	}

	public void setSugar( String sugar )
	{
		this.sugar = sugar;
	}

}
