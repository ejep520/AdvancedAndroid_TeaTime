package com.example.android.teatime;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.android.teatime.IdlingResource.SimpleIdlingResource;
import com.example.android.teatime.model.Tea;

import java.util.ArrayList;

public class ImageDownloader {

    private static final long DELAY_MILLS = 3000;

    //Create an ArrayList fo mTeas.
    private final static ArrayList<Tea> mTeas = new ArrayList<>();

    interface DelayerCallback {
        void onDone(ArrayList<Tea> teas);
    }

    static void downloadImage(Context context, final DelayerCallback callback,
                              @Nullable final SimpleIdlingResource idlingResource) {
        if (idlingResource != null) {
            idlingResource.setIdleState(false);
        }

        String text = context.getString(R.string.loading_msg);
        int duration = Toast.LENGTH_LONG;
        Toast.makeText(context, text, duration).show();

        mTeas.add(new Tea(context.getString(R.string.black_tea_name), R.drawable.black_tea));
        mTeas.add(new Tea(context.getString(R.string.green_tea_name), R.drawable.green_tea));
        mTeas.add(new Tea(context.getString(R.string.white_tea_name), R.drawable.white_tea));
        mTeas.add(new Tea(context.getString(R.string.oolong_tea_name), R.drawable.oolong_tea));
        mTeas.add(new Tea(context.getString(R.string.honey_lemon_tea_name),
                R.drawable.honey_lemon_tea));
        mTeas.add(new Tea(context.getString(R.string.chamomile_tea_name),
                R.drawable.chamomile_tea));

        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(() -> {
            if (callback != null) {
                callback.onDone(mTeas);
                if (idlingResource != null) {
                    idlingResource.setIdleState(true);
                }
            }
        }, DELAY_MILLS);
    }
}
