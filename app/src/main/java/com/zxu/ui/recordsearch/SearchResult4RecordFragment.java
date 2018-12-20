package com.zxu.ui.recordsearch;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.zxu.R;
import com.zxu.model.JC_Record;
import com.zxu.model.JC_RecordSearchResult;
import com.zxu.util.CostEnum;
import com.zxu.util.UtilTools;

import java.math.BigDecimal;
import java.util.List;

public class SearchResult4RecordFragment extends DialogFragment {
    private List<JC_RecordSearchResult> resultList;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.Dialog_FullScreen);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        // 设置位置在底部
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(params);
        // 设置背景透明
        window.setWindowAnimations(R.style.dialogWindowAnim);
        window.setBackgroundDrawableResource(R.color.vifrification);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //
        /* 收支 汇总 */
        BigDecimal inCome = BigDecimal.ZERO;
        BigDecimal spend = BigDecimal.ZERO;
        BigDecimal balance = BigDecimal.ZERO;
        for (JC_RecordSearchResult res : resultList){
            for (JC_Record record : res.getRecords()){
                if (CostEnum.SPEND.code().equals(record.getType())) {
                    spend = spend.add(new BigDecimal(record.getMoney()));
                } else if (CostEnum.INCOME.code().equals(record.getType())) {
                    inCome = inCome.add(new BigDecimal(record.getMoney()));
                }
            }
        }
        balance = inCome.subtract(spend);
        // view
        View view = inflater.inflate(R.layout.record_search_result, null);

        TextView tv_inCome = view.findViewById(R.id.record_search_sum_income_id);
        TextView tv_spend = view.findViewById(R.id.record_search_sum_spend_id);
        TextView tv_balance = view.findViewById(R.id.record_search_sum_balance_id);
        // list
        ExpandableListView ev_list = view.findViewById(R.id.record_search_result_list_id);
        SearchResult4RecordAdapter adapter = new SearchResult4RecordAdapter(getActivity(), resultList);
        ev_list.setAdapter(adapter);
        //
        tv_inCome.setText(UtilTools.format(inCome));
        tv_spend.setText(UtilTools.format(spend));
        tv_balance.setText(UtilTools.format(balance));
        return view;
    }

    /*
     * */

    public void setResultList(List<JC_RecordSearchResult> resultList) {
        this.resultList = resultList;
    }
}
