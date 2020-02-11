package com.claxtastic.newsgateway;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    public final String KEY = "30b6e774879741cb982dbd39a3153b17";
    private static final String TAG = "MainActivity";
    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.drawerLayout = findViewById(R.id.drawer_layout);
        this.drawerList = findViewById(R.id.drawer_list);

        initializeDrawerLayout();
    }

    public void initializeDrawerLayout() {
        this.drawerList.setOnItemClickListener(
                new ListView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        TODO: startActivity(intent) article/source activity
                    }
                }
        );

        this.drawerToggle = new ActionBarDrawerToggle(
                this,
                this.drawerLayout,
                R.string.drawer_open,
                R.string.drawer_close
        );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (this.drawerToggle.onOptionsItemSelected(item)) {
            Log.d(TAG, "onOptionsItemSelected: " + item);
            return true;
        }

//        setTitle(item.getTitle());
//        ArrayList<>
        return false;
    }
}
