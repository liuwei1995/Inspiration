package com.xinaliu.inspiration;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.xinaliu.inspiration.http.util.HttpHelper;
import com.xinaliu.inspiration.http.util.ICallback;
import com.xinaliu.inspiration.util.LogUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Map<String, Object> map = new HashMap<>();
                map.put("_app_id","hottopic");
                map.put("_fetch","1");
                map.put("_fetch_incrs","1");
                map.put("_sort","_lists.score:desc");
                map.put("_lists","_lists.list_id:\"index_promotion\"");
                String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                map.put("_objects","active_time:[* TO "+format+"]");
//                map.put("_objects","active_time:[* TO \"2017-8-18 09:26:00\"]");
                map.put("_select","url,title,cover,tag,desc,comment_total,incrs_flag,list_name,post_type");
                String ip = "http://napi.uc.cn/3/classes/topic/search";
//                http://napi.uc.cn/3/classes/topic/search?_app_id=hottopic&_fetch=1&_fetch_incrs=1&_sort=_lists.score%3Adesc&_lists=_lists.list_id%3A%22index_promotion%22&_objects=active_time%3A%5B*+TO+%222017-8-18+10%3A42%3A00%22%5D&_select=url%2Ctitle%2Ccover%2Ctag%2Cdesc%2Ccomment_total%2Cincrs_flag%2Clist_name%2Cpost_type
                String url = "http://napi.uc.cn/3/classes/topic/search?_app_id=hottopic&_fetch=1&_fetch_incrs=1&_sort=_lists.score%3Adesc&_lists=_lists.list_id%3A%22index_promotion%22&_objects=active_time%3A%5B*+TO+%222017-8-18+10%3A42%3A00%22%5D&_select=url%2Ctitle%2Ccover%2Ctag%2Cdesc%2Ccomment_total%2Cincrs_flag%2Clist_name%2Cpost_type";
//                HttpHelper.newInstance().post(ip, map, new ICallback() {
                HttpHelper.newInstance().post(url,new ICallback() {
                    @Override
                    public void onCallbackResult(Boolean isSuccess, String result) {
                        LogUtils.d(result);
                    }
                });
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
