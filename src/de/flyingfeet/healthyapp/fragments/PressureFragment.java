package de.flyingfeet.healthyapp.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;
import de.flyingfeet.healthyapp.HealthyConstants;
import de.flyingfeet.healthyapp.PressureList;
import de.flyingfeet.healthyapp.R;
import de.flyingfeet.healthyapp.models.Pressure;
import de.flyingfeet.healthyapp.util.CalenderUtil;
import de.flyingfeet.healthyapp.util.DataStorage;
import de.flyingfeet.healthyapp.util.NavigationUtil;
import de.flyingfeet.healthyapp.util.NumberPickerUtil;
import de.flyingfeet.healthyapp.util.StorageUtil;

public class PressureFragment extends Fragment
{
	private CalenderUtil calenderUtil = new CalenderUtil();
	private NavigationUtil navigationUtil;

	public PressureFragment()
	{
	}

	@Override
	public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState )
	{
		navigationUtil = new NavigationUtil( getActivity(), getFragmentManager() );

		View rootView = inflater.inflate( R.layout.pressure_fragment, container, false );
		int i = getArguments().getInt( HealthyConstants.NAVIGATION_POSITION );
		String healthy = getResources().getStringArray( R.array.healthy_array )[i];

		getActivity().setTitle( healthy );
		setHasOptionsMenu( true );
		return rootView;
	}

	@Override
	public void onViewCreated( View view, Bundle savedInstanceState )
	{
		setDateAndTime();
		loadNumberPickers();

		Button listPressures = (Button) view.findViewById( R.id.buttonListPressure );
		listPressures.setOnClickListener( new View.OnClickListener()
		{

			@Override
			public void onClick( View view )
			{
				Intent intent = new Intent( getActivity(), PressureList.class );
				startActivity( intent );
			}
		} );
	}

	@Override
	public void onCreateOptionsMenu( Menu menu, MenuInflater inflater )
	{
		inflater.inflate( R.menu.save, menu );
		super.onCreateOptionsMenu( menu, inflater );
	}

	@Override
	public void onPrepareOptionsMenu( Menu menu )
	{
		DrawerLayout mDrawerLayout = (DrawerLayout) getActivity().findViewById( R.id.drawer_layout );
		ListView mDrawerList = (ListView) getActivity().findViewById( R.id.left_drawer );
		boolean drawerOpen = mDrawerLayout.isDrawerOpen( mDrawerList );
		menu.findItem( R.id.save ).setVisible( !drawerOpen );
		super.onPrepareOptionsMenu( menu );
	}

	@Override
	public boolean onOptionsItemSelected( MenuItem item )
	{
		switch ( item.getItemId() )
		{
		case R.id.save:
			savePressure();
			DataStorage.getInstance().reloadPressures();
			navigationUtil.selectItem( 0 );
			return true;
		default:
			return super.onOptionsItemSelected( item );
		}
	}

	private void setDateAndTime()
	{
		TextView actualDate = (TextView) getActivity().findViewById( R.id.actualDate );
		TextView actualTime = (TextView) getActivity().findViewById( R.id.actualTime );
		calenderUtil.setTextOnView( actualDate );
		calenderUtil.setTextOnTimeView( actualTime );
	}

	private void loadNumberPickers()
	{
		Pressure latestPressure = DataStorage.getInstance().getLatestPressure();
		if ( latestPressure != null )
		{
			loadSystolicNumberPicker( latestPressure.getSystolic() );
			loadDiastolicNumberPicker( latestPressure.getDiastolic() );
			loadPulseNumberPicker( latestPressure.getPulse() );
		}
		else
		{
			// Default Werte laden, falls csv-Datei nicht existiert
			loadSystolicNumberPicker( "120" );
			loadDiastolicNumberPicker( "80" );
			loadPulseNumberPicker( "70" );
		}
	}

	private void loadSystolicNumberPicker( String value )
	{
		NumberPicker systolicNumberPicker = (NumberPicker) getActivity().findViewById( R.id.systolicNumberPicker );

		String[] nums = NumberPickerUtil.loadNumberPickerValues( 200 );

		systolicNumberPicker.setMaxValue( nums.length - 1 );
		systolicNumberPicker.setMinValue( 0 );
		systolicNumberPicker.setWrapSelectorWheel( false );
		systolicNumberPicker.setDisplayedValues( nums );
		systolicNumberPicker.setValue( Integer.parseInt( value ) );
	}

	private void loadDiastolicNumberPicker( String value )
	{
		NumberPicker diastolicNumberPicker = (NumberPicker) getActivity().findViewById( R.id.diastolicNumberPicker );

		String[] nums = NumberPickerUtil.loadNumberPickerValues( 150 );

		diastolicNumberPicker.setMaxValue( nums.length - 1 );
		diastolicNumberPicker.setMinValue( 0 );
		diastolicNumberPicker.setWrapSelectorWheel( false );
		diastolicNumberPicker.setDisplayedValues( nums );
		diastolicNumberPicker.setValue( Integer.parseInt( value ) );
	}

	private void loadPulseNumberPicker( String value )
	{
		NumberPicker pulseNumberPicker = (NumberPicker) getActivity().findViewById( R.id.pulseNumberPicker );

		String[] nums = NumberPickerUtil.loadNumberPickerValues( 180 );

		pulseNumberPicker.setMaxValue( nums.length - 1 );
		pulseNumberPicker.setMinValue( 0 );
		pulseNumberPicker.setWrapSelectorWheel( false );
		pulseNumberPicker.setDisplayedValues( nums );
		pulseNumberPicker.setValue( Integer.parseInt( value ) );
	}

	private void savePressure()
	{
		TextView actualDate = (TextView) getActivity().findViewById( R.id.actualDate );
		TextView actualTime = (TextView) getActivity().findViewById( R.id.actualTime );
		NumberPicker diaPicker = (NumberPicker) getActivity().findViewById( R.id.diastolicNumberPicker );
		NumberPicker sysPicker = (NumberPicker) getActivity().findViewById( R.id.systolicNumberPicker );
		NumberPicker pulsePicker = (NumberPicker) getActivity().findViewById( R.id.pulseNumberPicker );

		StorageUtil storageUtil = new StorageUtil();
		String data = storageUtil.makeDataToString( actualDate, actualTime, sysPicker, diaPicker, pulsePicker );
		boolean storePressure = storageUtil.storePressure( data );
		if ( !storePressure )
		{
			Toast.makeText( getActivity(), "Beim Speichern ist ein Fehler aufgetreten...", Toast.LENGTH_SHORT ).show();
		}
		else
		{
			Toast.makeText( getActivity(), "Speichern war erfolgreich.", Toast.LENGTH_SHORT ).show();
		}
	}
}
