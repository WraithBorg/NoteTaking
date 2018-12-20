package com.zxu.ui.recordsearch;

import com.zxu.application.BaseView;
import com.zxu.model.JC_RecordSearchQuery;
import com.zxu.model.JC_RecordSearchResult;

import java.util.List;

public class SearchRecordContract {
    interface Persenter {
        void getRecords(JC_RecordSearchQuery recordQuery);
    }

    interface View extends BaseView<SearchRecordContract.Persenter> {
        void setRecords(List<JC_RecordSearchResult> list);
    }
}
