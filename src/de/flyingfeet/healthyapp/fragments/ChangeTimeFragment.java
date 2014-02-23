package de.flyingfeet.healthyapp.fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.Bundle;
import android.widget.TimePicker;
import de.flyingfeet.healthyapp.R;
import de.flyingfeet.healthyapp.util.CalenderUtil;

public class ChangeTimeFragment extends DialogFragment implements
		OnTimeSetListener {

	private CalenderUtil calenderUtil = new CalenderUtil();

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		return calenderUtil.getTimePickerDialog(getActivity(), this);
	}

	@Override
	public void onTimeSet(TimePicker picker, int hour, int minute) {
		calenderUtil.setTextOnTimeView(
				getActivity().findViewById(R.id.actualTime), hour, minute);
	}
}
