package com.dj.boot;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BootDemoApplication.class)
//@TestPropertySource(locations = "classpath:system.properties")
public abstract class BootDemoApplicationTests {

    protected static final Logger logger = LoggerFactory.getLogger(BootDemoApplicationTests.class);

    @Test
    public  void contextLoads() {
        logger.error("contextLoads:{}", "contextLoads");
    }
    /**
     * run void
     */
    @Test
    public abstract void run() throws Exception;

    @Before
    public void init() {
        logger.error("-----------------      开始测试   -----------------");
    }

    @After
    public void after() {
        logger.error("-----------------      测试结束   -----------------");
    }


}
