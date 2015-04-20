package com.xinran.boot;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinran.event.impl.BookOnStockEvent;
import com.xinran.event.util.EventListenerSupport;
import com.xinran.listener.Listener;
import com.xinran.listener.impl.DonateOrShareBookAddScoreListerner;
import com.xinran.util.SpringContextUtil;

/**
 * @author 高海军 帝奇 Apr 19, 2015 12:37:14 PM
 */
@Service
public class ApplicationBootstrap implements InitializingBean {

    @Autowired
    private SpringContextUtil contextUtil;

    // @PostConstruct
    public void init() {
        Listener bean = (Listener) contextUtil.getBean("donateOrShareBookAddScoreListerner");
        EventListenerSupport.addListener(BookOnStockEvent.class, bean);

        // EventListenerSupport.addListener(BookOnStockEvent.class, new DonateOrShareBookAddScoreListerner());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        init();
    }

}
