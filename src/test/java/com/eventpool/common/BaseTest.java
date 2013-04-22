package com.eventpool.common;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/META-INF/eventpool/application*.xml"})
public abstract class BaseTest {

	protected static final Logger log = LoggerFactory.getLogger(BaseTest.class);
	
	@BeforeClass
	public static void beforeClass() {
		String profile = "dev";
		System.setProperty("profile", profile);
		log.info("Setting Profile to  :{}", profile);
	}

}
