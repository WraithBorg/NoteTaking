package com.zxu.ui.record;

import com.zxu.application.BaseView;
import com.zxu.model.JC_Record;

import java.util.List;

public class ListRecordContract {
    interface Presenter {
        void getRecords(String accountId,String period);
    }

    interface View extends BaseView<ListRecordContract.Presenter> {
        void setRecords(List<JC_Record> records);

    }
}
