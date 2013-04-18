package net.narusas.android.network;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.DialogFragment;

public class NetworkErrorDialogFragment extends DialogFragment {
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		return new AlertDialog.Builder(getActivity()).setTitle("네트워크 오류").setMessage("인터넷에 접근할수 없습니다.").setCancelable(true)
				.setPositiveButton("3G", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
						getActivity().startActivity(intent);
					}
				}).setNeutralButton("WIFI", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
						getActivity().startActivity(intent);
					}
				}).setNegativeButton("종료", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						getActivity().finish();
					}
				}).setOnCancelListener(new DialogInterface.OnCancelListener() {
					@Override
					public void onCancel(DialogInterface dialog) {
						getActivity().finish();
					}
				}).create();
	}
}
