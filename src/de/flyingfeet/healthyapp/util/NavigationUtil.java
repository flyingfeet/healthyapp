package de.flyingfeet.healthyapp.util;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.widget.ListView;
import de.flyingfeet.healthyapp.HealthyConstants;
import de.flyingfeet.healthyapp.R;
import de.flyingfeet.healthyapp.fragments.DiagramFragment;
import de.flyingfeet.healthyapp.fragments.PressureFragment;
import de.flyingfeet.healthyapp.fragments.SugarFragment;

public class NavigationUtil
{
	private FragmentManager fragmentManager;
	private Activity activity;

	public NavigationUtil( Activity activity, FragmentManager fragmentManager )
	{
		this.activity = activity;
		this.fragmentManager = fragmentManager;
	}

	public void selectItem( int position )
	{
		Fragment fragment = null;
		Bundle arguments = new Bundle();

		switch ( position )
		{
		case 0:
			fragment = new PressureFragment();
			arguments.putInt( HealthyConstants.NAVIGATION_POSITION, position );
			fragment.setArguments( arguments );
			break;
		case 1:
			fragment = new SugarFragment();
			arguments.putInt( HealthyConstants.NAVIGATION_POSITION, position );
			fragment.setArguments( arguments );
			break;
		case 2:
			fragment = new DiagramFragment();
			arguments.putInt( HealthyConstants.NAVIGATION_POSITION, position );
			fragment.setArguments( arguments );
			break;
		default:
			break;
		}

		fragmentManager.beginTransaction().replace( R.id.content_frame, fragment ).commit();

		ListView mDrawerList = (ListView) activity.findViewById( R.id.left_drawer );
		String[] mPlanetTitles = activity.getResources().getStringArray( R.array.healthy_array );
		DrawerLayout mDrawerLayout = (DrawerLayout) activity.findViewById( R.id.drawer_layout );

		mDrawerList.setItemChecked( position, true );
		activity.setTitle( mPlanetTitles[position] );
		mDrawerLayout.closeDrawer( mDrawerList );
	}
}
