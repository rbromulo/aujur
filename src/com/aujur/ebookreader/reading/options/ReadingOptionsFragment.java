/*
 * Copyright (C) 2014 Romulo Bittencourt <rbromulo@gmail.com>
 *
 * 
 * This file is part of AuJur E-Book Reader
 *
 * AuJur E-Book Reader is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * PageTurner is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with AuJur E-Book Reader.  If not, see <http://www.gnu.org/licenses/>.*
 */

package com.aujur.ebookreader.reading.options;

import roboguice.inject.InjectView;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;
import com.aujur.ebookreader.R;
import com.github.rtyley.android.sherlock.roboguice.fragment.RoboSherlockFragment;

public class ReadingOptionsFragment extends RoboSherlockFragment {

	private final Handler handler = new Handler();

	@InjectView(R.id.tabs_options)
	private PagerSlidingTabStrip tabs;

	@InjectView(R.id.pager_options)
	private ViewPager pager;

	private MyPagerAdapter adapter;

	private Drawable oldBackground = null;
	// -10066330
	// -6903239
	private int currentColor = -6903239;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		return inflater
				.inflate(R.layout.fragment_mainoptions, container, false);

	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {

		super.onViewCreated(view, savedInstanceState);

		adapter = new MyPagerAdapter(getActivity().getSupportFragmentManager());

		pager.setAdapter(adapter);

		final int pageMargin = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 1, getResources()
						.getDisplayMetrics());
		pager.setPageMargin(pageMargin);
		pager.setPageMarginDrawable(R.color.medium_gray);

		tabs.setViewPager(pager);

		changeColor(currentColor);

	}

	private Drawable.Callback drawableCallback = new Drawable.Callback() {
		@Override
		public void invalidateDrawable(Drawable who) {
			getActivity().getActionBar().setBackgroundDrawable(who);
		}

		@Override
		public void scheduleDrawable(Drawable who, Runnable what, long when) {
			handler.postAtTime(what, when);
		}

		@Override
		public void unscheduleDrawable(Drawable who, Runnable what) {
			handler.removeCallbacks(what);
		}
	};

	private void changeColor(int newColor) {

		// tabs.setIndicatorColor(newColor);
		tabs.setIndicatorColor(getResources().getColor(
				R.color.green_tab_indicator));
		// tabs.setIndicatorColor(2131034133);

		// change ActionBar color just if an ActionBar is available
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

			Drawable colorDrawable = new ColorDrawable(newColor);
			Drawable bottomDrawable = getResources().getDrawable(
					R.drawable.actionbar_bottom);
			LayerDrawable ld = new LayerDrawable(new Drawable[] {
					colorDrawable, bottomDrawable });

			if (oldBackground == null) {

				if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
					ld.setCallback(drawableCallback);
				} else {
					getActivity().getActionBar().setBackgroundDrawable(ld);
				}

			} else {

				TransitionDrawable td = new TransitionDrawable(new Drawable[] {
						oldBackground, ld });

				// workaround for broken ActionBarContainer drawable handling on
				// pre-API 17 builds
				// https://github.com/android/platform_frameworks_base/commit/a7cc06d82e45918c37429a59b14545c6a57db4e4
				if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
					td.setCallback(drawableCallback);
				} else {
					getActivity().getActionBar().setBackgroundDrawable(td);
				}

				td.startTransition(200);

			}

			oldBackground = ld;

			// http://stackoverflow.com/questions/11002691/actionbar-setbackgrounddrawable-nulling-background-from-thread-handler
			getActivity().getActionBar().setDisplayShowTitleEnabled(false);
			getActivity().getActionBar().setDisplayShowTitleEnabled(true);

		}

		currentColor = newColor;

	}

	public class MyPagerAdapter extends FragmentPagerAdapter {

		private final String[] TITLES = { getString(R.string.overview),
				getString(R.string.toc_label),
				getString(R.string.notes_and_highlights),
				getString(R.string.bookmarks),
				getString(R.string.search_results) };

		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return TITLES[position];
		}

		@Override
		public int getCount() {
			return TITLES.length;
		}

		@Override
		public Fragment getItem(int position) {

			switch (position) {

			case 0:
				return OverviewFragment.newInstance();

			case 1:
				return IndexFragment.newInstance();

			case 2:
				return HighlightsFragment.newInstance();

			case 3:
				return BookmarksFragment.newInstance();

			case 4:
				return SearchFragment.newInstance();

			default:
				return OverviewFragment.newInstance();

			}
		}

	}

	public ViewPager getPager() {
		return this.pager;
	}

}
