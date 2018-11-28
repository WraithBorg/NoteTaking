package com.zxu.ui.record;

import com.zxu.application.BaseView;
import com.zxu.model.JC_Record;

public interface RecordAddContract {
    interface Presenter {
        void addRecord(JC_Record record);
    }
    interface View extends BaseView<Presenter> {

    }
}
