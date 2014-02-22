/*
 * Copyright (C) 2013 Andreas Stuetz <andreas.stuetz@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.aujur.ebookreader.reading.options;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import com.aujur.ebookreader.activity.ReadingFragment;

public class IndexFragment extends ListFragment {

	private IndexArrayAdapter mAdapter;

	private static final Logger LOG = LoggerFactory.getLogger("IndexFragment");

	public static IndexFragment newInstance() {
		IndexFragment f = new IndexFragment();
		Bundle b = new Bundle();
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		super.onActivityCreated(savedInstanceState);

		// Create an empty adapter we will use to display the loaded data.
		mAdapter = new IndexArrayAdapter(getActivity());
		setListAdapter(mAdapter);

		// Start out with a progress indicator.
		// setListShown(false);
		mAdapter.setData(ReadingFragment.getBookViewWraper().getBookView()
				.getTableOfContents());

	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {

		ReadingFragment
				.getBookViewWraper()
				.getBookView()
				.navigateTo(
						ReadingFragment.getBookViewWraper().getBookView()
								.getTableOfContents().get(position).getHref());

		getActivity().finish();

	}

}