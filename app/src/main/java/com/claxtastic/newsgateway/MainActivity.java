package com.claxtastic.newsgateway;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Menu menuBar;
    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private ActionBarDrawerToggle drawerToggle;
    private ViewPager pager;
    private ArticlePagerAdapter articlePagerAdapter;

    private ArrayList<Source> currentSources;
    private ArrayList<Source> allSources;
    private HashSet<String> topics;
    private ArrayList<String> languages;
    private ArrayList<String> countries;

    private List<Fragment> fragments = new ArrayList<>();
    private HashMap<String, Integer> topicIntMap = new HashMap<>();
    private String categoryExpanded;
    private String selectedSourceName;
    private int[] topicColors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.drawerLayout = findViewById(R.id.drawer_layout);
        this.drawerList = findViewById(R.id.drawer_list);
        topicColors = getResources().getIntArray(R.array.topicColors);

        setupDrawerLayout();
        new AsyncSourcesDownloader(this).execute();
    }

    public void setupDrawerLayout() {
        this.drawerList.setOnItemClickListener(
                new ListView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        onClickSource(position);
                        drawerLayout.closeDrawer(drawerList);
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

        articlePagerAdapter = new ArticlePagerAdapter(getSupportFragmentManager(), fragments);
        pager = findViewById(R.id.viewpager);
        pager.setAdapter(articlePagerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        this.menuBar = menu;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (this.drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()) {
            /* for each category, set a flag so we know how to categorize the filter option */
            case R.id.item_topics:
                this.categoryExpanded = "topics";
                return true;
            case R.id.item_languages:
                this.categoryExpanded = "languages";
                return true;
            case R.id.item_countries:
                this.categoryExpanded = "countries";
                return true;

            default:
                /* something other than a category was selected */
                switch (categoryExpanded) {
                    /* topic type selected */
                    case "topics":
                        ArrayList<Source> topicFilteredSources = Utilities.filterOnTopic(this.currentSources, item.getTitle().toString().toLowerCase());
                        updateDrawerList(topicFilteredSources, (String) item.getTitle());
                        return super.onOptionsItemSelected(item);
                    /* language type selected */
                    case "languages":
                        String languageCode = Utilities.getLanguageCode((String) item.getTitle(), getResources().openRawResource(R.raw.language_codes));
                        ArrayList<Source> languageFilteredSources = Utilities.filterOnLanguage(this.currentSources, languageCode);
                        updateDrawerList(languageFilteredSources, (String) item.getTitle());
                        return super.onOptionsItemSelected(item);
                    /* country type selected */
                    case "countries":
                        String countryCode = Utilities.getCountryCode((String) item.getTitle(), getResources().openRawResource(R.raw.country_codes));
                        ArrayList<Source> countryFilteredSources = Utilities.filterOnCountry(this.currentSources, countryCode);
                        updateDrawerList(countryFilteredSources, (String) item.getTitle());
                        return super.onOptionsItemSelected(item);
                }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    /* Callback from AsyncSourcesDownloader */
    public void setSources(ArrayList<Source> sources) {
        /* Save sources twice so we can revert filters */
        this.allSources = new ArrayList<>();
        allSources.addAll(sources);
        this.currentSources = sources;
    }

    /* Callback from AsyncSourcesDownloader */
    public void setTopics(HashSet<String> topics) {
//        MenuItem topicsItem = findViewById(R.id.submenu_topics);
//        SubMenu subMenu = topicsItem.getSubMenu();
//        subMenu.add("all");
        int i = 0;
        for (String topic: topics) {
            SpannableString topicString = new SpannableString(topic);
            topicString.setSpan(new ForegroundColorSpan(topicColors[i]), 0, topicString.length(), 0);
            topicIntMap.put(topic, topicColors[i]);
//            subMenu.add(topicString);
            i++;
        }
        this.topics = topics;
    }

    /* Callback from AsyncSourcesDownloader */
    public void setLanguages(HashSet<String> languages) {
        this.languages = Utilities.parseLanguageJson(languages, getResources().openRawResource(R.raw.language_codes));
    }

    /* Callback from AsyncSourcesDownloader */
    public void setCountries(HashSet<String> countries) {
        this.countries = Utilities.parseCountryJson(countries, getResources().openRawResource(R.raw.country_codes));
    }

    /* Callback from AsyncSourcesDownloader */
    public void setDrawerListAdapter() {
        this.drawerList.setAdapter(new SourceArrayAdapter(this, R.layout.drawer_item, this.currentSources));
        setTitle("News Gateway (" + this.currentSources.size() + ")");
    }

    /* Update sources list with new list of sources */
    public void updateDrawerList(ArrayList<Source> newSources, String selectedItem) {
        if (selectedItem.equals("all")) {
            this.currentSources.clear();
            this.currentSources.addAll(allSources);
            setTitle("News Gateway (" + this.currentSources.size() + ")");
            ((ArrayAdapter) drawerList.getAdapter()).notifyDataSetChanged();
            return;
        }
        /* Filter options produced a 0 list, reject */
        else if (newSources.size() == 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Invalid filter options").setMessage("The topic/language/country options selected produced no results.").show();
            return;
        }
        else {
            this.currentSources.clear();
            this.currentSources.addAll(newSources);
            setTitle("News Gateway (" + this.currentSources.size() + ")");
            ((ArrayAdapter) drawerList.getAdapter()).notifyDataSetChanged();
            return;
        }
    }

    /* Called by drawer's onItemClickListener */
    public void onClickSource(int position) {
        pager.setBackground(null);
        Source selectedSource = currentSources.get(position);
        selectedSourceName = selectedSource.getName();
        new AsyncArticleDownloader(this, selectedSource.getId()).execute();
        drawerLayout.closeDrawer(drawerList);
    }

    /* Callback from AsyncArticleDownloader */
    public void handleArticlesAPIResponse(ArrayList<Article> articles) {
        setTitle(selectedSourceName);
        for (int i = 0; i < articlePagerAdapter.getCount(); i++)
            articlePagerAdapter.notifyChangeInPosition(i);

        fragments.clear();

        for (int i = 0; i < articles.size(); i++) {
            fragments.add(ArticleFragment.newInstance(articles.get(i), i + 1, articles.size()));
        }

        articlePagerAdapter.notifyDataSetChanged();

        pager.setCurrentItem(0);
    }

    public String getCategoryExpanded() { return this.categoryExpanded; }

    public ArrayList<Source> getCurrentSources() { return this.currentSources; }
}
