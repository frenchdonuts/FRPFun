package com.example.frenchdonuts.frpfun;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.frenchdonuts.frpfun.com.example.frenchdonuts.frpfun.twittermodels.Status;
import com.example.frenchdonuts.frpfun.com.example.frenchdonuts.frpfun.twittermodels.StatusItem;
import com.example.frenchdonuts.frpfun.com.example.frenchdonuts.frpfun.twittermodels.TwitterSearchResponse;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;
import rx.Observer;


public class FRPFunActivity extends ActionBarActivity implements ActionBar.TabListener {
    public static String CONSUMER_KEY_AND_SECRET = "lZMSAZj6e7GrSnXcGvnybdwOH:0glwySG0OouAjiBi21ckRkyW2VWIWnXjAY1m7JLffli4i5G7Ua";
    public static String CONSUMER_KEY_AND_SECRET_64 = "";
    public static String ACCESS_TOKEN = "";
    public static SharedPreferences preferences;
    static float scale;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    rxSectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frpfun);
        scale = getResources().getDisplayMetrics().density;
        CONSUMER_KEY_AND_SECRET_64 = Base64.encodeToString(CONSUMER_KEY_AND_SECRET.getBytes(), Base64.NO_WRAP);

        // Set up the action bar.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        preferences = getPreferences(Context.MODE_PRIVATE);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new rxSectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
		mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

			}

			@Override
			public void onPageSelected(int position) {
				actionBar.selectTab(actionBar.getTabAt(position));
			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});


        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }

        ApiManager.getBearerToken().subscribe(mSectionsPagerAdapter);

    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }
    public static int scale(int pixels) {
        return (int) (pixels * scale + 0.5f);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.frpfun, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class rxSectionsPagerAdapter extends FragmentPagerAdapter  implements Observer<BearerToken> {
        List<WeakReference<rxTwitterListFragment>> fragmentReferences;
        String[] fromQueries = {"from:mashable", "from:lifehacker", "from:nytimes"};

        public rxSectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            fragmentReferences = new ArrayList<>();
        }

        public void subscribeFragments() {
            for(WeakReference<rxTwitterListFragment> ref : fragmentReferences) {
                rxTwitterListFragment fragment = ref.get();
                if(fragment != null) {
                    fragment.subscribe();
                }
            }
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return an rxTwitterListFragment (defined as a static inner class below).

            rxTwitterListFragment fragment = rxTwitterListFragment.newInstance(fromQueries[position]);
            fragmentReferences.add(new WeakReference<>(fragment));

            // Only subscribe fragments if accessToken is available
            if(ACCESS_TOKEN != "")
                fragment.subscribe();

            return fragment;
        }


        @Override
        public void onNext(BearerToken token) {
            ACCESS_TOKEN = token.accessToken;
        }

        @Override
        public void onCompleted() {
            // Subscribe all CURRENTLY instantiated fragments to Twitter search query
            mSectionsPagerAdapter.subscribeFragments();

        }

        @Override
        public void onError(Throwable throwable) {

        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            return fromQueries[position];
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class rxTwitterListFragment extends ListFragment implements Observer<TwitterSearchResponse> {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        public String FROM_QUERY = "";
        public boolean subscribed = false;

        private LayoutInflater inflater;

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static rxTwitterListFragment newInstance(String twitterSearchQuery) {
            rxTwitterListFragment fragment = new rxTwitterListFragment();
            fragment.FROM_QUERY = twitterSearchQuery;
            return fragment;
        }

        public rxTwitterListFragment() {

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            this.inflater = inflater;
            return inflater.inflate(R.layout.fragment_frpfun, container, false);
        }

        public void subscribe() {
            if(!this.subscribed) {
                ApiManager.getTweets(this.FROM_QUERY).subscribe(this);
                this.subscribed = true;
            }
        }

        @Override
        public void onCompleted() {

        }

        @Override
        public void onNext(TwitterSearchResponse twitterSearchResponse) {
            this.setListAdapter(new TwitterArrayAdapter(this.getActivity(), 0, twitterSearchResponse.statuses));

        }

        @Override
        public void onError(Throwable throwable) {
            Log.i("rxTwitterFragment, onError", throwable.getMessage());
        }

        private class TwitterArrayAdapter extends ArrayAdapter<Status> {
            public TwitterArrayAdapter(Context context, int resource, List<Status> statuses) {
                super(context, resource, statuses);
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                StatusItem rowView = new StatusItem(inflater, this.getContext());
                rowView.bindDataToView(this.getItem(position));
                return rowView;
            }
        }

    }

}

