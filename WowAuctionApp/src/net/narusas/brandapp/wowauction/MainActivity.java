package net.narusas.brandapp.wowauction;

import net.narusas.android.auction.AuctionMainActivity;
import net.narusas.brandapp.R;

public class MainActivity extends AuctionMainActivity {
	@Override
	protected int getActivityMainLayout() {
		return R.layout.activity_main;
	}

	@Override
	protected int getProgressBarId() {
		return R.id.progressBar1;
	}

	@Override
	protected int getWebViewId() {
		return R.id.webView1;
	}

	@Override
	protected int getBaseUrl() {
		return R.string.base_url;
	}
}