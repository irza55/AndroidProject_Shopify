package com.example.ecommercelogin;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Vibrator;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Item_details extends AppCompatActivity {
int Quantity=1;
ImageView imageview;
Button addQuantity, minusQuantity;
TextView showQuantity,Title,Description,Price;
String description,title,price,image;
    private ProgressBar progressBar;
   static ArrayList<String> Product_Name_List = new ArrayList<String>();
   static ArrayList<Integer> Product_Price_List= new ArrayList<Integer>();
    static ArrayList<Integer> Product_Quantity_List= new ArrayList<Integer>();
    static ArrayList<Double> Product_Tprice_List= new ArrayList<Double>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);


        addQuantity = (Button) findViewById(R.id.addQuantity);
        minusQuantity = (Button) findViewById(R.id.minusQuantity);
        showQuantity = (TextView) findViewById(R.id.showQuantity);
        Title = (TextView) findViewById(R.id.DItem_title);
        imageview=(ImageView)findViewById(R.id.DItemImage);

        Description = (TextView) findViewById(R.id.DItem_desc);
        Price = (TextView) findViewById(R.id.DItemprice);
        //getting intents
        title=getIntent().getStringExtra("Title");
        description=getIntent().getStringExtra("Description");
        price=getIntent().getStringExtra("Price");
        image=getIntent().getStringExtra("Image");
        //progressbar
        progressBar = (ProgressBar) findViewById(R.id.load);
        progressBar.setVisibility(View.VISIBLE);

        //set information
        Title.setText(title);
        Description.setText(description);
        Price.setText("PKR: "+price);
        //imageview.setImagePicasso.get().load(image);
        Picasso.get().load(image).into(imageview, new Callback() {
            @Override
            public void onSuccess() {

                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(Exception e) {
                Title.setText("Connection Error");
                progressBar.setVisibility(View.GONE);
            }
        });


       // ActionBar bar = getActionBar();
        ActionBar actionBar= getSupportActionBar();
        actionBar.setTitle("Product Detail");
        actionBar.setDisplayHomeAsUpEnabled(true);
       actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF97BF4A")));



    }
    //ActionBAR work
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_cart:
                Intent intent = new Intent(Item_details.this,cart.class);
//              Log.d("product",Product_Name_List.get(0).toString());
               // Title.setText(Product_Price_List.get(0).toString());
                startActivity(intent);
                finish();
                return true;}
        return super.onOptionsItemSelected(item);
    }

    @Override
   public boolean onSupportNavigateUp() {
       onBackPressed();
      return true;
    }
    public void addQuantity(View v){
        Quantity =Quantity+1;
        showQuantity.setText(String.valueOf(Quantity));
    }
    public void minusQuantity(View v){
        if(Quantity==1){
            Toast.makeText(getApplicationContext(), "Minimun 1 Product",
                    Toast.LENGTH_SHORT).show();
            Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            vibe.vibrate(80);
        }
        else {
            Quantity = Quantity - 1;
            showQuantity.setText(String.valueOf(Quantity));
        }
    }

    public void buy(View view) {


        //dialog
        AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                Item_details.this,R.style.Theme_AppCompat_Light_Dialog_Alert);

// Setting Dialog Title
        alertDialog2.setTitle("     Confirmation");

// Setting Dialog Message
        alertDialog2.setMessage(Html.fromHtml("Are you sure you want add "+"<b>"+title+"</b>"+ " to cart?"));

// Setting Icon to Dialog
        alertDialog2.setIcon(R.drawable.cart_add);

// Setting Positive "Yes" Btn
        alertDialog2.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //adding to arrayList
                        Product_Name_List.add(title);
                        int Price_Int = Integer.parseInt(price);
                        Product_Price_List.add(Price_Int);
                        Product_Quantity_List.add(Quantity);
                        double Tprice=Price_Int*Quantity;
                        Product_Tprice_List.add(Tprice);
                        // Write your code here to execute after dialog
                        Toast.makeText(getApplicationContext(),
                                "Product added", Toast.LENGTH_SHORT)
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_single_icon,menu);
        return true;
    }
}
