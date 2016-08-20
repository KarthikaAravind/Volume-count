package com.example.karthika.volumecount;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class Volume_Count extends AppCompatActivity {

    FileOutputStream fileOutputStream;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_volume__count);
        GridView gridView = (GridView) findViewById(R.id.gridView);
        gridView.setAdapter(new ImageAdapter(this));
        String values[] = getIntent().getExtras().getStringArray("details");

        try {

            final File file = new File(values[3]);
            file.createNewFile();
            //final FileOutputStream
            fileOutputStream = new FileOutputStream(file);
            byte loc[] = values[0].getBytes();
            fileOutputStream.write(loc);
            final byte tab[] = "              ".getBytes();
            fileOutputStream.write(tab);
            byte dat[] = values[1].getBytes();
            fileOutputStream.write(dat);
            fileOutputStream.write(tab);
            byte tim[] = values[2].getBytes();
            fileOutputStream.write(tim);
            final byte nl[] = "\n".getBytes();
            fileOutputStream.write(nl);
            fileOutputStream.write(nl);
            byte sl[] = "Sl No:     ".getBytes();
            fileOutputStream.write(sl);
            byte v[] = "Vehicle                   ".getBytes();
            fileOutputStream.write(v);
            //fileOutputStream.write(tab);
            byte ti[] = "Time".getBytes();
            fileOutputStream.write(ti);
            fileOutputStream.write(nl);
            //fileOutputStream.write(nl);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                int count = 0;
                String getname(int val)
                {
                    switch (val)
                    {
                        case 0 : return "Two wheeler      ";
                        case 1 : return "Car                       ";
                        case 2 : return "Bus                      ";
                        case 3 : return "Cycle                   ";
                        case 4 : return "Mini Bus              ";
                        case 5 : return "Mini truck           ";
                        case 6 : return "Handcart                 ";
                        case 7 : return "Truck                    ";
                        case 8 : return "Auto rickshaw    ";
                    }
                    return "error" ;
                }

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //Toast.makeText(second_page.this, "you clicked" + position, Toast.LENGTH_LONG).show();
                    count = count + 1;
                    String vehicle = getname(position);
                    Calendar rightnow = Calendar.getInstance();
                    long offset = rightnow.get(Calendar.ZONE_OFFSET) +rightnow.get(Calendar.DST_OFFSET);
                    long since = (rightnow.getTimeInMillis() + offset) % (24 * 60 * 60 *1000);
                    long s = since / 1000;
                    long mm = s / 60;
                    long h = mm / 60;
                    long hun = (since % 1000 ) / 10;
                    String time_in_hun = Long.toString(h) + ":" + Long.toString(mm % 60) + ":" + Long.toString(s % 60) + ":" + Long.toString(hun);

                    try {
                        byte sl[] = Integer.toString(count).getBytes();
                        fileOutputStream.write(sl);
                        fileOutputStream.write(tab);
                        byte veh[] = vehicle.getBytes();
                        fileOutputStream.write(veh);
                        //fileOutputStream.write(tab);
                        byte time_in_hund[] = time_in_hun.getBytes();
                        fileOutputStream.write(time_in_hund);
                        fileOutputStream.write(nl);

                    } catch (IOException e) {
                        Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                    }

                    Toast.makeText(Volume_Count.this , Integer.toString(count) + "   " + vehicle + time_in_hun, Toast.LENGTH_LONG).show();
                }
            });
        }catch (Exception e){
            Toast.makeText(Volume_Count.this , e.toString(),Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        createMenu(menu);
        return true;
    }

    private void createMenu(Menu menu){
        MenuItem m=menu.add(0,1,1,"EXIT");
        {
            m.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        // if (id == R.id.action_settings) {
        //    return true;
        //}
        try {
            fileOutputStream.close();
            Intent j = new Intent(Intent.ACTION_MAIN);
            j.addCategory(Intent.CATEGORY_HOME);
            j.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(j);
            finish();
            System.exit(0);
        }
        catch (Exception e1) {
            Toast.makeText(getApplicationContext(), e1.toString(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

}
