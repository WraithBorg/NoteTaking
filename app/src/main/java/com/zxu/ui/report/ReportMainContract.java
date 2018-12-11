package com.zxu.ui.report;

import com.zxu.application.BaseView;
import com.zxu.model.JC_Record;

import java.util.List;

public class ReportMainContract {
    interface Presenter {
        void getRecords(String accountId,String period);

    }

    interface View extends BaseView<ReportMainContract.Presenter> {
        void setRecords(List<JC_Record> records);

    }
}
