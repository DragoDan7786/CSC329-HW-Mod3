/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.farmingdale.m03linkedlist;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author gerstl
 */
public class IteratorRemoveTest implements RunTest {

    @Override
    public String runTest() {
        // Note: This test is extra credit (10 pts)
        DLList<String> dl = new DLList<>();
        ArrayList<String> al = new ArrayList<>();
        dl.append("A");
        al.add("A");
        dl.append("B");
        al.add("B");
        dl.append("C");
        al.add("C");
        dl.append("D");
        al.add("D");
        dl.append("E");
        al.add("E");
        dl.append("F");
        al.add("F");
        dl.append("G");
        al.add("G");
        dl.append("H");
        al.add("H");
        dl.append("I");
        al.add("I");
        if (!dl.sameContentsAs(al)) {
            return "Failed at A0001";
        }
        Iterator<String> theIter = dl.iterator();
        theIter.next();
        theIter.next();
        theIter.next();
        Iterator<String> otherIter = al.iterator();
        otherIter.next();
        otherIter.next();
        otherIter.next();
        // theIter should point to "C"
        String myCurrent = theIter.next();
        String otherCurrent = otherIter.next();
        if (!myCurrent.equals(otherCurrent)) {
            return "Failed at A0002";
        }
        System.out.println("At Location 1, ArrayList is " + al + " and dl is "
                + dl + " and the iterators just pointed to " + myCurrent);
        otherIter.remove();
        System.out.println("At Location 2, ArrayList is " + al + " and dl is " + dl);
        if (dl.sameContentsAs(al)) {
            return "Failed at A0003";
        }
        theIter.remove();
        System.out.println("At Location 3, ArrayList is " + al + " and dl is " + dl);
        if (!dl.sameContentsAs(al)) {
            return "Failed at A0004";
        }
        // now check that next() still works after removal
        myCurrent = theIter.next();
        otherCurrent = otherIter.next();
        if (!myCurrent.equals(otherCurrent)) {
            return "Failed at A0005";
        }
        if (!dl.sameContentsAs(al)) {
            return "Failed at A0006";
        }
        System.out.println("At Location 4, ArrayList is " + al + " and dl is "
                + dl + " and the iterators just pointed to " + myCurrent);
        // now remove the first item
        theIter = dl.iterator();
        otherIter = al.iterator();
        if (!theIter.hasNext()) {
            return "Failed at A0007";
        }
        if (!otherIter.hasNext()) {
            return "Failed at A0008";
        }
        theIter.next();
        theIter.remove();
        otherIter.next();
        otherIter.remove();
        System.out.println("At Location 5, ArrayList is " + al + " and dl is " + dl);
        if (!dl.sameContentsAs(al)) {
            return "Failed at A0009";
        }
        // now check that next() still works after removal
        myCurrent = theIter.next();
        otherCurrent = otherIter.next();
        if (!myCurrent.equals(otherCurrent)) {
            return "Failed at A0010";
        }
        if (!dl.sameContentsAs(al)) {
            return "Failed at A0011";
        }
        return "";
    }
    @Override public String getTestName(){
        return "Iterator Remove Test (EC)";
    }
}
