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
import de.flyingfeet.healthyapp.models.Sugar;

public class SugarAdapter extends ArrayAdapter<Sugar>
{
	private final Context context;
	private final Sugar[] values;

	public SugarAdapter( Context context, Sugar[] values )
	{
		super( context, R.layout.sugar_layout, values );
		this.context = context;
		this.values = values;
	}

	@Override
	public View getView( int position, View convertView, ViewGroup parent )
	{
		LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
		View rowView = inflater.inflate( R.layout.sugar_layout, parent, false );
		TextView listDatePressure = (TextView) rowView.findViewById( R.id.listDateSugar );
		TextView listTimePressure = (TextView) rowView.findViewById( R.id.listTimeSugar );
		TextView listSystolic = (TextView) rowView.findViewById( R.id.listSugar );

		DateFormat dateFormat = new SimpleDateFormat( "dd.MM.yyyy" );
		listDatePressure.setText( dateFormat.format( values[position].getDate() ) );
		listTimePressure.setText( values[position].getTime() + " Uhr" );
		listSystolic.setText( values[position].getSugar() );

		return rowView;
	}
}
