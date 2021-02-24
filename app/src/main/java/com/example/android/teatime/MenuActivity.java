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

    private Intent mTeaIntent;
    private ActivityMenuBinding binding;

    public final static String EXTRA_TEA_NAME = "com.example.android.teatime.EXTRA_TEA_NAME";

    @Nullable
    private SimpleIdlingResource mIdlingResource;

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
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(getString(R.string.menu_title));

        getIdlingResource();
    }

    @Override
    protected void onStart() {
        super.onStart();
        ImageDownloader.downloadImage(this, MenuActivity.this, mIdlingResource);
    }

    @Override
    public void onDone(ArrayList<Tea> teas) {
        TeaMenuAdapter adapter = new TeaMenuAdapter(this, R.layout.grid_item_layout, teas);
        binding.teaGridView.setAdapter(adapter);
        binding.teaGridView.setOnItemClickListener((adapterView, view, position, id) -> {
            Tea tea = (Tea) adapterView.getItemAtPosition(position);
            mTeaIntent = new Intent(MenuActivity.this, OrderActivity.class);
            String teaName = tea.getTeaName();
            mTeaIntent.putExtra(EXTRA_TEA_NAME, teaName);
            startActivity(mTeaIntent);
        });
    }
}
