package de.flyingfeet.healthyapp;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectResource;
import roboguice.inject.InjectView;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TimePicker;
import android.widget.Toast;
import de.flyingfeet.healthyapp.datetime.CalenderUtil;
import de.flyingfeet.healthyapp.models.Pressure;
import de.flyingfeet.healthyapp.util.NumberPickerUtil;

public class ModifyPressure extends RoboActivity
{
	@InjectView(R.id.editPressureDate)
	EditText date;

	@InjectView(R.id.editPressureTime)
	EditText time;

	@InjectView(R.id.editPressureSystolic)
	EditText systolic;

	@InjectView(R.id.editPressureDiastolic)
	EditText diastolic;

	@InjectView(R.id.editPressurePulse)
	EditText pulse;

	@InjectResource(R.string.buttonOk)
	String ok;

	@InjectResource(R.string.buttonClose)
	String close;

	SimpleDateFormat formatter = new SimpleDateFormat( "dd.MM.yyyy" );
	CalenderUtil calenderUtil = new CalenderUtil();

	@Override
	public void onCreate( Bundle bundle )
	{
		super.onCreate( bundle );
		setContentView( R.layout.pressure_modify );

		Bundle extras = getIntent().getExtras();
		Pressure pressure = (Pressure) extras.getSerializable( "pressureObject" );

		initDate( pressure );
		initTime( pressure );
		initSystolic( pressure );
		initDiastolic( pressure );
		initPulse( pressure );
	}

