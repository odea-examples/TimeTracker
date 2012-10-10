package com.odea;

import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Simple test using the WicketTester
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml"})
public class TestHomePage
{
	private WicketTester tester;
    @Autowired
    private ApplicationContext applicationContext;

	@Before
	public void setUp()
	{
        this.tester = new WicketTester();
        SpringComponentInjector springComponentInjector = new SpringComponentInjector(this.tester.getApplication(), this.applicationContext);
        this.tester.getApplication().getComponentInstantiationListeners().add(springComponentInjector);
	}

	@Test
	public void homepageRendersSuccessfully()
	{
		//start and render the test page
		tester.startPage(HomePage.class);

		//assert rendered page class
		tester.assertRenderedPage(HomePage.class);
	}
}
