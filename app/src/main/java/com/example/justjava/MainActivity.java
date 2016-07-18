package com.example.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
   int quantity = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {


        EditText namefield = (EditText)findViewById(R.id.name_field);
        String value = namefield.getText().toString();

        CheckBox wippeCreamCheckBox = (CheckBox)findViewById(R.id.Wipped_cream_checkbox);
        boolean hasWippedCream = wippeCreamCheckBox.isChecked();

        CheckBox chochlateBox = (CheckBox)findViewById(R.id.Chocolate_checkbox);
        boolean hasChocolate =  chochlateBox.isChecked();

        int price = calculatePrice(hasWippedCream, hasChocolate);

        String priceMessage = creatOrderSummary(price, hasWippedCream, hasChocolate, value);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + namefield);
        intent.putExtra(Intent.EXTRA_TEXT,  priceMessage);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        displayMessage(priceMessage);

    }

    /**
     * Calculates the price of the order.
     *
     */
    private int calculatePrice(boolean addwippedcream, boolean addchocolate) {
        int base = 5;
        if (addwippedcream)
             base += 1;
        if (addchocolate)
             base += 2;

        return  quantity * base;
    }

    /**
    * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (quantity ==100){
            Toast.makeText(this, "You cannot have more than 100 coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity+1;
        displayQuantity(quantity);
    }
    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (quantity ==1){
            Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity-1;
        displayQuantity(quantity);
    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
    /**
     * This method displays the given price on the screen.
     */
    private void displayPrice(int number) {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(message);
    }

    private String creatOrderSummary(int price, boolean addCream, boolean addChocolate, String name)
    {
        String priceMessage= "Name: "+ name;
        priceMessage += "\nAdd cream? " + addCream;
        priceMessage += "\nAdd cream? " + addChocolate;
        priceMessage += "\nQuantity: " + quantity;
        priceMessage += "\nTotal: $" + price;
        priceMessage += "\nThank you!";
        return priceMessage;
    }
}