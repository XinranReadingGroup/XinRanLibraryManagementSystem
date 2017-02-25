package com.xinran.boot;

import com.xinran.event.impl.BorrowBookEvent;
import com.xinran.event.impl.ReturnBookEvent;
import com.xinran.listener.impl.DonateOrShareBookListener;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinran.event.impl.BookOnStockEvent;
import com.xinran.event.util.EventListenerSupport;
import com.xinran.listener.Listener;
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
        // TUNE use sping configurable,http://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle/
        Listener bean ;

        bean = (Listener) contextUtil.getBean("donateOrShareBookListener");
        EventListenerSupport.addListener(BookOnStockEvent.class, bean);

        bean = (Listener) contextUtil.getBean("borrowBookListener");
        EventListenerSupport.addListener(BorrowBookEvent.class, bean);

        bean = (Listener) contextUtil.getBean("returnBookListener");
        EventListenerSupport.addListener(ReturnBookEvent.class, bean);

//        EventListenerSupport.addListener(BookOnStockEvent.class, new DonateOrShareBookListener());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        init();
    }

}
