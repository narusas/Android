package net.narusas.android.network;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class NetworkAvailabilityChecker {
	static NetworkAvailabilityChecker instance;
	private final Context context;

	public NetworkAvailabilityChecker(Context applicationContext) {
		this.context = applicationContext;
	}

	public static void init(Context applicationContext) {
		instance = new NetworkAvailabilityChecker(applicationContext);
	}

	public static NetworkAvailabilityChecker instance() {
		return instance;
	}

	public boolean checkNetworkAvailable() {
		Log.i("HERE", "checkNetworkAvailable");
		return isAnyNetWorkAvailable();
	}

	public boolean isWifiAvailable() {
		Log.i("HERE", "isWifiAvailable");
		return isNetWorkAvailable(ConnectivityManager.TYPE_WIFI);
	}

	public boolean is3GAvailable() {
		Log.i("HERE", "is3GAvailable");
		return isNetWorkAvailable(ConnectivityManager.TYPE_MOBILE);
	}

	public boolean isAnyNetWorkAvailable() {
		return (isWifiAvailable() || is3GAvailable());
	}

	private boolean isNetWorkAvailable(int networkType) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivityManager == null) {
			Log.i("HERE", "No ConnectivityManager");
			return false;
		}

		NetworkInfo info = connectivityManager.getNetworkInfo(networkType);
		boolean isAvailable =  (info.isAvailable() && info.isConnected());
		Log.i("HERE", "Network Type:"+networkType+" Status:"+isAvailable);
		return isAvailable;
	}

	public boolean isGPSAvailable() {
		LocationManager locManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		if (!locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			return false;
		}
		return true;
	}

}
