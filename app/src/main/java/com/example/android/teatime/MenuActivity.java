/*
* Copyright (C) 2017 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*  	http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.example.android.teatime;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.test.espresso.IdlingResource;

import com.example.android.teatime.IdlingResource.SimpleIdlingResource;
import com.example.android.teatime.databinding.ActivityMenuBinding;
import com.example.android.teatime.model.Tea;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity implements ImageDownloader.DelayerCallback {

    Intent mTeaIntent;
    private ActivityMenuBinding binding;

    public final static String EXTRA_TEA_NAME = "com.example.android.teatime.EXTRA_TEA_NAME";

    // The Idling Resource which will be null in production.
    @Nullable
    private SimpleIdlingResource mIdlingResource;

    /**
     * Only called from test, creates and returns a new {@link SimpleIdlingResource}.
     */
    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.menuToolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setTitle(getString(R.string.menu_title));

        // Get the IdlingResource instance
        getIdlingResource();
    }

    /**
     * We call ImageDownloader.downloadImage from onStart or onResume instead of in onCreate
     * to ensure there is enough time to register IdlingResource if the download is done
     * too early (i.e. in onCreate)
     */
    @Override
    protected void onStart() {
        super.onStart();
        ImageDownloader.downloadImage(this, MenuActivity.this, mIdlingResource);
    }

    /**
     * When the thread in {@link ImageDownloader} is finished, it will return an ArrayList of Tea
     * objects via the callback's onDone().
     */
    @Override
    public void onDone(ArrayList<Tea> teas) {

        //TextView testing =findViewById(R.id.textView);
        //binding.textView.setText("Changed");

        // Create a {@link TeaAdapter}, whose data source is a list of {@link Tea}s.
        // The adapter know how to create grid items for each item in the list.
        TeaMenuAdapter adapter = new TeaMenuAdapter(this, R.layout.grid_item_layout, teas);
        binding.teaGridView.setAdapter(adapter);

        // Set a click listener on that View
        binding.teaGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Tea item = (Tea) adapterView.getItemAtPosition(position);
                // Set the intent to open the {@link OrderActivity}
                mTeaIntent = new Intent(MenuActivity.this, OrderActivity.class);
                String teaName = item.getTeaName();
                mTeaIntent.putExtra(EXTRA_TEA_NAME, teaName);
                startActivity(mTeaIntent);
            }
        });
    }
}