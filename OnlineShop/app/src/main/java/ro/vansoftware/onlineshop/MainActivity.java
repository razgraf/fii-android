package ro.vansoftware.onlineshop;

import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import ro.vansoftware.onlineshop.adapter.ProductAdapter;
import ro.vansoftware.onlineshop.model.Product;

public class MainActivity extends AppCompatActivity {



    ListView listView;
    ProductAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onViewBinder();
        onDataBinder();

        Log.e("Lifecycle", "onCreate");
    }




    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("list", this.adapter.getProducts());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        this.adapter.setProducts(savedInstanceState.<Product>getParcelableArrayList("list"));
        this.adapter.notifyDataSetChanged();
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("Lifecycle", "onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("Lifecycle", "onResume");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("Lifecycle", "onDestroy");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.action_first: Log.e("Menu", "Action 1"); break;
            case R.id.action_second: Log.e("Menu", "Action 2");break;
            default: break;
        }

        return super.onOptionsItemSelected(item);
    }




    private void onViewBinder(){
        listView = findViewById(R.id.list);
    }
    private void onDataBinder(){


        final ArrayList<Product> products = new ArrayList<>();
        products.add(new Product("Milk", "Description of Milk"));
        products.add(new Product("Cola","Description of Cola"));
        products.add(new Product("Pizza","Description of Pizza"));

        adapter = new ProductAdapter(this, R.layout.activity_main, products);
        listView.setAdapter(adapter);
    }
}
