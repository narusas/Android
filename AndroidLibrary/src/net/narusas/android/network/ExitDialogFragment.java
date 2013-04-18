package net.narusas.android.network;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class ExitDialogFragment extends DialogFragment {

	@Override
	public Dialog onCreateDialog(Bundle bundle) {
		return new AlertDialog.Builder(getActivity()).setMessage("프로그램을 종료 하시겠습니까?").setCancelable(true)
				.setPositiveButton("종료", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						getActivity().finish();
					}
				}).setNegativeButton("취소", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				}).create();
	}
}
