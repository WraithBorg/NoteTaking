package com.zxu.ui.report;

import com.zxu.application.BaseView;

public class TodayReportMainContract {
    interface Presenter {
        void getTodayRecords();

    }

    interface View extends BaseView<TodayReportMainContract.Presenter> {
        void showError();
    }
}
