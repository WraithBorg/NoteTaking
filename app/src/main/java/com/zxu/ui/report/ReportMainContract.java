package com.zxu.ui.report;

import com.zxu.application.BaseView;
import com.zxu.model.JC_Record;

import java.util.List;

public class ReportMainContract {
    interface Presenter {
        void getTodayRecords(String accountId);

    }

    interface View extends BaseView<ReportMainContract.Presenter> {
        void setTodayRecords(List<JC_Record> records);

    }
}
