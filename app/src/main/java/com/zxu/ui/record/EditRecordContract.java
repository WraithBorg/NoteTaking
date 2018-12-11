package com.zxu.ui.record;

import com.zxu.application.BaseView;
import com.zxu.model.JC_Record;

public interface EditRecordContract {
    interface Presenter {
        void editRecord(JC_Record record);

        void delete(JC_Record mRecord);
    }

    interface View extends BaseView<Presenter> {

    }
}
