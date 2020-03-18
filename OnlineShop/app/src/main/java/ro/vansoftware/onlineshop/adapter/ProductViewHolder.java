package ro.vansoftware.onlineshop.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import ro.vansoftware.onlineshop.R;
import ro.vansoftware.onlineshop.model.Product;

public class ProductViewHolder {

    private TextView titleTextView;
    private View containerView;

    public ProductViewHolder(View view){

        this.containerView = view.findViewById(R.id.container);
        this.titleTextView = view.findViewById(R.id.title);
    }

    public void setData(final Context context, final Product product){
        this.titleTextView.setText(product.getTitle());
        this.containerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, product.getDescription(), Toast.LENGTH_LONG).show();
            }
        });
    }

}
