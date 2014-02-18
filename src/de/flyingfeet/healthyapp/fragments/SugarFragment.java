package de.flyingfeet.healthyapp.fragments;

import android.app.Fragment;
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
import de.flyingfeet.healthyapp.R;
import de.flyingfeet.healthyapp.datetime.CalenderUtil;
import de.flyingfeet.healthyapp.util.NavigationUtil;
import de.flyingfeet.healthyapp.util.NumberPickerUtil;

public class SugarFragment extends Fragment
{
	private CalenderUtil calenderUtil = new CalenderUtil();
	private NavigationUtil navigationUtil;

	public SugarFragment()
	{
	}

	@Override
	public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState )
	{
		navigationUtil = new NavigationUtil( getActivity(), getFragmentManager() );

		View rootView = inflater.inflate( R.layout.sugar_fragment, container, false );
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
		loadNumberPicker();

		Button listPressures = (Button) view.findViewById( R.id.buttonListSugars );
		listPressures.setOnClickListener( new View.OnClickListener()
		{

			@Override
			public void onClick( View view )
			{
//				Intent intent = new Intent( getActivity(), PressureList.class );
//				startActivity( intent );
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
			Toast.makeText( getActivity(), "Speichern kommt noch...", Toast.LENGTH_SHORT ).show();
			navigationUtil.selectItem( 1 );
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

	private void loadNumberPicker()
	{
		NumberPicker sugarNumberPicker = (NumberPicker) getActivity().findViewById( R.id.sugarNumberPicker );

		String[] nums = NumberPickerUtil.loadNumberPickerValues( 200 );

		sugarNumberPicker.setMaxValue( nums.length - 1 );
		sugarNumberPicker.setMinValue( 0 );
		sugarNumberPicker.setWrapSelectorWheel( false );
		sugarNumberPicker.setDisplayedValues( nums );
		sugarNumberPicker.setValue( 100 );
	}
}
