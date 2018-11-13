package com.zxu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zxu.R;
import com.zxu.model.JC_AccountBook;

import java.util.ArrayList;
import java.util.List;

/**
 * 账本适配器
 */
public class AccountBooksAdapter extends BaseAdapter {
    List<JC_AccountBook> accountBooks;
    Context context;

    public AccountBooksAdapter(Context context, List<JC_AccountBook> accountBooks) {
        this.context = context;
        if (accountBooks == null) {
            accountBooks = new ArrayList<>();
            JC_AccountBook b1 = new JC_AccountBook("1", "Abc");
            JC_AccountBook b2 = new JC_AccountBook("2", "Zxc");
            accountBooks.add(b1);
            accountBooks.add(b2);
        }
        this.accountBooks = accountBooks;
    }

    @Override
    public int getCount() {
        return accountBooks.size();
    }

    @Override
    public JC_AccountBook getItem(int position) {
        return accountBooks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.indexpage_accountbooks_item, parent, false);
            TextView bookNameView = (TextView) view.findViewById(R.id.indexpage_accountbooks_name_id);
            bookNameView.setText(getItem(position).getName());
        }
        return view;
    }
}
