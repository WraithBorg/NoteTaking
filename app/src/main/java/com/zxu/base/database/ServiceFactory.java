package com.zxu.base.database;

import com.zxu.application.GaiaApplication;

import java.util.HashMap;
import java.util.Map;

public class ServiceFactory {

    private Map<GaiaApplication, Map<Class<? extends AbstractService>, AbstractService>> serviceMap
            = new HashMap<GaiaApplication, Map<Class<? extends AbstractService>, AbstractService>>();

    public <T extends AbstractService> T getService(GaiaApplication context, Class<T> clazz) {
        try {
            AbstractService service = clazz.newInstance();
            service.init(context);
            service.setServiceFactory(this);
            return (T) service;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
// old <<<<<<
//        Map<Class<? extends AbstractService>, AbstractService> contextServices = serviceMap.get(context);
//        AbstractService service = contextServices.get(clazz);
//        try {
//            if (service == null) {
//                service = clazz.newInstance();
//                service.init(context);
//                service.setServiceFactory(this);
//                contextServices.put(clazz, service);
//            }
//        } catch (Exception e) {
//            return null;
//        }
// old >>>>>>
    }

    /**
     * Activity destory 时调用
     *
     * @param context
     */
// old <<<<<<
//    public void cleanUp(GaiaApplication context) {
//        Map<Class<? extends AbstractService>, AbstractService> contextServices = serviceMap.get(context);
//        if (contextServices != null) {
//            for (AbstractService service : contextServices.values()) {
//                service.finish();
//            }
//        }
//    }
// old >>>>>>
}
