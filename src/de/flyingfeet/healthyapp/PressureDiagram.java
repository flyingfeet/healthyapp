package de.flyingfeet.healthyapp;

import java.util.ArrayList;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.os.Bundle;

import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.PointLabelFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;

import de.flyingfeet.healthyapp.models.Pressure;
import de.flyingfeet.healthyapp.util.DataStorage;

public class PressureDiagram extends RoboActivity
{
	@InjectView( R.id.diagram_pressure )
	private XYPlot pressurePlot;

	@InjectView( R.id.diagram_pressure_pulse )
	private XYPlot pressurePulsePlot;

	@Override
	public void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		setContentView( R.layout.pressure_diagram );

		Pressure[] pressures = DataStorage.getInstance().getPressures();
		ArrayList<Number> systolicValues = new ArrayList<Number>();
		ArrayList<Number> diastolicValues = new ArrayList<Number>();
		ArrayList<Number> pulseValues = new ArrayList<Number>();
		for ( int i = 0; i < pressures.length; i++ )
		{
			String systolic = pressures[i].getSystolic();
			String diastolic = pressures[i].getDiastolic();
			String pulse = pressures[i].getPulse();
			systolicValues.add( Integer.parseInt( systolic ) );
			diastolicValues.add( Integer.parseInt( diastolic ) );
			pulseValues.add( Integer.parseInt( pulse ) );
		}

		XYSeries systolicSeries = new SimpleXYSeries( systolicValues, SimpleXYSeries.ArrayFormat.Y_VALS_ONLY,
				"Systolisch" );
		XYSeries diastolicSeries = new SimpleXYSeries( diastolicValues, SimpleXYSeries.ArrayFormat.Y_VALS_ONLY,
				"Diastolisch" );
		XYSeries pulseSeries = new SimpleXYSeries( diastolicValues, SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Puls" );

		LineAndPointFormatter systolicFormat = new LineAndPointFormatter();
		systolicFormat.setPointLabelFormatter( new PointLabelFormatter() );
		systolicFormat.configure( getApplicationContext(), R.xml.line_point_formatter_green );
		pressurePlot.addSeries( systolicSeries, systolicFormat );

		LineAndPointFormatter diastolicFormat = new LineAndPointFormatter();
		diastolicFormat.setPointLabelFormatter( new PointLabelFormatter() );
		diastolicFormat.configure( getApplicationContext(), R.xml.line_point_formatter_blue );
		pressurePlot.addSeries( diastolicSeries, diastolicFormat );

		LineAndPointFormatter pulseFormat = new LineAndPointFormatter();
		pulseFormat.setPointLabelFormatter( new PointLabelFormatter() );
		pulseFormat.configure( getApplicationContext(), R.xml.line_point_formatter_blue );
		pressurePulsePlot.addSeries( pulseSeries, pulseFormat );

		pressurePlot.setTicksPerRangeLabel( 3 );
		pressurePlot.getGraphWidget().setDomainLabelOrientation( -45 );

		pressurePulsePlot.setTicksPerRangeLabel( 3 );
		pressurePulsePlot.getGraphWidget().setDomainLabelOrientation( -45 );
	}
}
