package de.flyingfeet.healthyapp;

import java.text.DecimalFormat;
import java.util.ArrayList;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.os.Bundle;

import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.PointLabelFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.androidplot.xy.XYStepMode;

import de.flyingfeet.healthyapp.models.Sugar;
import de.flyingfeet.healthyapp.util.DataStorage;

public class SugarDiagram extends RoboActivity
{
	@InjectView( R.id.diagram_sugar )
	private XYPlot sugarPlot;

	@Override
	public void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		setContentView( R.layout.sugar_diagram );

		Sugar[] sugars = DataStorage.getInstance().getSugars();
		ArrayList<Number> sugarValues = new ArrayList<Number>();
		for ( int i = 0; i < sugars.length; i++ )
		{
			String sugar = sugars[i].getSugar();
			sugarValues.add( Integer.parseInt( sugar ) );
		}

		XYSeries sugarSeries = new SimpleXYSeries( sugarValues, SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Zucker" );

		LineAndPointFormatter sugarFormat = new LineAndPointFormatter();
		sugarFormat.setPointLabelFormatter( new PointLabelFormatter() );
		sugarFormat.configure( getApplicationContext(), R.xml.line_point_formatter_blue );
		sugarPlot.addSeries( sugarSeries, sugarFormat );

		sugarPlot.setTicksPerRangeLabel( 2 );
		sugarPlot.setDomainStep( XYStepMode.INCREMENT_BY_VAL, 1 );
		sugarPlot.setDomainValueFormat( new DecimalFormat( "#" ) );
	}
}
