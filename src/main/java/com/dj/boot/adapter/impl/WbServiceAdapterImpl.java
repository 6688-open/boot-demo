package com.dj.boot.adapter.impl;

import com.dj.boot.adapter.WayBillServiceAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component("wbServiceAdapter")
public class WbServiceAdapterImpl implements WayBillServiceAdapter {
    private static final Logger log = LogManager.getLogger(WbServiceAdapterImpl.class);

    @Override
    public String queryWbMainBySoNo(String soNo) {
        return "WbServiceAdapterImpl";
    }
}
