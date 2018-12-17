package com.zxu.ui.record;

import com.zxu.application.BaseView;
import com.zxu.model.JC_Record;
import com.zxu.model.JC_RecordSumWeek;

import java.util.List;

public class ListRecordContract {
    interface Presenter {
        void getRecords(String accountId, String period);

        void getRecordSumByWeek(String accountId, String period);
    }

    interface View extends BaseView<ListRecordContract.Presenter> {
        void setRecords(List<JC_Record> records);

        void setRecordSumByWeek(List<JC_RecordSumWeek> records);

    }
}
