package com.zxu.ui.category;

import com.zxu.application.GaiaApplication;
import com.zxu.base.database.ServiceFactory;
import com.zxu.dao.CategoryDao;
import com.zxu.model.JC_Category;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryBookListPresenter implements CategoryBookListContract.Presenter {
    private GaiaApplication application;
    private ServiceFactory serviceFactory = new ServiceFactory();
    CategoryBookListContract.View cView;

    public CategoryBookListPresenter(GaiaApplication application, CategoryBookListContract.View cView) {
        this.application = application;
        this.cView = cView;
    }

    public CategoryDao categoryDao() {
        return serviceFactory.getService(application, CategoryDao.class);
    }

    @Override
    public void getAll(GaiaApplication application) {
        List<JC_Category> list = categoryDao().getList();
        Map<String, JC_Category> bigs = new HashMap<>();
        Map<String, List<JC_Category>> smalls = new HashMap<>();
        for (JC_Category category : list) {
            if (category.getType() == 0) {
                bigs.put(category.getId(), category);
            } else {
                if (smalls.containsKey(category.getFatherId())) {
                    smalls.get(category.getFatherId()).add(category);
                } else {
                    List<JC_Category> li = new ArrayList<>();
                    li.add(category);
                    smalls.put(category.getFatherId(), li);
                }
            }
        }
        // assemble
        list.clear();
        for (String key : bigs.keySet()){
            JC_Category category = bigs.get(key);
            List<JC_Category> ss = smalls.get(key);
            if (ss == null){
                ss = new ArrayList<>();
            }
            category.setChilds(ss);
            list.add(category);
        }
        cView.setCategorys(list);
    }
}
