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
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android.teatime.databinding.ActivityOrderSummaryBinding;

import java.text.NumberFormat;
import java.util.Locale;

public class OrderSummaryActivity extends AppCompatActivity {

    private Locale mLocale;
    private ActivityOrderSummaryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderSummaryBinding.inflate(getLayoutInflater());
        mLocale = getResources().getConfiguration().getLocales().get(0);
        setContentView(binding.getRoot());
        setSupportActionBar(binding.orderSummaryToolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setTitle(getString(R.string.order_summary_title));

        Intent intent = getIntent();
        String teaName = intent.getStringExtra(OrderActivity.EXTRA_TEA_NAME);
        int price = intent.getIntExtra(OrderActivity.EXTRA_TOTAL_PRICE, 0);
        String size = intent.getStringExtra(OrderActivity.EXTRA_SIZE);
        String milkType = intent.getStringExtra(OrderActivity.EXTRA_MILK_TYPE);
        String sugarType = intent.getStringExtra(OrderActivity.EXTRA_SUGAR_TYPE);
        int quantity = intent.getIntExtra(OrderActivity.EXTRA_QUANTITY, 0);

        displayOrderSummary(teaName, price, size, milkType, sugarType, quantity);
    }

    /**
     * Create summary of the order.
     *
     * @param teaName   type of tea
     * @param quantity  quantity ordered
     * @param price     price of the order
     * @param milkType  type of milk to add
     * @param sugarType amount of sugar to add
     */
    private void displayOrderSummary(String teaName, int price, String size, String milkType,
            String sugarType, int quantity) {

        // Set tea name in order summary
        binding.summaryTeaName.setText(teaName);

        // Set quantity in order summary
        binding.summaryQuantity.setText(String.valueOf(quantity));

        // Set tea size in order summary
        binding.summaryTeaSize.setText(size);

        // Set milk type in order summary
        binding.summaryMilkType.setText(milkType);

        // Set sugar amount in order summary
        binding.summarySugarAmount.setText(sugarType);

        // Set total price in order summary
        String convertPrice = NumberFormat.getCurrencyInstance(mLocale).format(price);
        binding.summaryTotalPrice.setText(convertPrice);

    }

    /**
     * This method is called when the Send Email button is clicked and sends a copy of the order
     * summary to the inputted email address.
     */

    public void sendEmail(View view) {

        String emailMessage = getString(R.string.email_message);

        // Use an intent to launch an email app.
        // Send the order summary in the email body.
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT,
                getString(R.string.order_summary_email_subject));
        intent.putExtra(Intent.EXTRA_TEXT, emailMessage);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);

        }
    }
}
