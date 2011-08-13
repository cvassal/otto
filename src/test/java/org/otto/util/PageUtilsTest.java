package org.otto.util;

import junit.framework.Assert;
import org.junit.Test;

/**
 * @author damien bourdette <a href="https://github.com/dbourdette">dbourdette on github</a>
 * @version \$Revision$
 */
public class PageUtilsTest {
    @Test
    public void getPageCount() throws Exception {
        Assert.assertEquals(1, PageUtils.getPageCount(-2, 10));
        Assert.assertEquals(1, PageUtils.getPageCount(0, 10));
        Assert.assertEquals(1, PageUtils.getPageCount(3, 10));
        Assert.assertEquals(1, PageUtils.getPageCount(10, 10));
        Assert.assertEquals(2, PageUtils.getPageCount(12, 10));
        Assert.assertEquals(2, PageUtils.getPageCount(20, 10));
        Assert.assertEquals(3, PageUtils.getPageCount(25, 10));
    }
}
