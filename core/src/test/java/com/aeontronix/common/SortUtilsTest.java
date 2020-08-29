package com.aeontronix.common;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.*;

public class SortUtilsTest {
    @Test
    public void testTSortSuccessful() throws Exception {
        TSortObject seven = new TSortObject(7);
        TSortObject five = new TSortObject(5);
        TSortObject three = new TSortObject(3);
        TSortObject eleven = new TSortObject(11);
        TSortObject eight = new TSortObject(8);
        TSortObject two = new TSortObject(2);
        TSortObject nine = new TSortObject(9);
        TSortObject ten = new TSortObject(10);
        seven.dependencies.add(eleven);
        seven.dependencies.add(eight);
        five.dependencies.add(eleven);
        three.dependencies.add(eight);
        three.dependencies.add(ten);
        eleven.dependencies.add(two);
        eleven.dependencies.add(nine);
        eleven.dependencies.add(ten);
        eight.dependencies.add(nine);
        eight.dependencies.add(ten);
        HashSet<TSortObject> list = new HashSet<TSortObject>(Arrays.asList(seven, five, three, eleven, eight, two, nine, ten));
        List<TSortObject> sortedList = tsort(list);
        Assert.assertEquals(sortedList.size(), 8);
        Assert.assertTrue(sortedList.indexOf(seven) > sortedList.indexOf(eleven));
        Assert.assertTrue(sortedList.indexOf(seven) > sortedList.indexOf(eight));
        Assert.assertTrue(sortedList.indexOf(five) > sortedList.indexOf(eleven));
        Assert.assertTrue(sortedList.indexOf(three) > sortedList.indexOf(eight));
        Assert.assertTrue(sortedList.indexOf(three) > sortedList.indexOf(ten));
        Assert.assertTrue(sortedList.indexOf(eleven) > sortedList.indexOf(two));
        Assert.assertTrue(sortedList.indexOf(eleven) > sortedList.indexOf(nine));
        Assert.assertTrue(sortedList.indexOf(eleven) > sortedList.indexOf(ten));
        Assert.assertTrue(sortedList.indexOf(eight) > sortedList.indexOf(nine));
        Assert.assertTrue(sortedList.indexOf(eight) > sortedList.indexOf(ten));
    }

    @Test(expectedExceptions = CircularDependencyException.class)
    public void testTSortCircular() throws Exception {
        // circular dependency  5 -> [ 7 -> 11 -> 2 -> 7 ]
        TSortObject seven = new TSortObject(7);
        TSortObject five = new TSortObject(5);
        TSortObject three = new TSortObject(3);
        TSortObject eleven = new TSortObject(11);
        TSortObject eight = new TSortObject(8);
        TSortObject two = new TSortObject(2);
        TSortObject nine = new TSortObject(9);
        TSortObject ten = new TSortObject(10);
        seven.dependencies.add(eleven);
        seven.dependencies.add(eight);
        five.dependencies.add(eleven);
        three.dependencies.add(eight);
        three.dependencies.add(ten);
        eleven.dependencies.add(two);
        eleven.dependencies.add(nine);
        eleven.dependencies.add(ten);
        eleven.dependencies.add(two);
        eight.dependencies.add(nine);
        eight.dependencies.add(ten);
        two.dependencies.add(seven);
        five.dependencies.add(seven);
        List<TSortObject> list = new ArrayList<TSortObject>(Arrays.asList(seven, five, three, eleven, eight, two, nine, ten));
        tsort(list);
        // Circular dependency: DataFile[7] -> DataFile[11] -> DataFile[2] -> DataFile[7]
    }

    public static class TSortObject {
        int nb;
        HashSet<TSortObject> dependencies = new HashSet<TSortObject>();
        TSortObject(int nb) {
            this.nb = nb;
        }
    }

    private List<TSortObject> tsort(Collection<TSortObject> list) throws CircularDependencyException {
        return SortUtils.topologicalSort(list, new TopologicalSortComparator<TSortObject>() {
            @Override
            public SortUtils.TopologicalSortRelationship getRelationship(TSortObject source, TSortObject target) {
                Assert.assertNotEquals(source,target,"TSort shouldn't compare an object to itself ?!");
                if (source.dependencies.contains(target)) {
                    return SortUtils.TopologicalSortRelationship.STRONG;
                } else {
                    return SortUtils.TopologicalSortRelationship.NONE;
                }
            }

            @Override
            public String getObjectRepresentation(TSortObject object) {
                return Integer.toString(object.nb);
            }
        });
    }
}
