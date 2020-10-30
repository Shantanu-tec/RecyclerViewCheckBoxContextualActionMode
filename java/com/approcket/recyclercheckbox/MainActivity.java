package com.approcket.recyclercheckbox;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Employee> arrayList=new ArrayList<>();
    ArrayList<Employee> selectionList=new ArrayList<>();
    int counter=0;
    StringBuilder sb=null;
    MyAdapter adapter;
    Toolbar toolbar;
    TextView textView;
    ImageButton bckButton;
    RelativeLayout relativeLayout;
    public boolean inActionMode=false;
    public int position=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        textView=findViewById(R.id.textView);
        textView.setVisibility(View.GONE);
        bckButton=findViewById(R.id.btnBack);
        bckButton.setVisibility(View.GONE);
        relativeLayout=findViewById(R.id.hideontap);
        arrayList.add( new Employee("Nimesh Saini", "Android Developer", R.drawable.appsquadz));
        arrayList.add(new Employee("Niraj Kumar", "Android Developer", R.drawable.appsquadz));
        arrayList.add(new Employee("Sumit Kumar", "Android Developer", R.drawable.appsquadz));
        arrayList.add(new Employee("Priyanshu garg", "Android Developer", R.drawable.appsquadz));
        arrayList.add(new Employee("Deepak Chauhan", "Android Developer", R.drawable.appsquadz));
        arrayList.add( new Employee("Nimesh Saini", "Android Developer", R.drawable.appsquadz));
        arrayList.add(new Employee("Niraj Kumar", "Android Developer", R.drawable.appsquadz));
        arrayList.add(new Employee("Sumit Kumar", "Android Developer", R.drawable.appsquadz));
        arrayList.add(new Employee("Priyanshu garg", "Android Developer", R.drawable.appsquadz));
        arrayList.add(new Employee("Deepak Chauhan", "Android Developer", R.drawable.appsquadz));
        adapter=new MyAdapter(this,arrayList);

        RecyclerView recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        bckButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearActionMode();
            }
        });
    }

    private void clearActionMode() {
            inActionMode=false;
            textView.setVisibility(View.GONE);
            textView.setText("0 item selected");
            bckButton.setVisibility(View.GONE);
            counter =0;
            selectionList.clear();
            toolbar.getMenu().clear();
            adapter.notifyDataSetChanged();
    }


    public void startSelection(int index) {
        if(!inActionMode){
            inActionMode=true;
            //selectionList.add(arrayList.get(index));
            updateToolbarText(counter);
            //counter++;
            textView.setVisibility(View.VISIBLE);
            bckButton.setVisibility(View.VISIBLE);
            toolbar.inflateMenu(R.menu.menu_action);
            position=index;
            adapter.notifyDataSetChanged();
        }
    }

    private void updateToolbarText(int counter) {
        if(counter==0)
        {
            textView.setText("0 items Selected");
        }
        if (counter==1)
        {
            textView.setText("1 item selected");
        }
        else
        {
            textView.setText(counter+" items selected");
        }
    }

    public void check(View view, int index) {
        if(((CheckBox)view).isChecked()){
            selectionList.add(arrayList.get(index));
            counter++;
            updateToolbarText(counter);
        }
        else {
            selectionList.remove(arrayList.get(index));
            counter--;
            updateToolbarText(counter);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.item_delete && selectionList.size()>0) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Delete " + selectionList.size() + " items?");
            builder.setTitle("Confirm");
            builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    for (Employee employee : selectionList) {
                        arrayList.remove(employee);
                    }
                    updateToolbarText(0);
                    clearActionMode();
                }
            }).setNegativeButton("no", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });

            builder.show();
        }
        return super.onOptionsItemSelected(item);
    }

}
