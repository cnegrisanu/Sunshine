/*
 * Copyright (C) 2014 The Android Open Source Project
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

package com.example.android.sunshine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.ShareActionProvider;
import android.widget.TextView;

public class DetailActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new DetailFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this,SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class DetailFragment extends Fragment {

        private static final String LOG_TAG = DetailFragment.class.getSimpleName();

        private static final String FORECAST_SHARE_HASHTAG = " #SunshineApp";
        private String mForecastStr;


        /**
         * Initialize the contents of the Activity's standard options menu.  You
         * should place your menu items in to <var>menu</var>.  For this method
         * to be called, you must have first called {@link #setHasOptionsMenu}.  See
         * {@link Activity#onCreateOptionsMenu(Menu) Activity.onCreateOptionsMenu}
         * for more information.
         *
         * @param menu     The options menu in which you place your items.
         * @param inflater
         * @see #setHasOptionsMenu
         * @see #onPrepareOptionsMenu
         * @see #onOptionsItemSelected
         */
        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            inflater.inflate(R.menu.detailfragment, menu);


            MenuItem menuItem = menu.findItem(R.id.action_share);

            ShareActionProvider mShareActionProvider =
                    (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);

            if (mShareActionProvider != null) {
                mShareActionProvider.setShareIntent(createShareForecastIntent());
            } else {
                Log.d(LOG_TAG,"Share action provider is null?");
            }

        }

        public DetailFragment() {
            setHasOptionsMenu(true);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            Intent intent = getActivity().getIntent();

            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
            TextView weather = (TextView) rootView.findViewById(R.id.weather_text);

            if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
                mForecastStr = intent.getStringExtra(Intent.EXTRA_TEXT);
                weather.setText(mForecastStr);
            }


            return rootView;
        }

        private Intent createShareForecastIntent() {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT,mForecastStr + FORECAST_SHARE_HASHTAG);
            return shareIntent;
        }
    }
}