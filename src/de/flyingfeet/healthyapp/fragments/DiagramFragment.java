package de.flyingfeet.healthyapp.fragments;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jjoe64.graphview.CustomLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphView.LegendAlign;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.GraphViewSeries.GraphViewSeriesStyle;
import com.jjoe64.graphview.LineGraphView;

import de.flyingfeet.healthyapp.HealthyConstants;
import de.flyingfeet.healthyapp.R;
import de.flyingfeet.healthyapp.models.Pressure;
import de.flyingfeet.healthyapp.util.DataStorage;

public class DiagramFragment extends Fragment
{
	@Override
	public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState )
	{
		View rootView = inflater.inflate( R.layout.diagram_fragment, container, false );
		int i = getArguments().getInt( HealthyConstants.NAVIGATION_POSITION );
		String healthy = getResources().getStringArray( R.array.healthy_array )[i];

		getActivity().setTitle( healthy );
		setHasOptionsMenu( true );
		return rootView;
	}

	@Override
	public void onViewCreated( View view, Bundle savedInstanceState )
	{
		Pressure[] pressures = DataStorage.getInstance().getPressures();
		GraphViewData[] diastolicValues = new GraphViewData[pressures.length];
		GraphViewData[] systolicValues = new GraphViewData[pressures.length];
		GraphViewData[] pulseValues = new GraphViewData[pressures.length];
		for ( int i = 0; i < pressures.length; i++ )
		{
			diastolicValues[i] = new GraphViewData( i, Double.parseDouble( pressures[i].getDiastolic() ) );
			systolicValues[i] = new GraphViewData( i, Double.parseDouble( pressures[i].getSystolic() ) );
			pulseValues[i] = new GraphViewData( i, Double.parseDouble( pressures[i].getPulse() ) );
		}
		GraphViewSeries diastolicSeries = new GraphViewSeries( "Diastolisch", new GraphViewSeriesStyle( Color.rgb( 0,
				128, 0 ), 3 ), diastolicValues );
		GraphViewSeries systolicSeries = new GraphViewSeries( "Systolisch", new GraphViewSeriesStyle( Color.rgb( 0, 0,
				255 ), 3 ), systolicValues );
		GraphViewSeries pulseSeries = new GraphViewSeries( "Puls", new GraphViewSeriesStyle( Color.rgb( 128, 0, 0 ), 3 ),
				pulseValues );

		GraphView pressureGraphView = new LineGraphView( getActivity() // context
				, "Blutdruckwerte" // heading
		);
		pressureGraphView.addSeries( diastolicSeries );
		pressureGraphView.addSeries( systolicSeries );
		pressureGraphView.addSeries( pulseSeries );

		pressureGraphView.setShowLegend( true );
		pressureGraphView.setLegendAlign( LegendAlign.BOTTOM );
		pressureGraphView.getGraphViewStyle().setLegendWidth( 200 );

		LinearLayout pressureLayout = (LinearLayout) getActivity().findViewById( R.id.pressureGraph );
		pressureLayout.addView( pressureGraphView );
	}
}
