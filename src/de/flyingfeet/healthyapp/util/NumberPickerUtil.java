package de.flyingfeet.healthyapp.util;

public class NumberPickerUtil
{
	public static String[] loadNumberPickerValues( int count )
	{
		String[] nums = new String[count];
		for ( int i = 0; i < nums.length; i++ )
			nums[i] = Integer.toString( i );
		return nums;
	}
}
