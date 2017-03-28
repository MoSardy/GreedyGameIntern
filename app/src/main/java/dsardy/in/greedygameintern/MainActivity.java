package dsardy.in.greedygameintern;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    RecyclerView recyclerView;
    DataAdapter adapter;
    TextView msg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText)findViewById(R.id.editTextapp);
        msg = (TextView)findViewById(R.id.textViewMsg);
        recyclerView = (RecyclerView) findViewById(R.id.rview);


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,4));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //getting list of all installed apps, a list of ResolveInfo objects
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        final List<ResolveInfo> pkgAppsList = getApplicationContext().getPackageManager().queryIntentActivities( mainIntent, 0);



        //with this we can eliminate the search button, and this is realtime so better(for our case)
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                String textinput = charSequence.toString().toLowerCase();
                final List<ResolveInfo> filteredlist = new ArrayList<>();

                for (int ii = 0; i < pkgAppsList.size(); i++) {

                    final String text = pkgAppsList.get(i).loadLabel(getPackageManager()).toString().toLowerCase();
                    if (text.contains(textinput)) {

                        filteredlist.add(pkgAppsList.get(i));
                    }
                }

                if(filteredlist.isEmpty()){

                    msg.setVisibility(View.VISIBLE);
                    msg.setText(R.string.msg_not_found);
                    adapter = new DataAdapter(filteredlist,getApplicationContext(),textinput);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();


                }else {

                    msg.setVisibility(View.GONE);
                    adapter = new DataAdapter(filteredlist,getApplicationContext(), textinput);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                }



            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }




}
