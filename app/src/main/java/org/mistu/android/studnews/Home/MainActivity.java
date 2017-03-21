package org.mistu.android.studnews.Home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.mistu.android.studnews.AppConstants;
import org.mistu.android.studnews.BuildConfig;
import org.mistu.android.studnews.Details.NewsDetailsActivity;
import org.mistu.android.studnews.R;
import org.mistu.android.studnews.Utils.News;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NewsFragment.OnNewsFragmentInteractionListener,
                    CategoryFragment.OnCategoryFragmentInteractionListener {

    private static final int RC_SIGN_IN = 831;
    private static final String DEFAULT_CAT = "ALL";
    private static int count = 0;

    private FloatingActionButton fab;
    private CategoryFragment categoryFragment;
    private FragmentManager fragmentManager;
    private NewsFragment newsFragment;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private String userName;
    private String categorySelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        if (savedInstanceState != null) {
            return;
        }

        initRequiredVariables();
        setAuthStateListener();


    }

    private void initRequiredVariables() {
        categorySelected = DEFAULT_CAT;
        fragmentManager = getSupportFragmentManager();
        fab = (FloatingActionButton) findViewById(R.id.fab);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    private void setAuthStateListener(){
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    Log.d("SIGNED_IN", user.getDisplayName());
                    userName = user.getDisplayName();
                    onSignedInInit();
                }else {
                    Log.d("SIGNED_OUT", "USER IS NOT SIGNED IN");
                    onSignedOutCleanUp();
                    startActivityForResult(
                        AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setIsSmartLockEnabled(!BuildConfig.DEBUG)
                            .setTheme(R.style.LoginTheme)
                            .setLogo(R.drawable.swen_rect)
                            .setProviders(Arrays.asList(
                                    new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                                    new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()
                                    )
                            ).build(), RC_SIGN_IN);
                }
            }
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN){
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if(resultCode == RESULT_OK) {
                Toast.makeText(MainActivity.this, "Signed In", Toast.LENGTH_SHORT).show();
                onSignedInInit();
            }
            else {
                if (response == null) {
                    Toast.makeText(MainActivity.this, "Sign In Cancelled", Toast.LENGTH_LONG).show();
                }
                else if (response.getErrorCode() == ErrorCodes.NO_NETWORK) {
                    Toast.makeText(MainActivity.this, "   Please Check Your\nINTERNET CONNECTION !", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(MainActivity.this, "Sign In Cancelled", Toast.LENGTH_LONG).show();
                }
                this.finish();
            }
        }
    }

    private void onSignedInInit(){
        Log.d("MainFragmentCreated", String.valueOf(count));
        if(newsFragment == null) {
            Log.d("NEWS_FRAG NULL creating", String.valueOf(count) );
            setNewsFragment();
        }
        if (categoryFragment == null) {
            Log.d("CAT_FRAG NULL creating ", String.valueOf(count++) );
            setUpCategoryFrag();
        }

    }

    private void onSignedOutCleanUp(){
        this.userName = null;
        if (newsFragment != null) {
            fragmentManager.beginTransaction().remove(newsFragment).commit();
            newsFragment = null;
        }
        if (categoryFragment != null) {
            fragmentManager.beginTransaction().remove(categoryFragment).commit();
            categoryFragment = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        firebaseAuth.removeAuthStateListener(authStateListener);
    }


    private void setUpCategoryFrag(){
        categoryFragment = CategoryFragment.newInstance("random", "args");

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryFragment.show(fragmentManager, "Dialog Fragment");
            }
        });

    }


    private void setNewsFragment() {
        newsFragment = NewsFragment.newInstance(AppConstants.mainNewsUrl, "Trending");
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.news_frag_container, newsFragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextListener(
                new SearchView.OnQueryTextListener(){
                    @Override
                    public boolean onQueryTextChange(String newText) {
                        newText = newText.toLowerCase();
                        newsFragment.setSearchResult(newText);

                        return true;
                    }

                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }
                }
        );

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            AuthUI.getInstance().signOut(this);
            return true;
        }
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onNewsFragmentInteractionListener(List<News> newsList, int position, View view) {
        Intent intent = new Intent(this, NewsDetailsActivity.class);

        Bundle args = new Bundle();
        args.putSerializable(AppConstants.NEWS_LIST, (ArrayList)newsList);
        args.putInt(AppConstants.INDEX, position);
        intent.putExtras(args);

        //Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
        startActivity(intent);
    }

    @Override
    public void showFab(boolean toShow) {
        if(toShow){
            fab.show();
        } else {
            fab.hide();
        }
    }

    @Override
    public void onCategoryFragmentInteraction(String category) {
        // Toast.makeText(this, "Category clicked is " + category, Toast.LENGTH_SHORT ).show();
        categoryFragment.dismiss();
        categorySelected = category;
        loadNewsByCategory(category);
    }

    private void loadNewsByCategory(String category){
        newsFragment.loadNewsDataFromUrl(AppConstants.categoryNewsUrl + category);
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        if (!categorySelected.equals(DEFAULT_CAT)) {
            categorySelected = DEFAULT_CAT;
            newsFragment.loadNewsDataFromUrl(AppConstants.mainNewsUrl);
        }else {
            finish();
        }
    }
}