	@Override
	public boolean onCreateOptionsMenu( Menu menu )
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate( R.menu.modify_pressure, menu );
		return super.onCreateOptionsMenu( menu );
	}

	@Override
	public boolean onOptionsItemSelected( MenuItem item )
	{
		switch ( item.getItemId() )
		{
		case R.id.action_editPressure:
			Toast.makeText( this, "Bearbeiten kommt noch...", Toast.LENGTH_SHORT ).show();
			return true;
		case R.id.action_removePressure:
			Toast.makeText( this, "Löschen kommt noch...", Toast.LENGTH_SHORT ).show();
			return true;
		default:
			return super.onOptionsItemSelected( item );
		}
	}

	private void initDate( Pressure pressure )
	{
		final DatePickerDialog datePickerDialog = initializeDatePickerDialog();
		date.setText( formatter.format( pressure.getDate() ) );
		date.setOnFocusChangeListener( new View.OnFocusChangeListener()
		{

			@Override
			public void onFocusChange( View v, boolean hasFocus )
			{
				if ( hasFocus )
				{
					datePickerDialog.show();
				}
			}
		} );
		date.setOnClickListener( new View.OnClickListener()
		{

			@Override
			public void onClick( View v )
			{
				datePickerDialog.show();
			}
		} );
	}

	private void initTime( Pressure pressure )
	{
		final TimePickerDialog timePickerDialog = initializeTimePickerDialog();
		time.setText( pressure.getTime() + " Uhr" );
		time.setOnFocusChangeListener( new View.OnFocusChangeListener()
		{

			@Override
			public void onFocusChange( View v, boolean hasFocus )
			{
				if ( hasFocus )
				{
					timePickerDialog.show();
				}
			}
		} );
		time.setOnClickListener( new View.OnClickListener()
		{

			@Override
			public void onClick( View v )
			{
				timePickerDialog.show();
			}
		} );
	}

	private void initSystolic( final Pressure pressure )
	{
		systolic.setText( pressure.getSystolic() );
		systolic.setOnFocusChangeListener( new View.OnFocusChangeListener()
		{

			@Override
			public void onFocusChange( View v, boolean hasFocus )
			{
				if ( hasFocus )
				{
					show( pressure.getSystolic(), (EditText) v );
				}
			}
		} );
		systolic.setOnClickListener( new View.OnClickListener()
		{

			@Override
			public void onClick( View v )
			{
				show( pressure.getSystolic(), (EditText) v );
			}
		} );
	}

	private void initDiastolic( final Pressure pressure )
	{
		diastolic.setText( pressure.getDiastolic() );
		diastolic.setOnFocusChangeListener( new View.OnFocusChangeListener()
		{

			@Override
			public void onFocusChange( View v, boolean hasFocus )
			{
				if ( hasFocus )
				{
					show( pressure.getDiastolic(), (EditText) v );
				}
			}
		} );
		diastolic.setOnClickListener( new View.OnClickListener()
		{

			@Override
			public void onClick( View v )
			{
				show( pressure.getDiastolic(), (EditText) v );
			}
		} );
	}

	private void initPulse( final Pressure pressure )
	{
		pulse.setText( pressure.getPulse() );
		pulse.setOnFocusChangeListener( new View.OnFocusChangeListener()
		{

			@Override
			public void onFocusChange( View v, boolean hasFocus )
			{
				if ( hasFocus )
				{
					show( pressure.getPulse(), (EditText) v );
				}
			}
		} );
		pulse.setOnClickListener( new View.OnClickListener()
		{

			@Override
			public void onClick( View v )
			{
				show( pressure.getPulse(), (EditText) v );
			}
		} );
	}

	private DatePickerDialog initializeDatePickerDialog()
	{
		final DatePickerDialog picker = calenderUtil.getDatePickerDialog( this, null );
		picker.setCancelable( true );
		picker.setCanceledOnTouchOutside( true );
		picker.setButton( DialogInterface.BUTTON_POSITIVE, ok, new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick( DialogInterface dialog, int which )
			{
				Integer day = Integer.valueOf( picker.getDatePicker().getDayOfMonth() );
				Integer month = Integer.valueOf( picker.getDatePicker().getMonth() );
				Integer year = Integer.valueOf( picker.getDatePicker().getYear() );
				Calendar calendar = new GregorianCalendar( year, month, day );
				date.setText( formatter.format( calendar.getTime() ) );
			}
		} );

		picker.setButton( DialogInterface.BUTTON_NEGATIVE, close, new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick( DialogInterface dialog, int which )
			{
			}
		} );

		return picker;
	}

	private TimePickerDialog initializeTimePickerDialog()
	{
		TimePickerDialog picker = calenderUtil.getTimePickerDialog( this, new TimePickerDialog.OnTimeSetListener()
		{

			@Override
			public void onTimeSet( TimePicker view, int hourOfDay, int minute )
			{
				if ( minute < 10 )
				{
					time.setText( hourOfDay + ":0" + minute + " Uhr" );
				}
				else if ( hourOfDay < 10 )
				{
					time.setText( "0" + hourOfDay + ":" + minute + " Uhr" );
				}
				else if ( hourOfDay < 10 && minute < 10 )
				{
					time.setText( "0" + hourOfDay + ":0" + minute + " Uhr" );
				}
				else
				{
					time.setText( hourOfDay + ":" + minute + " Uhr" );
				}
			}
		} );
		picker.setCancelable( true );
		picker.setCanceledOnTouchOutside( true );

		return picker;
	}

	private void show( String value, final EditText editText )
	{
		final Dialog dialog = new Dialog( this );
		dialog.setTitle( "Wählen" );
		dialog.setContentView( R.layout.pressure_number_picker_dialog );
		final NumberPicker numberPicker = (NumberPicker) dialog.findViewById( R.id.numberPicker );
		Button buttonClose = (Button) dialog.findViewById( R.id.buttonClose );
		Button buttonOk = (Button) dialog.findViewById( R.id.buttonOk );
		String[] nums = NumberPickerUtil.loadNumberPickerValues( 180 );

		numberPicker.setMaxValue( nums.length - 1 );
		numberPicker.setMinValue( 0 );
		numberPicker.setWrapSelectorWheel( false );
		numberPicker.setDisplayedValues( nums );
		numberPicker.setValue( Integer.valueOf( value ) );
		numberPicker.setOnValueChangedListener( new NumberPicker.OnValueChangeListener()
		{

			@Override
			public void onValueChange( NumberPicker picker, int oldVal, int newVal )
			{
			}
		} );
		buttonClose.setOnClickListener( new View.OnClickListener()
		{

			@Override
			public void onClick( View v )
			{
				dialog.dismiss();

			}
		} );

		buttonOk.setOnClickListener( new View.OnClickListener()
		{

			@Override
			public void onClick( View v )
			{
				editText.setText( String.valueOf( numberPicker.getValue() ) );
				dialog.dismiss();
			}
		} );
		dialog.show();
	}
}
