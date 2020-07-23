package sg.edu.rp.c347.id18016094.knowyourfacts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ArrayList<Fragment> al;
    FragmentPager adapter;
    ViewPager pager;
    int reqCode = 12345;
    Button btnLater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLater = findViewById(R.id.buttonSendLater);
        pager = findViewById(R.id.viewpager1);


        FragmentManager fm = getSupportFragmentManager();
        al = new ArrayList<Fragment>();
        al.add(new Frag1());
        al.add(new Frag2());

        adapter = new FragmentPager(fm, al);
        pager.setAdapter(adapter);

        btnLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.MINUTE, 5);
                Intent intent = new Intent(MainActivity.this,
                        ScheduledNotificationReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        MainActivity.this, reqCode,
                        intent, PendingIntent.FLAG_CANCEL_CURRENT);
                AlarmManager am = (AlarmManager)
                        getSystemService(Activity.ALARM_SERVICE);
                am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
                        pendingIntent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_next:
                int max = pager.getChildCount();
                if (pager.getCurrentItem() < max - 1) {
                    int nextPage = pager.getCurrentItem() + 1;
                    pager.setCurrentItem(nextPage, true);
                    return (true);
                }
            case R.id.action_previous:
                if (pager.getCurrentItem() > 0) {
                    int previousPage = pager.getCurrentItem() - 1;
                    pager.setCurrentItem(previousPage, true);
                }
            case R.id.action_random:
                // create random object
                Random randomize = new Random();
                // check next int value
                pager.setCurrentItem(randomize.nextInt(), true);
        }
        return(super.onOptionsItemSelected(item));
    }
}