package ro.vansoftware.onlineshop;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.util.ArrayList;

import ro.vansoftware.onlineshop.adapter.ProductAdapter;
import ro.vansoftware.onlineshop.dialog.ConnectDialogFragment;
import ro.vansoftware.onlineshop.model.Product;
import ro.vansoftware.onlineshop.storage.Shared;

public class MainActivity extends AppCompatActivity implements ConnectDialogFragment.ConnectDialogListener {



    ListView listView;
    ProductAdapter adapter;

    ViewFlipper viewFlipper;

    Boolean connected = false;

    Shared storage, settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.storage = new Shared(this, Shared.STORAGE);
        this.settings = new Shared(this, Shared.SETTINGS);
        onViewBinder();
        onDataBinder();
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
            case R.id.action_website:
                String website = "https://www.vansoftware.ro";
                Intent intentShow = new Intent(Intent.ACTION_VIEW);
                intentShow.setData(Uri.parse(website));
                startActivity(intentShow);
                break;
            case R.id.action_call:
                String number = "tel:0712345678";
                Intent intentCall = new Intent(Intent.ACTION_DIAL);
                try {
                    intentCall.setData(Uri.parse(number));
                    startActivity(intentCall);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(this, "Could not find an activity to place the call.", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.action_about:
                Intent intentAbout = new Intent(this, AboutActivity.class);
                startActivity(intentAbout);
                break;
            case R.id.action_settings:
                Intent intentSettings = new Intent(this, SettingsActivity.class);
                startActivity(intentSettings);
                break;
            default: break;
        }

        return super.onOptionsItemSelected(item);
    }




    private void onViewBinder(){
        this.listView = findViewById(R.id.list);
        this.viewFlipper = findViewById(R.id.header);
        this.onUserBinder();
    }

    private void onUserBinder(){
        final MainActivity self = this;
        this.connected = self.storage.get(Shared.USER_USERNAME) != null && self.storage.get(Shared.USER_PASSWORD) != null;

        if(this.connected){

            String message = self.settings.get(Shared.SETTING_MESSAGE,"Welcome");
            String age = self.settings.get(Shared.SETTING_AGE,"Alive");
            String username = self.storage.get( Shared.USER_USERNAME,"");


            viewFlipper.setDisplayedChild(viewFlipper.indexOfChild(findViewById(R.id.headerKnown)));
            ((TextView)findViewById(R.id.welcome)).setText( message + " " + username + "("+age +")" );
            (findViewById(R.id.buttonDisconnect)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    self.storage.set( Shared.USER_USERNAME, null);
                    self.storage.set( Shared.USER_PASSWORD, null);
                    self.onUserBinder();
                }
            });
        }else {
            viewFlipper.setDisplayedChild(viewFlipper.indexOfChild(findViewById(R.id.headerUnknown)));
            (findViewById(R.id.buttonConnect)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ConnectDialogFragment connect = new ConnectDialogFragment();
                    connect.show(getSupportFragmentManager(), "connect");

                }
            });
        }

    }

    private void onDataBinder(){


        final ArrayList<Product> products = new ArrayList<>();
        products.add(new Product("Milk", "Description of Milk"));
        products.add(new Product("Cola","Description of Cola"));
        products.add(new Product("Pizza","Description of Pizza"));

        this.adapter = new ProductAdapter(this, R.layout.activity_main, products);
        this.listView.setAdapter(adapter);

        this.onUserBinder();


    }

    @Override
    public void onDialogPositiveClick(String username, String password) {
        this.storage.set(Shared.USER_USERNAME, username);
        this.storage.set( Shared.USER_PASSWORD, password);
        onUserBinder();
    }

    @Override
    public void onDialogNegativeClick() {

    }
}
