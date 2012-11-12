package org.pan.voucher.test;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author Pance.Isajeski
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "/voucher-spring.xml",
        "/voucher-spring-db.xml",
        "/voucher-spring-dao.xml",
        "/voucher-spring-remote.xml",
        "/voucher-spring-service.xml",
        "/voucher-spring-sms.xml"
    })
public abstract class BaseTestCase extends AbstractJUnit4SpringContextTests {

}
