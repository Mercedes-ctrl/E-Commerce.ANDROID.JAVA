package com.unt_apps.ecommerceebfarma.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.smarteist.autoimageslider.SliderView;
import com.unt_apps.ecommerceebfarma.R;
import com.unt_apps.ecommerceebfarma.activities.DetailedActivity;
import com.unt_apps.ecommerceebfarma.activities.ShowAllActivity;
import com.unt_apps.ecommerceebfarma.adapters.CategoryAdapter;
import com.unt_apps.ecommerceebfarma.adapters.NewProductsAdapter;
import com.unt_apps.ecommerceebfarma.adapters.PopularProductsAdapter;
import com.unt_apps.ecommerceebfarma.adapters.sliderBanner;
import com.unt_apps.ecommerceebfarma.models.CategoryModel;
import com.unt_apps.ecommerceebfarma.models.NewProductsModel;
import com.unt_apps.ecommerceebfarma.models.PopularProductsModel;
import com.unt_apps.ecommerceebfarma.models.SliderData;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    TextView catShowAll,popularShowAll,newProductShowAll;



    LinearLayout linearLayout;
    ProgressDialog progressDialog;  // el cargando antes de ingresar al aplicativo
    RecyclerView catRecyclerview,newProductRecyclerview,popularRecyclerview;

    //Category recyclerview
    CategoryAdapter categoryAdapter;
    List<CategoryModel> categoryModelList;

    // New Product recyclerview
    NewProductsAdapter newProductsAdapter;
    List<NewProductsModel> newProductsModelList;
    // Popular products
    PopularProductsAdapter popularProductsAdapter;
    List<PopularProductsModel> popularProductsModelList;

    //FireStore
    FirebaseFirestore db;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_home, container, false);

        db=FirebaseFirestore.getInstance();

        progressDialog = new ProgressDialog(getActivity());
        catRecyclerview=root.findViewById(R.id.rec_category);   //category
        newProductRecyclerview=root.findViewById(R.id.new_product_rec); //new products
        popularRecyclerview=root.findViewById(R.id.popular_rec);        //popular products
        catRecyclerview.setHasFixedSize(true);

        // All products
        catShowAll=root.findViewById(R.id.category_see_all);
        popularShowAll=root.findViewById(R.id.popular_see_all);
        newProductShowAll=root.findViewById(R.id.newProducts_see_all);

        catShowAll.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                Intent intent=new Intent(getContext(), ShowAllActivity.class);
                startActivity(intent);
            }

        });
        newProductShowAll.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                Intent intent=new Intent(getContext(), ShowAllActivity.class);
                startActivity(intent);
            }

        });
        popularShowAll.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                Intent intent=new Intent(getContext(), ShowAllActivity.class);
                startActivity(intent);
            }

        });



        linearLayout=root.findViewById(R.id.home_layout);
        linearLayout.setVisibility(View.GONE);


        //image slider
        SliderView sliderView =root.findViewById(R.id.image_slider);
//        List<> slideModels=new ArrayList<>();
         ArrayList<SliderData> sliderDataArrayList = new ArrayList<>();
        // adding the urls inside array list
        //slideModels.add(new SlideModel(R.drawable.banner1,"Excelente servicio",ScaleTypes.CENTER_CROP));
        sliderDataArrayList.add(new SliderData(R.drawable.banner1));
        sliderDataArrayList.add(new SliderData(R.drawable.banner2));
        sliderDataArrayList.add(new SliderData(R.drawable.banner3));

        // passing this array list inside our adapter class.
        sliderBanner adapter = new sliderBanner(this, sliderDataArrayList);

        // below method is used to set auto cycle direction in left to
        // right direction you can change according to requirement.
        sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);

        // below method is used to
        // setadapter to sliderview.
        sliderView.setSliderAdapter(adapter);

        // below method is use to set
        // scroll time in seconds.
        sliderView.setScrollTimeInSec(3);

        // to set it scrollable automatically
        // we use below method.
        sliderView.setAutoCycle(true);

        // to start autocycle below method is used.
        sliderView.startAutoCycle();

        progressDialog.setTitle("Bienvenido al Ecommerce de EBFARMA");
        progressDialog.setMessage("Por favor espere...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        //Category

        catRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        categoryModelList=new ArrayList<>();
        categoryAdapter =new CategoryAdapter(getContext(),categoryModelList);
        catRecyclerview.setAdapter(categoryAdapter);
        //Read data FireStore

        db.collection("Categoría")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                CategoryModel categoryModel=document.toObject(CategoryModel.class);
                                categoryModelList.add(categoryModel);
                                categoryAdapter.notifyDataSetChanged();
                                linearLayout.setVisibility(View.VISIBLE);
                                progressDialog.dismiss();
                            }
                        } else {
                            Toast.makeText(getActivity(), ""+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        //New Products
        newProductRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        newProductsModelList=new ArrayList<>();
        newProductsAdapter=new NewProductsAdapter(getContext(),newProductsModelList);
        newProductRecyclerview.setAdapter(newProductsAdapter);

        db.collection("NuevosProductos")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                NewProductsModel newProductsModel=document.toObject(NewProductsModel.class);
                                newProductsModelList.add(newProductsModel);
                                newProductsAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(getActivity(), ""+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        // Popular products
        popularRecyclerview.setLayoutManager(new GridLayoutManager(getActivity(),2));
        popularProductsModelList=new ArrayList<>();
        popularProductsAdapter=new PopularProductsAdapter(getContext(),popularProductsModelList);
        popularRecyclerview.setAdapter(popularProductsAdapter);

        db.collection("AllProducts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                PopularProductsModel popularProductsModel=document.toObject(PopularProductsModel.class);
                                popularProductsModelList.add(popularProductsModel);
                                popularProductsAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(getActivity(), ""+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


        return root;
    }





}