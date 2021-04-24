package converter;

import cz.muni.fi.pa165.service.config.ServiceConfig;
import cz.muni.fi.pa165.service.converter.BeanConverter;
import lombok.val;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.inject.Inject;


/**
 * @author juraj
 */
@ContextConfiguration(classes = ServiceConfig.class)
public class BeanConverterTest extends AbstractTestNGSpringContextTests {

    @Inject
    BeanConverter converter;

    @Test
    public void testCovnersion() {
        Assert.assertNotNull(converter);
        val a = new A(1, "a");
        val b = new B(1, "a");
        val c = new C(1, "a", "c");
        val a2 = new A(1, "a");

        val b2 = converter.convert(a, B.class);
        Assert.assertEquals(b2, b);
    }


}
