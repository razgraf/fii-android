package ro.vansoftware.onlineshop.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ro.vansoftware.onlineshop.R;
import ro.vansoftware.onlineshop.model.Product;

public class ProductAdapter extends ArrayAdapter<Product> {

    private Context context;
    private ArrayList<Product> products = new ArrayList<>();


    public ProductAdapter(Context context, int resource, ArrayList<Product> products){
        super(context,resource, products);
        this.context = context;
        this.products = products;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView,  @NonNull ViewGroup parent) {
        final ProductViewHolder viewHolder;


        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
            viewHolder = new ProductViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ProductViewHolder) convertView.getTag();
        }

        Product product = this.products.get(position);
        viewHolder.setData(this.context, product);

        return convertView;
    }

    @NonNull
    @Override
    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

}


