package com.zxu.ui.report;

import com.zxu.application.BaseView;
import com.zxu.model.JC_Record;

import java.util.List;

public class TodayReportMainContract {
    interface Presenter {
        void getTodayRecords();

    }

    interface View extends BaseView<TodayReportMainContract.Presenter> {
        void setTodayRecords(List<JC_Record> records);

    }
}
