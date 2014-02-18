package de.flyingfeet.healthyapp.models;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;

public class Pressure implements Comparable<Pressure>, Serializable
{
	private static final long serialVersionUID = -2862607445772408578L;

	private Date date;
	private String time;
	private String systolic;
	private String diastolic;
	private String pulse;

	public Pressure(String line)
	{
		parseLine(line);
	}

	private void parseLine(String line)
	{
		String[] split = line.split(";");
		String dateAsString = split[0];
		String[] splittedDate = dateAsString.split("\\.");
		Calendar calendar = new GregorianCalendar(parseInt(splittedDate[2]), parseInt(splittedDate[1]) - 1,
				parseInt(splittedDate[0]));
		date = calendar.getTime();
		this.time = split[1];
		this.systolic = split[2];
		this.diastolic = split[3];
		this.pulse = split[4];
	}

	private int parseInt(String s)
	{
		int i = Integer.parseInt(s);
		return i;
	}

	public static Comparator<Pressure> PressureDateComparator = new Comparator<Pressure>()
	{

		public int compare(Pressure pressure1, Pressure pressure2)
		{

			Date date1 = pressure1.getDate();
			Date date2 = pressure2.getDate();
			String time1 = pressure1.getTime();
			String time2 = pressure2.getTime();

			int compareDate = date2.compareTo(date1);
			if (compareDate != 0)
			{
				return compareDate;
			}

			// return date1.compareTo(date2);
			return time2.compareTo(time1);
		}

	};

	public Date getDate()
	{
		return date;
	}

	public void setDate(Date date)
	{
		this.date = date;
	}

	public String getTime()
	{
		return time;
	}

	public void setTime(String time)
	{
		this.time = time;
	}

	public String getSystolic()
	{
		return systolic;
	}

	public void setSystolic(String systolic)
	{
		this.systolic = systolic;
	}

	public String getDiastolic()
	{
		return diastolic;
	}

	public void setDiastolic(String diastolic)
	{
		this.diastolic = diastolic;
	}

	public String getPulse()
	{
		return pulse;
	}

	public void setPulse(String pulse)
	{
		this.pulse = pulse;
	}

	@Override
	public int compareTo(Pressure anotherPressure)
	{
		return this.getDate().compareTo(anotherPressure.getDate());
	}
}
