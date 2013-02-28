package com.odea;

import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * User: pbergonzi
 * Date: 10/10/12
 * Time: 13:04
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public abstract class AbstractTestCase {
    protected WicketTester tester;
    @Autowired
    protected ApplicationContext applicationContext;

    @Before
    public void setUp() {
        this.tester = new WicketTester();
        SpringComponentInjector springComponentInjector = new SpringComponentInjector(this.tester.getApplication(), this.applicationContext);
        this.tester.getApplication().getComponentInstantiationListeners().add(springComponentInjector);
    }
}