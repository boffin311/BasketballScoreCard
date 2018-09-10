package com.example.ankit.basketballscorecard.activities;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ankit.basketballscorecard.R;

import com.example.ankit.basketballscorecard.database.ChngTheme.ThemeList;



import com.example.ankit.basketballscorecard.database.teamA.MyDataBaseHelper;
import com.example.ankit.basketballscorecard.database.teamA.TeamATable;

import com.example.ankit.basketballscorecard.fragments.FragmentOne;

import com.example.ankit.basketballscorecard.fragments.FragmentThree;
import com.example.ankit.basketballscorecard.fragments.FragmentTwo;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
static ArrayList<Fragment> arrayList=new ArrayList<>();
    SQLiteDatabase db,db2,db3;
   // TabItem tabItem2,tabItem3;
    ArrayList<ThemeList> themeLists;
    EditText etNumber,etAdd;
    int ColorCode;
    TabLayout tabLayout;
    public static final String TAG="MAIN";
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        Log.d("CHK1", "onCreate: ");
        mSectionsPagerAdapter.notifyDataSetChanged();
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
//        tabItem2=findViewById(R.id.tabItem2);
//        tabItem3=findViewById(R.id.tabItem3);
        mViewPager.setAdapter(mSectionsPagerAdapter);
mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        FragmentThree fragmentThree= (FragmentThree) mSectionsPagerAdapter.instantiateItem(mViewPager,0);
        Log.d("CHK1", "onPageSelected: "+position);
        if (fragmentThree!=null)
        {
            fragmentThree.onResume();
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
});

        tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        MyDataBaseHelper myDataBaseHelper=new MyDataBaseHelper(this);
        db = myDataBaseHelper.getWritableDatabase();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id==R.id.TeamName)
        {
            View view=getLayoutInflater().inflate(R.layout.list_of_players_added,null);

            etAdd = view.findViewById(R.id.etAdd);
            etAdd.setHint("Team A");
            etNumber=view.findViewById(R.id.etNumber);
            etNumber.setHint("Team B");

            etNumber = view.findViewById(R.id.etNumber);
          if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP) {
              AlertDialog alert = new AlertDialog.Builder(MainActivity.this)
                      .setTitle("Choose Team Name")
                      .setView(view)
                      .setPositiveButton("Set", new DialogInterface.OnClickListener() {
                          @Override
                          public void onClick(DialogInterface dialog, int which) {

                              tabLayout.getTabAt(1).setText(etAdd.getText().toString());
                              tabLayout.getTabAt(2).setText(etNumber.getText().toString());
                              FragmentThree fragmentThree= (FragmentThree) mSectionsPagerAdapter.instantiateItem(mViewPager,0);
                               if (fragmentThree!=null) fragmentThree.TeamNameUpdate();
                          }
                      })
                      .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                          @Override
                          public void onClick(DialogInterface dialog, int which) {

                          }
                      })
                      .show();
          }
        }
        if (id == R.id.Delete) {
            final String st="Delete From "+TeamATable.TABLE_NAME+";";
            final String st1="Delete From "+TeamATable.TABLE_B_NAME+";";
            final String st2="Delete From "+ TeamATable.CommonTableName+";";

            AlertDialog alert=new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Delete")
                    .setMessage("Do you want Delete the whole score card")
                    .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (TeamATable.readAllB(db).size()!=0||TeamATable.readAll(db).size()!=0) {
                                Toast.makeText(MainActivity.this, " Successfully Deleted", Toast.LENGTH_SHORT).show();
                                db.execSQL(st);
                                db.execSQL(st1);
                                db.execSQL(st2);

                                FragmentOne fragmentOne = (FragmentOne) mSectionsPagerAdapter.instantiateItem(mViewPager, 1);
                                if (fragmentOne != null) fragmentOne.onResume();
                                FragmentTwo fragmentTwo = (FragmentTwo) mSectionsPagerAdapter.instantiateItem(mViewPager, 2);
                                if (fragmentTwo != null) fragmentTwo.onResume();
                            }
                            else
                                Toast.makeText(MainActivity.this, "Nothing to delete", Toast.LENGTH_SHORT).show();
                        }
                   })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setCancelable(false)
                    .show();

            return true;
        }
     if (id==R.id.Reset)
     {
         final String st2="Delete From "+ TeamATable.CommonTableName+";";
         AlertDialog alert=new AlertDialog.Builder(MainActivity.this)
                 .setTitle("Reset")
                 .setMessage("Do you want to reset the score card")
                 .setPositiveButton("Reset", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog, int which) {
                         if (TeamATable.readAllB(db).size()!=0||TeamATable.readAll(db).size()!=0) {
                             db.execSQL(st2);
                             ContentValues values = new ContentValues();
                             values.put(TeamATable.Column.Sum, 0);
                             values.put(TeamATable.Foul, 0);
                             db.update(TeamATable.TABLE_NAME, values, null, null);
                             ContentValues row = new ContentValues();
                             row.put(TeamATable.Column.Sum, 0);
                             row.put(TeamATable.Foul, 0);
                             db.update(TeamATable.TABLE_B_NAME, row, null, null);
                             Toast.makeText(getBaseContext(), " Reset Successful ", Toast.LENGTH_SHORT).show();
                             FragmentOne fragmentOne = (FragmentOne) mSectionsPagerAdapter.instantiateItem(mViewPager, 1);
                             if (fragmentOne != null) fragmentOne.onResume();
                             FragmentTwo fragmentTwo = (FragmentTwo) mSectionsPagerAdapter.instantiateItem(mViewPager, 2);
                             if (fragmentTwo != null) fragmentTwo.onResume();
                         }
                         else
                             Toast.makeText(MainActivity.this, "Nothing to reset", Toast.LENGTH_SHORT).show();
                         }
                 })
                 .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog, int which) {

                     }
                 })
                 .setCancelable(false)
                 .show();
         return true;
             }


     if (id==R.id.ChngTheme) {
         ContentValues contentValues = new ContentValues();
         themeLists = TeamATable.readAllTheme(db);
         if (themeLists.size() == 0) {
             db.execSQL(TeamATable.insert);
         }
         themeLists = TeamATable.readAllTheme(db);
         if (themeLists.get(0).getCode() == 2) {
             contentValues.put(TeamATable.ThemeColumn.Theme, 1);
             db.update(TeamATable.ThemeTableName, contentValues, null, null);
         } else {
             contentValues.put(TeamATable.ThemeColumn.Theme, 2);
             db.update(TeamATable.ThemeTableName, contentValues, null, null);
         }
         FragmentOne fragmentOne = (FragmentOne) mSectionsPagerAdapter.instantiateItem(mViewPager, 1);
         FragmentTwo fragmentTwo = (FragmentTwo) mSectionsPagerAdapter.instantiateItem(mViewPager, 2);
       //  FragmentThree fragmentThree= (FragmentThree) mSectionsPagerAdapter.instantiateItem(mViewPager,0);
         if (fragmentOne != null) fragmentOne.onResume();
         if (fragmentTwo != null) fragmentTwo.onResume();
      //   if (fragmentTwo!=null) fragmentThree.ThemeUpdate();
     }

         return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            arrayList.add(FragmentThree.newInstance(3));

           arrayList.add(FragmentOne.newInstance(0));
               arrayList.add(FragmentTwo.newFragment(1));

           // View view=arrayList.get(position);
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

            Log.d("TAG", "getItem: "+position);
            Log.d("TAG", "getItem: "+arrayList.size());
            return arrayList.get(position);

        }


        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }
}
