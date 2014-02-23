package de.flyingfeet.healthyapp.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;
import de.flyingfeet.healthyapp.R;
import de.flyingfeet.healthyapp.util.CalenderUtil;

public class ChangeDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener
{

	private CalenderUtil calenderUtil = new CalenderUtil();

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		return calenderUtil.getDatePickerDialog(getActivity(), this);
	}

	@Override
	public void onDateSet(DatePicker picker, int year, int month, int day)
	{
		calenderUtil.setTextOnView(getActivity().findViewById(R.id.actualDate), day, month, year);
	}
}
