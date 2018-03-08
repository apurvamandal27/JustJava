package com.example.apurva.justjava;

         import android.content.Intent;
         import android.net.Uri;
         import android.os.Bundle;
         import android.support.v7.app.AppCompatActivity;
         import android.util.Log;
         import android.view.View;
         import android.widget.CheckBox;
         import android.widget.EditText;
         import android.widget.TextView;
         import android.widget.Toast;

/*
* This app displays an order form to order coffee.
*/
public class MainActivity extends AppCompatActivity {

  int quantity=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        display(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText getName=findViewById(R.id.name_field);
        String name=getName.getText().toString();

        CheckBox whippedCreamCheckBox=findViewById(R.id.whippedCheckBox);
        CheckBox chocolateCheckBox=findViewById(R.id.chocolate_checkbox);

        boolean hasChocolate=chocolateCheckBox.isChecked();
        boolean hasWhippedCream=whippedCreamCheckBox.isChecked();

        int price=calculatePrice(hasWhippedCream,hasChocolate);
        String priceMessage=createOrderSummary(price,hasWhippedCream,hasChocolate,name);

        //intent
        Intent intent=new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); //only email app should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT,"just java order for"+name);
        intent.putExtra(Intent.EXTRA_TEXT,priceMessage);
        if(intent.resolveActivity(getPackageManager())!=null){
            startActivity(intent);
        }

//       displayPrice(priceMessage);


    }

    public void increment(View view){
        if(quantity==11){
            Toast.makeText(this, "You can't order more then 10 coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity++;
        display(quantity);
    }

    public void decrement(View view){
        if(quantity==1){
            Toast.makeText(this, "You can't order less than 1 coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity--;

        display(quantity);
    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given price on the screen.
     */
//    public void displayPrice(String number){
//        TextView price=(TextView) findViewById(R.id.price_text_view2);
//        price.setText(""+number);
//    }

    private int calculatePrice(boolean addWhippedCream, boolean addChocolate ){
        int basePrice=5;

        if(addWhippedCream){
            basePrice+=1;
        }

        if(addChocolate){
            basePrice+=2;
        }

        return quantity*basePrice;
    }
    private String createOrderSummary(int price,boolean addWhippedCream,boolean addChocolate,String name){
        String priceMessage="Name : "+name;
        priceMessage+="\nAdd Whipped Cream? "+addWhippedCream;
        priceMessage+="\nAdd Chocolate? "+addChocolate;
        priceMessage+="\nQuantity: "+quantity;
        priceMessage+="\nTotal: $"+price;
        priceMessage+="\nThank you";
        return priceMessage;
    }

}
