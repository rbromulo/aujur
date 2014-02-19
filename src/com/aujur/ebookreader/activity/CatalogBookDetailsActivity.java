/*
 * Copyright (C) 2013 Alex Kuiper
 * 
 * This file is part of PageTurner
 *
 * PageTurner is free software: you can redistribute it and/or modify
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
 * along with PageTurner.  If not, see <http://www.gnu.org/licenses/>.*
 */
package com.aujur.ebookreader.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import com.actionbarsherlock.view.Window;
import com.aujur.ebookreader.Configuration;
import com.aujur.ebookreader.PageTurner;
import com.aujur.ebookreader.R;
import com.aujur.ebookreader.catalog.BookDetailsFragment;
import com.aujur.ebookreader.catalog.CatalogParent;
import com.github.rtyley.android.sherlock.roboguice.activity.RoboSherlockFragmentActivity;
import net.nightwhistler.nucular.atom.Feed;
import roboguice.RoboGuice;
import roboguice.inject.InjectFragment;

import java.io.Serializable;

public class CatalogBookDetailsActivity extends PageTurnerActivity implements CatalogParent {

    private BookDetailsFragment detailsFragment;

    @Override
    protected void onCreatePageTurnerActivity(Bundle savedInstanceState) {
        if (getResources().getConfiguration().orientation
                == android.content.res.Configuration.ORIENTATION_LANDSCAPE) {
            // If the screen is now in landscape mode, we can show the
            // dialog in-line with the list so we don't need this activity.
            finish();
            return;
        }


        detailsFragment = (BookDetailsFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_bookdetails);

        Intent intent = getIntent();
        Uri uri = intent.getData();

        if (uri != null && uri.toString().startsWith("epub://")) {
            String downloadUrl = uri.toString().replace("epub://", "http://");
            detailsFragment.startDownload(false, downloadUrl);
        } else {
            Serializable ser = intent.getSerializableExtra("fakeFeed");
            Feed fakeFeed = (Feed) ser;

            if ( fakeFeed != null ) {
                detailsFragment.setNewFeed(fakeFeed, null);
            }
        }
    }

    @Override
    protected int getMainLayoutResource() {
        return R.layout.activity_catalog_details;
    }

    @Override
    public void loadFakeFeed(Feed fakeFeed) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onFeedReplaced(Feed feed) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void loadFeedFromUrl(String url) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

}
