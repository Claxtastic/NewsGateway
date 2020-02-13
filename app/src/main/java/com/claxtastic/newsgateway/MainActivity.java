package com.claxtastic.newsgateway;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Menu menuBar;
    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private ActionBarDrawerToggle drawerToggle;

    private ArrayList<Source> sources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.drawerLayout = findViewById(R.id.drawer_layout);
        this.drawerList = findViewById(R.id.drawer_list);

        setupDrawerLayout();
        new AsyncSourcesDownloader(this).execute();
    }

    public void setupDrawerLayout() {
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
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menuBar = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (this.drawerToggle.onOptionsItemSelected(item)) {
            Log.d(TAG, "onOptionsItemSelected: " + item);
            return true;
        }

        // TODO: Fix this so it acts on clicking actual menu items rather than submenus

        setTitle("News Gateway (" + this.sources.size() + ")");
        ((ArrayAdapter) drawerList.getAdapter()).notifyDataSetChanged();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    /* Response from AsyncSourcesDownloader */
    public void handleSourcesAPIResponse(ArrayList<Source> sources, ArrayList<HashSet<String>> sets) {
        HashSet<String> topics = sets.get(0);
        HashSet<String> languages = sets.get(1);
        HashSet<String> countries = sets.get(2);

        // TODO: Add default 'all' option to topics, languages, countries
        /* Add topics to topics submenu */
        MenuItem item = menuBar.getItem(0);
        SubMenu subMenu = item.getSubMenu();
        for (String topic: topics)
            subMenu.add(topic);

        try {
            /* Add languages to languages submenu */
            item = menuBar.getItem(1);
            subMenu = item.getSubMenu();
            ArrayList<String> fullLanguageStrings = Utilities.parseLanguageJson(languages, getResources().openRawResource(R.raw.language_codes));
            for (String language : fullLanguageStrings)
                subMenu.add(language);

            /* Add countries to countries submenu */
            item = menuBar.getItem(2);
            subMenu = item.getSubMenu();
            ArrayList<String> fullCountryStrings = Utilities.parseCountryJson(countries, getResources().openRawResource(R.raw.country_codes));
            for (String country : fullCountryStrings)
                subMenu.add(country);

        } catch (Exception e) {
            Log.e(TAG, "handleSourcesAPIResponse: ", e);
        }

        this.sources = sources;
        this.drawerList.setAdapter(new ArrayAdapter<>(this, R.layout.drawer_item, this.sources));
        setTitle("News Gateway (" + this.sources.size() + ")");
    }
}
