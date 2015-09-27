package org.ligi.meteredisonandroidfrontend;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import okio.BufferedSource;
import okio.ByteString;
import okio.Okio;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.chart)
    LineChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
       */

        final List<Entry> lineDataEntryList = new ArrayList<>();
        final ArrayList<String> xVals = new ArrayList<>();

        try {
            final InputStream dataInputStream = getAssets().open("wash.meter");

             BufferedSource dataSource = Okio.buffer(Okio.source(dataInputStream));

            final String s = dataSource.readString(Charset.defaultCharset());

            Log.i("", s);
            final String[] split = s.split("\n");

            int i=0;
            for (final String s1 : split) {

                try {
                    final String[] split1 = s1.split(" ");
                    String timeString = split1[0];

                    lineDataEntryList.add(new Entry(Float.parseFloat(split1[1]), i++));
                    xVals.add(timeString);
                } catch (Exception e) {

                }
            }
            /*
            while (!dataSource.exhausted()) {
                //final long timestamp = dataSource.readLong();
                final String timeStampString = dataSource.readString(dataSource.indexOf((byte) ' '), Charset.defaultCharset());
                Log.i("", timeStampString);
                dataSource.skip(1);
                final String data = dataSource.readString(dataSource.indexOf((byte) '\n'), Charset.defaultCharset());
                final ByteString byteString = dataSource.readByteString();
                Log.i("", "" + byteString);
                dataSource.skip(1);
            }
*/



        } catch (IOException e) {
            e.printStackTrace();
        }

        ButterKnife.bind(this);


        final LineDataSet washing = new LineDataSet(lineDataEntryList, "washing");
        washing.setDrawCubic(true);

        washing.setColor(Color.BLUE);

        final LineData lineData = new LineData(xVals, washing);
        lineData.setDrawValues(false);

        chart.setData(lineData);
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
