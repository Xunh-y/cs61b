package hw2;

import org.junit.Assert;
import org.junit.Test;

public class test {
    @Test
    public void testPer() {
//        Percolation p = new Percolation(5);
//        p.open(3, 3);
//        Assert.assertTrue(p.isOpen(3, 3));
//        Assert.assertFalse(p.isOpen(3, 2));
//        p.open(4, 4);
//        p.open(4, 2);
//        Assert.assertFalse(p.percolates());
//        Assert.assertEquals(3, p.numberOfOpenSites());
//        p.open(2, 3);
//        p.open(1, 3);
//        p.open(0, 3);
//        Assert.assertFalse(p.percolates());
//        Assert.assertTrue(p.isFull(3, 3));
//        Assert.assertFalse(p.isFull(4, 2));
//        p.open(4, 3);
//        Assert.assertTrue(p.percolates());
        Percolation p = new Percolation(3);
        p.open(0,0);
        p.open(1,0);
        p.open(2,0);
        Assert.assertTrue(p.percolates());
        p.open(1, 2);
        Assert.assertFalse(p.isFull(1, 2));
        p.open(2, 2);
        Assert.assertFalse(p.isFull(1, 2));
        Assert.assertFalse(p.isFull(0, 2));
    }
}
