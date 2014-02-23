package de.flyingfeet.healthyapp;

import roboguice.activity.RoboFragmentActivity;
import roboguice.inject.InjectResource;
import roboguice.inject.InjectView;
import android.app.DialogFragment;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import de.flyingfeet.healthyapp.fragments.ChangeDateFragment;
import de.flyingfeet.healthyapp.fragments.ChangeTimeFragment;
import de.flyingfeet.healthyapp.util.NavigationUtil;

public class MainActivity extends RoboFragmentActivity
{
	@InjectView(R.id.drawer_layout)
	DrawerLayout drawerLayout;

	@InjectView(R.id.left_drawer)
	ListView drawerList;

	@InjectResource(R.array.healthy_array)
	String[] healthyArray;

	private NavigationUtil navigationUtil;

	private ActionBarDrawerToggle drawerToggle;

	private CharSequence drawerTitle;
	private CharSequence mTitle;

	public static MainActivity activity;

	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_main );
		PreferenceManager.setDefaultValues( this, R.xml.preferences, false );

		activity = this;

		navigationUtil = new NavigationUtil( this, getFragmentManager() );
		mTitle = drawerTitle = getTitle();

		initDrawer();

		if ( savedInstanceState == null )
		{
			navigationUtil.selectItem( 0 );
		}

		Preferences.init( this );
	}

	private void initDrawer()
	{
		drawerLayout.setDrawerShadow( R.drawable.drawer_shadow, GravityCompat.START );
		drawerList.setAdapter( new ArrayAdapter<String>( this, R.layout.drawer_list_item, healthyArray ) );
		drawerList.setOnItemClickListener( new DrawerItemClickListener() );

		getActionBar().setDisplayHomeAsUpEnabled( true );
		getActionBar().setHomeButtonEnabled( true );

		drawerToggle = new ActionBarDrawerToggle( this, drawerLayout, R.drawable.ic_drawer, 0, 0 )
		{
			public void onDrawerClosed( View view )
			{
				getActionBar().setTitle( mTitle );
				invalidateOptionsMenu();
			}

			public void onDrawerOpened( View drawerView )
			{
				getActionBar().setTitle( drawerTitle );
				invalidateOptionsMenu();
			}
		};
		drawerLayout.setDrawerListener( drawerToggle );
	}

	@Override
	public boolean onCreateOptionsMenu( Menu menu )
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate( R.menu.main, menu );
		return super.onCreateOptionsMenu( menu );
	}

	@Override
	public boolean onPrepareOptionsMenu( Menu menu )
	{
		boolean drawerOpen = drawerLayout.isDrawerOpen( drawerList );
		menu.findItem( R.id.action_settings ).setVisible( !drawerOpen );
		return super.onPrepareOptionsMenu( menu );
	}

	@Override
	public boolean onOptionsItemSelected( MenuItem item )
	{
		if ( drawerToggle.onOptionsItemSelected( item ) )
		{
			return true;
		}
		switch ( item.getItemId() )
		{
		case R.id.action_settings:
			getFragmentManager().beginTransaction().replace( R.id.content_frame, new Preferences() ).addToBackStack( null )
					.commit();
			setTitle( getString( R.string.action_settings ) );
			return true;
		default:
			return super.onOptionsItemSelected( item );
		}
	}

	private class DrawerItemClickListener implements ListView.OnItemClickListener
	{
		@Override
		public void onItemClick( AdapterView<?> parent, View view, int position, long id )
		{
			navigationUtil.selectItem( position );
		}
	}

	@Override
	public void setTitle( CharSequence title )
	{
		mTitle = title;
		getActionBar().setTitle( mTitle );
	}

	@Override
	protected void onPostCreate( Bundle savedInstanceState )
	{
		super.onPostCreate( savedInstanceState );
		drawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged( Configuration newConfig )
	{
		super.onConfigurationChanged( newConfig );
		drawerToggle.onConfigurationChanged( newConfig );
	}

	public void selectDate( View view )
	{
		DialogFragment changeDateFragment = new ChangeDateFragment();
		changeDateFragment.show( getFragmentManager(), "DatePicker" );
	}

	public void selectTime( View view )
	{
		DialogFragment changeTimeFragment = new ChangeTimeFragment();
		changeTimeFragment.show( getFragmentManager(), "TimePicker" );
	}
}