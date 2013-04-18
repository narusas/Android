package net.narusas.android.auction;

import net.narusas.android.network.ExitDialogFragment;
import net.narusas.android.network.NetworkAvailabilityChecker;
import net.narusas.android.network.NetworkErrorDialogFragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public abstract class AuctionMainActivity extends FragmentActivity {
	public static final int EXIT_DIALOG = 104;
	protected ProgressBar progressBar;
	protected WebView webview;
	protected Handler handler;
	protected boolean isFirstPageVisited = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(getActivityMainLayout());
		NetworkAvailabilityChecker.init(getApplicationContext());
		handler = new Handler();
		progressBar = (ProgressBar) findViewById(getProgressBarId());
		webview = (WebView) findViewById(getWebViewId());
		defaultSetting(webview.getSettings());
		CookieSyncManager.createInstance(this);

	}

	protected abstract int getActivityMainLayout();

	protected abstract int getProgressBarId();

	protected abstract int getWebViewId();

	@Override
	protected void onResume() {
		super.onResume();
		Log.i("HERE", "onResume");
		checkPreconditions();
		CookieSyncManager.getInstance().startSync();

		goWebpage();
	}

	@Override
	protected void onPause() {
		super.onPause();
		CookieSyncManager.getInstance().stopSync();
	}

	private void checkPreconditions() {
		if (NetworkAvailabilityChecker.instance().checkNetworkAvailable() == false) {
			new NetworkErrorDialogFragment().show(getSupportFragmentManager(), "network_error");
		}
	}

	// ������ �⺻����
	private void defaultSetting(WebSettings settings) {
		settings.setJavaScriptEnabled(true);// �ڹٽ�ũ��Ʈ ��밡��
		webview.addJavascriptInterface(new JavascriptBridge(), "ANDROID");
		webview.setHapticFeedbackEnabled(true);
		// webview.setHorizontalScrollBarEnabled(false);
		// webview.setVerticalScrollBarEnabled(false);
		webview.setFocusable(true);
		webview.setFocusableInTouchMode(true);
		settings.setSupportZoom(true);// �� ���
		settings.setBuiltInZoomControls(true);// �� ���
		settings.setBlockNetworkImage(false);
		settings.setLoadsImagesAutomatically(true);
		settings.setUseWideViewPort(true);
		settings.setCacheMode(WebSettings.LOAD_NORMAL);

		// Ŭ���̾�Ʈ ����
		webview.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int progress) {
				if (progress < 100) {
					progressBar.setVisibility(ProgressBar.VISIBLE);
				} else if (progress == 100) {
					progressBar.setVisibility(ProgressBar.INVISIBLE);
				}
				progressBar.setProgress(progress);
			}

		});
		webview.setWebViewClient(new WebViewClient() {
			@Override
			public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
				webview.loadUrl(getResources().getString(getBaseUrl()));
			}

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				Log.i("HERE", "URL:" + url);
				if (url.startsWith("tel:")) {
					Intent intent = new Intent(Intent.ACTION_CALL);
					intent.setData(Uri.parse(url));
					startActivity(intent);
				} else {
					view.loadUrl(url);
				}
				return true;
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				CookieSyncManager.getInstance().sync();
			}

		});
	}

	protected abstract int getBaseUrl();

	/**
	 * ��ġ�� ��ư �Է� ó��
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if ((keyCode == KeyEvent.KEYCODE_BACK)) { // && webview.canGoBack()
			Log.i("HERE", "Back Requested");
			new ExitDialogFragment().show(getSupportFragmentManager(), "exit_confirm");
			// showDialog(EXIT_DIALOG);
			// webview.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void goWebpage() {
		if (isFirstPageVisited) {
			return;
		}

		//
		webview.loadUrl(getResources().getString(getBaseUrl()));
		isFirstPageVisited = true;
	}

	private class JavascriptBridge {

	}
}
