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

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;

import com.example.android.teatime.model.Tea;

import java.util.ArrayList;

/**
 * TeaMenuAdapter is backed by an ArrayList of {@link Tea} objects which populate
 * the GridView in MenuActivity
 */

public class TeaMenuAdapter extends ArrayAdapter<Tea> {

    @LayoutRes
    private final int layoutResourceId;
    private final Context mContext;
    private ArrayList<Tea> data;

    public TeaMenuAdapter(Context context, @LayoutRes int layoutResourceId, ArrayList data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.mContext = context;
        this.data = data;
    }

    static class ViewHolder {
        TextView imageTitle;
        ImageView image;
    }

    @Override
    // Create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        Tea currentTea = getItem(position);

        if (convertView == null) {
            // If it's not recycled, initialize some attributes
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.imageTitle = convertView.findViewById(R.id.tea_grid_name);
            holder.image = convertView.findViewById(R.id.image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();;
        }

        holder.imageTitle.setText(currentTea.getTeaName());
        holder.image.setImageResource(currentTea.getImageResourceId());
        return convertView;
    }
}
