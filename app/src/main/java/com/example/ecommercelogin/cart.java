package com.example.ecommercelogin;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class cart extends AppCompatActivity {
ScrollView scview ;
int NoOfProducts;
TextView totalammount;
ImageButton tickB;
ImageButton crossB;
 double Pricesum;
  static String Purchase_data="";
    Item_details obj=new Item_details();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

       // scview=(ScrollView)findViewById(R.id.Scview);
totalammount=(TextView)findViewById(R.id.totalamount);
        tickB=(ImageButton) findViewById(R.id.tickB);
        crossB=(ImageButton) findViewById(R.id.crossB);
        totalammount=(TextView)findViewById(R.id.totalamount);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linear_layout);

        //actionbar
        ActionBar actionBar= getSupportActionBar();
        actionBar.setTitle("                   Cart");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF97BF4A")));
//        Item_details obj=new Item_details();
        NoOfProducts=obj.Product_Price_List.size();
        for (int i = 0; i < NoOfProducts; i++) {
            Pricesum+=obj.Product_Tprice_List.get(i);
        }

        if(NoOfProducts>0) {
            crossB.setVisibility(View.VISIBLE);
            tickB.setVisibility(View.VISIBLE);
            totalammount.setText("PKR: "+Pricesum);
//        Log.d("price",obj.Product_Name_List.get(1));
            for (int i = 0; i < NoOfProducts; i++) {
//            // create a new textview
                TextView rowTextView = new TextView(this);
//
//            // set some properties of rowTextView or something
                rowTextView.setTextSize(15);
                rowTextView.setText(obj.Product_Name_List.get(i)+"          " +obj.Product_Quantity_List.get(i)+ "                " + obj.Product_Price_List.get(i) + "            "+obj.Product_Tprice_List.get(i)+"\n");
            Purchase_data+=(obj.Product_Name_List.get(i)+"     " +obj.Product_Quantity_List.get(i)+ "     " + obj.Product_Price_List.get(i) + "    "+obj.Product_Tprice_List.get(i)+"\n");
//            rowTextView.setText("hahahhhahaa");
//            // add the textview to the linearlayout
                linearLayout.addView(rowTextView);
//
//
            }
        }else{
            totalammount.setText("Cart is empty");
            crossB.setVisibility(View.GONE);
            tickB.setVisibility(View.GONE);
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    public void cross(View view) {

        //dialog
        AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                cart.this,R.style.Theme_AppCompat_Light_Dialog_Alert);

// Setting Dialog Title
        alertDialog2.setTitle("     Confirmation");

// Setting Dialog Message
        alertDialog2.setMessage(Html.fromHtml("Are you sure you want to Cancel the Order?"));

// Setting Icon to Dialog
        alertDialog2.setIcon(R.drawable.cart_add);

// Setting Positive "Yes" Btn
        alertDialog2.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //adding to arrayList
                        obj.Product_Name_List.clear();
                        obj.Product_Price_List.clear();
                        obj.Product_Quantity_List.clear();
                        obj.Product_Tprice_List.clear();
                        Purchase_data="";
                        finish();
                        startActivity(getIntent());
                        // Write your code here to execute after dialog
                        Toast.makeText(getApplicationContext(),
                                "Order cancelled", Toast.LENGTH_SHORT)
                                .show();
                    }
                });

// Setting Negative "NO" Btn
        alertDialog2.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog
                        dialog.cancel();
                    }
                });

// Showing Alert Dialog
        alertDialog2.show();
    }

    public void checkout(View view) {
        Intent intent = new Intent(getApplicationContext(), Check_Out.class);
       // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        this.startActivity(intent);
        finish();
        
    }
}

