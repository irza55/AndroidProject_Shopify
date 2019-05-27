package com.example.ecommercelogin;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommercelogin.ui.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Check_Out extends AppCompatActivity {
TextView Quantity,Totalammount,email;
EditText Name,phone;
Button submit;
int Quan;
double Pricesum;
String PurchaseData,userid;
String Email="";
    Item_details obj1=new Item_details();

DatabaseReference reference;
sale sale;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check__out);
//ActionBar =================================================
        ActionBar actionBar= getSupportActionBar();
        actionBar.setTitle("               Checkout");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF97BF4A")));
//===========================================================
        Quantity=(TextView)findViewById(R.id.ProductsQuantity);
        Totalammount=(TextView)findViewById(R.id.TotalAm);
        email=(TextView)findViewById(R.id.Email);
        Name=(EditText) findViewById(R.id.name);
        phone=(EditText) findViewById(R.id.PhoneNo);
        submit = (Button) findViewById(R.id.submit);
//============================================================

       int NoOfProducts=obj1.Product_Quantity_List.size();
       //=====================================================
        for (int i = 0; i < NoOfProducts; i++) {
            Quan+=obj1.Product_Quantity_List.get(i);
            Pricesum+=obj1.Product_Tprice_List.get(i);
        }
//=============================================================
Quantity.setText(Html.fromHtml("Total Products Quantity: "+"<b>"+Quan+"</b>"));
        Totalammount.setText(Html.fromHtml("Total Ammount: "+"<b>"+Pricesum+"</b>"));

        LoginActivity log=new LoginActivity();
       // Email=log.personEmail;
        //getting userid
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userid=user.getUid();
        Email=user.getEmail();
//set email====================================================

        email.setText(Email);
        Name.setText(log.personName);

//firebase refrence=============================================
        sale=new sale();
        reference= FirebaseDatabase.getInstance().getReference().child("Sales");
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = Name.getText().toString();
                final String phoneNo = phone.getText().toString();

                if (TextUtils.isEmpty(name)) {
                    Name.setError("Enter Name");
                    return;
                }

                if (TextUtils.isEmpty(phoneNo)) {
                    phone.setError("Enter Password");
                    return;
                }
                   cart obj=new cart();
                PurchaseData=obj.Purchase_data;
                //Firebase newRef =  userRef.push();
                sale.setName(name);
                sale.setPhNo(phoneNo);
                sale.setEmail(Email);
                sale.setSaleData(PurchaseData);
                reference.child(userid).push().setValue((sale));
                Toast.makeText(Check_Out.this, "Order submitted Sucessfully ", Toast.LENGTH_SHORT).show();
                obj1.Product_Quantity_List.clear();
                obj1.Product_Price_List.clear();
                obj1.Product_Name_List.clear();
                obj1.Product_Tprice_List.clear();;
//moving to dashboard==============================
                Intent intent = new Intent(Check_Out.this,dashboard.class);
//              Log.d("product",Product_Name_List.get(0).toString());
                // Title.setText(Product_Price_List.get(0).toString());
                startActivity(intent);

            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_single_icon,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_cart:
                Intent intent = new Intent(Check_Out.this,cart.class);
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
}
