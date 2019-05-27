package com.example.ecommercelogin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommercelogin.ui.login.LoginActivity;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ElectronicsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private EditText editText, etd;
    private Button button;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ProgressBar progressBar;
    private FirebaseRecyclerAdapter adapter;
    private ActionBarDrawerToggle toggle;
    ImageView userpic;
    TextView username;




    public ElectronicsActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = findViewById(R.id.list);
        progressBar = (ProgressBar) findViewById(R.id.load);
        progressBar.setVisibility(View.VISIBLE);


        //actionbar
        ActionBar actionBar= getSupportActionBar();
        actionBar.setTitle("   Electronics");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF97BF4A")));
        //navigate button work
        DrawerLayout drawer = findViewById(R.id.drawer_layout1);

        NavigationView navigationView = findViewById(R.id.nav_viewE);
                            toggle = new ActionBarDrawerToggle(
                this, drawer,R.string.navigation_drawer_open, R.string.navigation_drawer_close);
                            //navigationView.setItemIconTintList(null);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
//google retrived data
        userpic=(ImageView)findViewById(R.id.userPic);
        username=(TextView)findViewById(R.id.userName);

        LoginActivity obj=new LoginActivity();
        username.setText(obj.personName);
       // userpic.setImageURI((obj.personPhoto));
        Picasso.get().load(obj.personPhoto).into(userpic);


                //for horizontal scroll
        //linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        fetch();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
      if(toggle.onOptionsItemSelected(item)){
          return true;
      }
      switch (item.getItemId()){
          case R.id.action_cart:
              Intent intent = new Intent(ElectronicsActivity.this,cart.class);
//                        Log.d("product",Product_Name_List.get(0).toString());
              // Title.setText(Product_Price_List.get(0).toString());
              startActivity(intent);
              return true;
          case R.id.action_search:


      };
        return super.onOptionsItemSelected(item);
    }



    private void fetch() {
        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("Data");

//        FirebaseRecyclerOptions<Model> options =
//                new FirebaseRecyclerOptions.Builder<Model>()
//                        .setQuery(query, new SnapshotParser<Model>() {
//                            @NonNull
//                            @Override
//                            public Model parseSnapshot(@NonNull DataSnapshot snapshot) {
//                                return new Model(snapshot.child("id").getValue().toString(),
//                                        snapshot.child("title").getValue().toString(),
//                                        snapshot.child("desc").getValue().toString());
//                            }
//                        })
//                        .build();
        //fetch
        FirebaseRecyclerOptions<Model> options =
                new FirebaseRecyclerOptions.Builder<Model>()
                        .setQuery(query, Model.class)
                        .build();
        //

        adapter = new FirebaseRecyclerAdapter<Model, ViewHolder>(options) {
            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item, parent, false);
              //  ViewHolder v =onCreateViewHolder(parent,viewType);


                return new ViewHolder(view);
            }


            @Override
            protected void onBindViewHolder(final ViewHolder holder, final int position, final Model model) {
                holder.setTxtTitle(model.getTitle());
                holder.setTxtDesc(model.getDescription());
                holder.setListPrice("PKR: "+model.getPrice());
               // holder.setNewImage(Picasso.get().load(model.getImage());
holder.setImageLink(model.getImage());
                progressBar.setVisibility(View.GONE);


                holder.root.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d("start","start");
                        Intent intent = new Intent(ElectronicsActivity.this,Item_details.class);
//                        Log.d("enter","enter");
                            intent.putExtra("Title",model.getTitle());
                            intent.putExtra("Description",model.getDescription());
                            intent.putExtra("Image",model.getImage());
                        intent.putExtra("Price",model.getPrice());
                            startActivity(intent);
                       Log.d("end","end");

                    }
                });
            }


        };
      //  progressBar.setVisibility(View.GONE);
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();

        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
    ///search===============================================================================
    private void firebaseSearch(String SearchText){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Data");
        Query query = FirebaseDatabase.getInstance()
                .getReference("Data").orderByChild("title").startAt(SearchText).endAt(SearchText+"\uf8ff");
//        Query query = FirebaseDatabase.getInstance()
//                .getReference()
//                .child("Data");

        //Query firebaseSearchQuery=myRef.orderByChild("title").startAt(SearchText).endAt(SearchText +"\uf8ff");


        FirebaseRecyclerOptions<Model> options1 =
                new FirebaseRecyclerOptions.Builder<Model>()
                        .setQuery(query, Model.class)
                        .build();
        //

        adapter = new FirebaseRecyclerAdapter<Model, ViewHolder>(options1) {
            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item, parent, false);
                //  ViewHolder v =onCreateViewHolder(parent,viewType);


                return new ViewHolder(view);
            }


            @Override
            protected void onBindViewHolder(final ViewHolder holder, final int position, final Model model) {
                holder.setTxtTitle(model.getTitle());
                holder.setTxtDesc(model.getDescription());
                holder.setListPrice("PKR: "+model.getPrice());
                // holder.setNewImage(Picasso.get().load(model.getImage());
                holder.setImageLink(model.getImage());



                holder.root.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       // Toast.makeText(getApplicationContext(), model.getDescription(),
                              //  Toast.LENGTH_LONG).show();
                        Log.d("start","start");
                        Intent intent = new Intent(ElectronicsActivity.this,Item_details.class);
//                        Log.d("enter","enter");
                        intent.putExtra("Title",model.getTitle());
                        intent.putExtra("Description",model.getDescription());
                        intent.putExtra("Image",model.getImage());
                        intent.putExtra("Price",model.getPrice());
                        startActivity(intent);
               Log.d("end","end");

                    }
                });
            }


        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }
