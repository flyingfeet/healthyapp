package de.flyingfeet.healthyapp;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import de.flyingfeet.healthyapp.adapters.SugarAdapter;
import de.flyingfeet.healthyapp.models.Sugar;
import de.flyingfeet.healthyapp.util.DataStorage;

public class SugarList extends RoboActivity
{
	@InjectView( R.id.listViewSugar )
	ListView listview;

	@Override
	public void onCreate( Bundle bundle )
	{
		super.onCreate( bundle );
		setContentView( R.layout.sugar_list );

		Sugar[] sugars = DataStorage.getInstance().getSugars();
		SugarAdapter adapter = new SugarAdapter( this, sugars );
		listview.setAdapter( adapter );
		listview.setOnItemClickListener( new AdapterView.OnItemClickListener()
		{
			@Override
			public void onItemClick( AdapterView<?> parent, View view, int position, long id )
			{
				// Sugar item = (Sugar) parent.getItemAtPosition( position );
				// Intent intent = new Intent( getBaseContext(),
				// ModifyPressure.class );
				// intent.putExtra( "pressureObject", item );
				// startActivity( intent );
			}
		} );
	}
}
