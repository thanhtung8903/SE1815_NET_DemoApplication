package com.example.se1815_net_demoapplication.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.se1815_net_demoapplication.ProductListActivity;
import com.example.se1815_net_demoapplication.R;
import com.example.se1815_net_demoapplication.bean.ProductBean;

import java.util.List;

public class ProductAdapter extends
        RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<ProductBean> productList;
    private final Context context;
    private int selectedPosition;
    public ProductAdapter(List<ProductBean> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }
    public int getSelectedPosition() {
        return selectedPosition;
    }
    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_item, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        ProductBean product = productList.get(position);
        holder.tvProductId.setText(String.valueOf(product.getId()));
        holder.tvProductName.setText(product.getName());
        holder.tvPrice.setText(String.valueOf(product.getPrice()));
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener {
        private final TextView tvProductId;
        private final TextView tvProductName;
        private final TextView tvPrice;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductId = itemView.findViewById(R.id.tvProductId);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvProductName.setOnClickListener(this);
            ((ProductListActivity) context).registerForContextMenu(tvProductName);
            tvProductName.setOnLongClickListener(this);
        }
        @Override
        public void onClick(View v) {
            // Handle item click event here
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Confirmation");
            builder.setMessage("Are you sure you want to delete Product ID: " + tvProductId.getText() +
                    "Product Name: " + tvProductName.getText())
                    .setPositiveButton("OK", (dialog, which) -> {
                        productList.remove(getAdapterPosition());
                        notifyItemRemoved(getAdapterPosition());
                        dialog.dismiss();
                    }).setNegativeButton("Cancel", (dialog, which) -> {
                        dialog.dismiss();
                    });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        @Override
        public boolean onLongClick(View v) {
            selectedPosition = getAdapterPosition();
            Toast.makeText(context, "Long Clicked: Position:"
                    + getAdapterPosition(), Toast.LENGTH_SHORT).show();
            v.showContextMenu();
            return true;
        }
    }
}
