package de.flyingfeet.healthyapp;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import de.flyingfeet.healthyapp.adapters.PressureAdapter;
import de.flyingfeet.healthyapp.models.Pressure;
import de.flyingfeet.healthyapp.util.StorageUtil;

public class PressureList extends RoboActivity
{
	@InjectView(R.id.listViewPressure)
	ListView listview;

	private StorageUtil storageUtil;

	@Override
	public void onCreate( Bundle bundle )
	{
		super.onCreate( bundle );
		setContentView( R.layout.pressure_list );

		storageUtil = new StorageUtil( this );
		Pressure[] values = storageUtil.parsePressure();
		PressureAdapter adapter = new PressureAdapter( this, values );
		listview.setAdapter( adapter );
		listview.setOnItemClickListener( new AdapterView.OnItemClickListener()
		{

			@Override
			public void onItemClick( AdapterView<?> parent, View view, int position, long id )
			{
				Pressure item = (Pressure) parent.getItemAtPosition( position );
				Intent intent = new Intent( getBaseContext(), ModifyPressure.class );
				intent.putExtra( "pressureObject", item );
				startActivity( intent );
			}
		} );
	}
}
