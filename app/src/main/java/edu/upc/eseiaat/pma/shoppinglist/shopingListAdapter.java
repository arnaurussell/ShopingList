package edu.upc.eseiaat.pma.shoppinglist;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by arnau on 23/10/2017.
 */

public class shopingListAdapter extends ArrayAdapter<ShoppingItem> {

    public shopingListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View result = convertView;
        if (result == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            result = inflater.inflate(R.layout.shoping_item, null);

        }

        CheckBox CheckBox = (CheckBox) result.findViewById(R.id.Shopping_item);
        ShoppingItem item = getItem(position);
        CheckBox.setText(item.getText());
        CheckBox.setChecked(item.isCheck());
        return  result;

    }
}
