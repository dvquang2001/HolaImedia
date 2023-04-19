package com.example.holaimedia.base;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.holaimedia.R;
import com.example.holaimedia.activity.auth.SplashActivity;
import com.example.holaimedia.adapter.food.OnModifyClick;
import com.example.holaimedia.adapter.food.ProductAdapter;
import com.example.holaimedia.model.food.Product;
import com.example.holaimedia.model.auth.User;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.util.List;

public abstract class BaseFoodFragment extends Fragment implements OnModifyClick {
    protected FirebaseFirestore fireStore;
    protected List<Product> productList;
    protected ProductAdapter productAdapter;
    protected User user;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fireStore = FirebaseFirestore.getInstance();
        Gson gson = new Gson();
        String userJson = SplashActivity.sharedPreferences.getString(SplashActivity.USER_DATA, null);
        user = gson.fromJson(userJson, User.class);
    }

    protected void fetchFruitList() {
            fireStore.collection("Product").whereEqualTo("type", "fruit").get().addOnCompleteListener(task -> {
                for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                    Product product = documentSnapshot.toObject(Product.class);
                    productList.add(product);
                    productAdapter.notifyItemRangeChanged(productList.size() - 1, productList.size());
                }
            });
    }

    protected void fetchVegetableList() {
            fireStore.collection("Product").whereEqualTo("type", "vegetable").get().addOnCompleteListener(task -> {
                for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                    Product product = documentSnapshot.toObject(Product.class);
                    productList.add(product);
                    productAdapter.notifyItemRangeChanged(productList.size() - 1, productList.size());
                }
            });
    }

    protected void fetchMilkList() {
            fireStore.collection("Product").whereEqualTo("type", "milk").get().addOnCompleteListener(task -> {
                for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                    Product product = documentSnapshot.toObject(Product.class);
                    productList.add(product);
                    productAdapter.notifyItemRangeChanged(productList.size() - 1, productList.size());
                }
            });
    }

    protected void fetchFastFoodList() {
            fireStore.collection("Product").whereEqualTo("type", "fastfood").get().addOnCompleteListener(task -> {
                for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                    Product product = documentSnapshot.toObject(Product.class);
                    productList.add(product);
                    productAdapter.notifyItemRangeChanged(productList.size() - 1, productList.size());
                }
            });
    }


    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void edit(Product product) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        LayoutInflater inflater = this.getLayoutInflater();
        View dialog = inflater.inflate(R.layout.dialog_edit_product, null);
        builder.setView(dialog);

        EditText etName = dialog.findViewById(R.id.etName);
        EditText etPrice = dialog.findViewById(R.id.etPrice);
        Button btnConfirm = dialog.findViewById(R.id.btnConfirm);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        btnConfirm.setOnClickListener(view -> {
            if (!etName.getText().toString().trim().isEmpty()) {
                product.setName(etName.getText().toString().trim());
            }
            if (!etPrice.getText().toString().trim().isEmpty()) {
                product.setPrice(Integer.parseInt(etPrice.getText().toString().trim()));
            }
            fireStore.collection("Product")
                    .document(product.getId())
                    .set(product)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            productAdapter.notifyDataSetChanged();
                            Toast.makeText(requireContext(), "Chỉnh sửa thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(requireContext(), task.getException().toString(), Toast.LENGTH_SHORT).show();
                        }
                        alertDialog.dismiss();
                    });
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void delete(Product product) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        LayoutInflater inflater = this.getLayoutInflater();
        View dialog = inflater.inflate(R.layout.dialog_delete_product, null);
        builder.setView(dialog);

        Button btnConfirm = dialog.findViewById(R.id.btnConfirm);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        btnConfirm.setOnClickListener(view -> {
            fireStore.collection("Product")
                    .document(product.getId())
                    .delete()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            productList.remove(product);
                            productAdapter.notifyDataSetChanged();
                            Toast.makeText(requireContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(requireContext(), task.getException().toString(), Toast.LENGTH_SHORT).show();
                        }
                        alertDialog.dismiss();
                    });
        });
        btnCancel.setOnClickListener(view -> {
            alertDialog.dismiss();
        });
    }
}
