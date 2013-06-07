package org.skylight1.neny.android;

import org.skylight1.neny.android.database.RestaurantDatabase;
import org.skylight1.neny.android.database.model.Address;
import org.skylight1.neny.android.database.model.Cuisine;
import org.skylight1.neny.android.database.model.Restaurant;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ShowRestaurantDetailActivity extends Activity implements
		OnClickListener {

	public final static String CITY = "New York,NY";
	private Restaurant selectedRestaurant = null;
	private final String TAG = ShowRestaurantDetailActivity.class
			.getSimpleName();

	@Override
	protected void onCreate(Bundle aSavedInstanceState) {
		super.onCreate(aSavedInstanceState);

		setContentView(R.layout.restaurant_detail);

		final Bundle extras = getIntent().getExtras();
		if (extras != null) {
			String camis = extras.getString("camis");
			if (camis != null) {
				final Restaurant restaurant = new RestaurantDatabase(this)
						.getRestaurantByCamis(camis);
				if (restaurant != null) {
					selectedRestaurant = restaurant;
					showRestaurantDetail(restaurant);
				}
			}
		}
	}

	private void showRestaurantDetail(Restaurant restaurant) {
		final TextView tvRestaurantName = (TextView) findViewById(R.id.tv_detail_restaurant_name);
		tvRestaurantName.setText(restaurant.getDoingBusinessAs());

		final TextView tvRestaurantPhone = (TextView) findViewById(R.id.tv_detail_restaurant_phone);
		tvRestaurantPhone.setText(restaurant.getPhone());
		tvRestaurantPhone.setOnClickListener(this);
		tvRestaurantPhone.setTag(restaurant.getPhone());
		tvRestaurantPhone.setPaintFlags(tvRestaurantPhone.getPaintFlags()
				| Paint.UNDERLINE_TEXT_FLAG);

		final Address address = restaurant.getAddress();
		final TextView tvStreet = (TextView) findViewById(R.id.tv_detail_restaurant_street);
		final String street = address.getBuilding() + " " + address.getStreet();
		tvStreet.setText(street + "\n" + address.getZipCode());
		tvStreet.setPaintFlags(tvStreet.getPaintFlags()
				| Paint.UNDERLINE_TEXT_FLAG);
		tvStreet.setOnClickListener(this);
		final ImageView cuisineImageView = (ImageView) findViewById(R.id.cuisine_image);
		final Cuisine cuisine = Cuisine
				.findCuisineByMajorCuisineLabel(restaurant.getMajorCuisine());
		cuisineImageView.setImageResource(cuisine.getActiveImageResourceId());
		final TextView cuisineNameView = (TextView) findViewById(R.id.cuisine_image_text);
		cuisineNameView.setText(cuisine.getLabel());
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_detail_restaurant_street:
			Address addRess = selectedRestaurant.getAddress();
			final String queryString = addRess.getBuilding() + " "
					+ addRess.getStreet() + " " + CITY + " "
					+ addRess.getZipCode();
			Log.d(TAG, queryString);
			Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
					Uri.parse("http://maps.google.com/maps?q=" + queryString));
			startActivity(intent);
			break;
		case R.id.tv_detail_restaurant_phone:
			final Intent callIntent = new Intent(Intent.ACTION_CALL);
			callIntent.setData(Uri.parse("tel:" + v.getTag()));
			startActivity(callIntent);
			break;
		}
	}
}
