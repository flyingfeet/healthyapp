package de.flyingfeet.healthyapp.adapters;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import de.flyingfeet.healthyapp.R;
import de.flyingfeet.healthyapp.models.Pressure;

public class PressureAdapter extends ArrayAdapter<Pressure>
{
	private final Context context;
	private final Pressure[] values;

	public PressureAdapter( Context context, Pressure[] values )
	{
		super( context, R.layout.pressure_layout, values );
		this.context = context;
		this.values = values;
	}

	@Override
	public View getView( int position, View convertView, ViewGroup parent )
	{
		LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
		View rowView = inflater.inflate( R.layout.pressure_layout, parent, false );
		TextView listDatePressure = (TextView) rowView.findViewById( R.id.listDatePressure );
		TextView listTimePressure = (TextView) rowView.findViewById( R.id.listTimePressure );
		TextView listSystolic = (TextView) rowView.findViewById( R.id.listSystolic );
		TextView listDiastolic = (TextView) rowView.findViewById( R.id.listDiastolic );
		TextView listPulse = (TextView) rowView.findViewById( R.id.listPulse );

		DateFormat dateFormat = new SimpleDateFormat( "dd.MM.yyyy" );
		listDatePressure.setText( dateFormat.format( values[position].getDate() ) );
		listTimePressure.setText( values[position].getTime() + " Uhr" );
		listSystolic.setText( values[position].getSystolic() );
		listDiastolic.setText( values[position].getDiastolic() );
		listPulse.setText( values[position].getPulse() );

		return rowView;
	}
}