//search end here//
    //onClose SearchView

    private void SearchClose(){

        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("Data");

        //Query firebaseSearchQuery=myRef.orderByChild("title").startAt(SearchText).endAt(SearchText +"\uf8ff");


        FirebaseRecyclerOptions<Model> options1 =
                new FirebaseRecyclerOptions.Builder<Model>()
                        .setQuery(query, Model.class)
                        .build();
        //

        adapter = new FirebaseRecyclerAdapter<Model, ViewHolder>(options1) {
            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item, parent, false);
                //  ViewHolder v =onCreateViewHolder(parent,viewType);


                return new ViewHolder(view);
            }


            @Override
            protected void onBindViewHolder(final ViewHolder holder, final int position, final Model model) {
                holder.setTxtTitle(model.getTitle());
                holder.setTxtDesc(model.getDescription());
                holder.setListPrice("PKR: "+model.getPrice());
                // holder.setNewImage(Picasso.get().load(model.getImage());
                holder.setImageLink(model.getImage());



                holder.root.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                      //  Toast.makeText(getApplicationContext(), model.getDescription(),
                           //     Toast.LENGTH_LONG).show();
                        Log.d("start","start");
                        Intent intent = new Intent(ElectronicsActivity.this,Item_details.class);
//                        Log.d("enter","enter");
                        intent.putExtra("Title",model.getTitle());
                        intent.putExtra("Description",model.getDescription());
                        intent.putExtra("Image",model.getImage());
                        intent.putExtra("Price",model.getPrice());
                        startActivity(intent);
                        Log.d("end","end");

                    }
                });
            }


        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }
    //close searchview end here

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        MenuItem item=menu.findItem(R.id.action_search);
        SearchView searchView=(SearchView) MenuItemCompat.getActionView(item);
       // MenuItem itemCart=menu.findItem(R.id.action_cart);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                firebaseSearch(s);
//
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                //filter as you type
                firebaseSearch(s);

                return false;
            }
        });
//        itemCart.setOnClickListener(new SearchView.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(),
//                        "Product added", Toast.LENGTH_SHORT)
//                        .show();
//            }
       // });


        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                SearchClose();
                return false;
            }
        });



        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        if (id == R.id.nav_home) {
            Intent intent = new Intent(getApplicationContext(), ElectronicsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            this.startActivity(intent);
        } else if (id == R.id.MobilePhonesNavi) {
            Intent intent = new Intent(getApplicationContext(), MobilePhones.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            this.startActivity(intent);
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        }else if (id == R.id.logoutNavigation) {
            SharedPreferences sp=getSharedPreferences("Login", MODE_PRIVATE);
            SharedPreferences.Editor Ed=sp.edit();

            Ed.clear();
            Ed.putInt("Token",2);
            Ed.commit();


            //gmailLogout
            AuthUI.getInstance()
                    .signOut(this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        public void onComplete(@NonNull Task<Void> task) {
                            // user is now signed out
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        }
                    });
            //removing static data
            LoginActivity obj=new LoginActivity();
            obj.personName="user";
            obj.personPhoto= Uri.parse("android.resource://com.example.ecommercelogin/drawable/user_icon");

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout1);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        return super.onOptionsItemSelected(item);
//    }

    public  class ViewHolder extends RecyclerView.ViewHolder  {
        public LinearLayout root;
        public TextView txtTitle;
        public ImageView newImage;
        public TextView txtDesc;
        public TextView listPrice;
        public String imageLink;



        public ViewHolder(View itemView) {
            super(itemView);




            root = itemView.findViewById(R.id.list_root);
            txtTitle = itemView.findViewById(R.id.list_title);
            newImage =itemView.findViewById(R.id.rImage);
            txtDesc = itemView.findViewById(R.id.list_desc);
            listPrice=itemView.findViewById(R.id.List_price);

        }

        public TextView getListPrice() {
            return listPrice;
        }

        public void setListPrice(String listPrice) {
            this.listPrice.setText(listPrice);
        }

        public TextView getTxtTitle() {
            return txtTitle;
        }

        public TextView getTxtDesc() {
            return txtDesc;
        }

        public String getImageLink() {
            return imageLink;
        }

        public void setTxtTitle(String string) {
            txtTitle.setText(string);
        }


        public void setTxtDesc(String string) {
            txtDesc.setText(string);
        }

        public ImageView getNewImage() {
            return newImage;
        }

        public void setNewImage(ImageView newImage) {
            this.newImage = newImage;
        }
        public void setImageLink (String Image1) {
            // this.imageLink =Image1;
            Picasso.get().load(Image1).into(newImage);


        }


    }
    //for navigation item selector


}
