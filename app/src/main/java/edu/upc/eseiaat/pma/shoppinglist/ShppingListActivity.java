package edu.upc.eseiaat.pma.shoppinglist;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ShppingListActivity extends AppCompatActivity {

    private static final String FILNAME = "shopping_list.txt ";
    private static final int MAX_BYTES = 8000;

    private ArrayList<ShoppingItem> itemList;
    private shopingListAdapter adapter;


    private ListView list;
    private Button btn_add;
    private EditText edit_item;

    private void writeItemList(){


        try {

            FileOutputStream fos = openFileOutput(FILNAME, Context.MODE_PRIVATE);
            for (int i = 0; i<itemList.size(); i++) {
                ShoppingItem it = itemList.get(i);
                String line = String.format("%s;%d\n", it.getText(), it.isCheck());
                fos.write(line.getBytes());

            }
            fos.close();
        } catch (FileNotFoundException e) {
            Toast.makeText(this, R.string.cannot_write,Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, R.string.cannot_write,Toast.LENGTH_SHORT).show();

        }


    }

    private void readItemList(){
        itemList = new ArrayList<>();
        try {
            FileInputStream fis = openFileInput(FILNAME);
            byte[] buffer = new byte[MAX_BYTES];
            int nread = fis.read(buffer);
            String content = new String(buffer, 0, nread);
            String[] lines = content.split("\n");
            for (String line : lines) {
                String[] parts = line.split(";");
                itemList.add(new ShoppingItem(parts[0], parts[1].equals("true")));
            }
            fis.close();



        } catch (FileNotFoundException e) {

        } catch (IOException e) {
            Toast.makeText(this, R.string.cannot_read ,Toast.LENGTH_SHORT).show();

        }


    }

    @Override
    protected void onStop() {
        super.onStop();
        writeItemList();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shpping_list);

        list = (ListView) findViewById(R.id.list);
        btn_add = (Button) findViewById(R.id.btn_add);
        edit_item = (EditText) findViewById(R.id.edit_item);

        readItemList();

        adapter = new shopingListAdapter(this,android.R.layout.simple_list_item_1,itemList);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem();

            }

            private void addItem() {
                String item_text = edit_item.getText().toString();
                if (!item_text.isEmpty()){
                    itemList.add(new ShoppingItem(item_text));
                    adapter.notifyDataSetChanged();
                    edit_item.setText("");}
                list.smoothScrollToPosition(itemList.size()-1);

            }
        });

        edit_item.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                addItem();
                return true;
            }

            private void addItem() {
                String item_text = edit_item.getText().toString();
                if (!item_text.isEmpty()){
                    itemList.add(new ShoppingItem(item_text));
                    adapter.notifyDataSetChanged();
                    edit_item.setText("");}
                list.smoothScrollToPosition(itemList.size()-1);

            }
        });

        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
               // ShoppingItem item = itemList.get(pos);
                //boolean checked = item.isCheck();
                //itemList.get(pos).setCheck(!checked);
                itemList.get(pos).toggleChecked();
                adapter.notifyDataSetChanged();

            }
        });

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> list, View item, int pos, long id) {
                maybeRemoveItem(pos);
                return true;
            }
        });


    }

    private void maybeRemoveItem(final int pos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String fmt = getResources().getString(R.string.confirm_mesage);
        builder.setTitle(R.string.confirm);
        builder.setMessage(String.format(fmt, itemList.get(pos).getText()));
        builder.setPositiveButton(R.string.remove, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                itemList.remove(pos);
                adapter.notifyDataSetChanged();

            }
        });

        builder.setNegativeButton(android.R.string.cancel, null);
        builder.create().show();


    }


};



