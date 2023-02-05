package edu.northeastern.numad23sp_shambhavikul;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class LinkCollectorActivity extends AppCompatActivity {

    private ArrayList<LinkCard> linkCardList;
    private AlertDialog inputAlertDialog;

    private EditText linkNameInput;
    private RecyclerView recyclerView;
    private LinkViewAdapter linkViewAdapter;
    private EditText linkUrlInput;

    private static final String KEY_OF_INSTANCE = "KEY_OF_INSTANCE";
    private static final String NUMBER_OF_ITEMS = "NUMBER_OF_ITEMS";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_collector);
        linkCardList = new ArrayList<>();
        init(savedInstanceState);

        FloatingActionButton addLinkButton = findViewById(R.id.addLinkButton);
        addLinkButton.setOnClickListener(v -> addLink());

        createInputAlertDialog();
        createRecyclerView();
        linkViewAdapter.setOnLinkClickListener(position -> linkCardList.get(position).onLinkCardClicked(this));

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getLayoutPosition();
                linkCardList.remove(position);
                linkViewAdapter.notifyDataSetChanged();
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        int size = linkCardList == null? 0 : linkCardList.size();
        outState.putInt(NUMBER_OF_ITEMS, size);

        //Need to generate unique key for each item
        for(int i=0; i<size; i++){
            outState.putString(KEY_OF_INSTANCE + i+ "0", linkCardList.get(i).getLinkName());
            outState.putString(KEY_OF_INSTANCE + i+ "1", linkCardList.get(i).getLinkUrl());
        }
        super.onSaveInstanceState(outState);
    }

    private void init(Bundle savedInstanceState) {
        initialItemData(savedInstanceState);
        createRecyclerView();
    }

    private void initialItemData(Bundle savedInstanceState){

        if(savedInstanceState != null && savedInstanceState.containsKey(NUMBER_OF_ITEMS)){
            if(linkCardList == null || linkCardList.size() == 0){

                int size = savedInstanceState.getInt(NUMBER_OF_ITEMS);

                for(int i=0; i<size; i++){
                    String name = savedInstanceState.getString(KEY_OF_INSTANCE+i+"0");
                    String url = savedInstanceState.getString(KEY_OF_INSTANCE+i+"1");

                    LinkCard card = new LinkCard(name, url);

                    linkCardList.add(card);

                }
            }
        }
    }

    public void createInputAlertDialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View view = layoutInflater.inflate(R.layout.activity_link_view_holder, null);

        linkNameInput = view.findViewById(R.id.link_name_input);
        linkUrlInput = view.findViewById(R.id.link_url_input);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(view);

        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton(getString(R.string.Add),
                        (dialog, id) -> {
                            LinkCard linkUnit = new LinkCard(linkNameInput.getText().toString(), linkUrlInput.getText().toString());
                            if (linkUnit.isCorrect()) {
                                linkCardList.add(0, linkUnit);
                                linkViewAdapter.notifyDataSetChanged();
                                Snackbar.make(recyclerView, getString(R.string.link_add_success), Snackbar.LENGTH_LONG).show();
                            } else {
                                Snackbar.make(recyclerView, getString(R.string.link_invalid), Snackbar.LENGTH_LONG).show();
                            }
                        })
                .setNegativeButton(getString(R.string.Cancel),
                        (dialog, id) -> dialog.cancel());

        inputAlertDialog = alertDialogBuilder.create();
    }

    public void createRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        linkViewAdapter = new LinkViewAdapter(linkCardList);

        recyclerView.setAdapter(linkViewAdapter);
        recyclerView.setLayoutManager(layoutManager);
    }


    private void addLink() {
        linkNameInput.getText().clear();
        linkUrlInput.setText(getString(R.string.Http));
        linkNameInput.requestFocus();
        inputAlertDialog.show();
    }

}