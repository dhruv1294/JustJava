package com.example.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method increments the quantity by 1
     */
    public void increaseQuantity(View view) {
        if (quantity < 100)
            quantity = quantity + 1;
        displayQuantity(quantity);
    }

    /**
     * This method decrements the quantity by 1
     */
    public void decreaseQuantity(View view) {
        if (quantity > 1)
            quantity = quantity - 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        boolean hasChocolate = chocolateCheckBox.isChecked();
        EditText nameEditView = (EditText) findViewById(R.id.name_edit_view);
        String name = nameEditView.getText().toString();
        String orderSummary = createOrderSummary(name, hasWhippedCream, hasChocolate);
        displayMessage(orderSummary);
        composeEmail(orderSummary);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

    /**
     * This method calculates the price of the coffee
     */
    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate){
        int priceOfOne = 5;
        if (hasWhippedCream){
            priceOfOne += 1;
        }
        if (hasChocolate){
            priceOfOne += 2;
        }
        return priceOfOne * quantity;
    }

    /**
     * This method creates a summary of the order
     */
    private String createOrderSummary(String name, boolean hasWhippedCream, boolean hasChocolate){
        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String summary = "Name: " + name +
                "\nAdd Whipped Cream: " + hasWhippedCream +
                "\nAdd Chocolate: " + hasChocolate +
                "\nQuantity: " + quantity +
                "\nTotal: $" + price +
                "\nThank you!";
        return summary;
    }

    public void composeEmail(String body) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] {"pradeep.deep7@gmail.com"});
        intent.putExtra(Intent.EXTRA_TEXT, body);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java Order");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
