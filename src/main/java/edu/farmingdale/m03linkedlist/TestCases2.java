/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.farmingdale.m03linkedlist;

import java.util.ArrayList;
import java.util.Random;
import java.util.Iterator;

/**
 *
 * @author gerstl
 */
public class TestCases2 implements RunTest {

    enum FUNCTIONS_TO_TEST {
        getItemAt, setDAtaInNodeAt, size, addAt, append, prepend, equals, contains, indexOf, lastIndexOf, remove

    }

    public String runTest() {
        // The public methods in the DLLList class:
        //        public T getItemAt(int position) {
        //        public T setDataInNodeAt(int position, T newData) {
        //        public int getCount() {
        //        public boolean addAt(int position, T addMe) {
        //        public void append(T addMe) {
        //        public void prepend(T addMe) {
        //        boolean equals(ArrayList<T> compareWith) {

        // We'll compare with an ArrayList<>
        ArrayList<String> al = new ArrayList<>();
        DLList<String> dl = new DLList<>();
        final int TEST_SIZE = 10_000;//1_000;
        var random = new Random();
        // this is defined in java, so not necessary to compute, but I do so 
        // you can see how I compute it
        final int MAX_WIDTH = (int) Math.log10(Integer.MAX_VALUE) + 1;
        System.out.println("MAX_WIDTH is " + MAX_WIDTH);
        StringBuilder sb = new StringBuilder();
        final int NUMBER_OF_TEST_TYPES = FUNCTIONS_TO_TEST.values().length;
        int testCount[] = new int[NUMBER_OF_TEST_TYPES];
        for (int i = 0; i < TEST_SIZE; ++i) {
            // statistically, we anticipate that nunbers between 0..test_size
            // should occassionally repeat. 
            // we want to avoid zeros, hense the + 1

            // now pick an operation: 
            int operation = random.nextInt(NUMBER_OF_TEST_TYPES);
            ++testCount[operation];
            switch (operation) {
                case 0: //getItemAt;
                    if (!testGetItemAt(al, dl, random)) {
                        return "Failed at A0400";
                    }
                    break;
                case 1: // getItemAt;
                    if (!testSetDataInNodeAt(al, dl, random, TEST_SIZE, MAX_WIDTH)) {
                        return "Failed at A0401";
                    }
                    break;
                case 2: // size;
                    if (!testSize(al, dl, random)) {
                        return "Failed at A0402";
                    }
                    break;
                case 3: // addAt;
                    if (!testAddAt(al, dl, random, TEST_SIZE, MAX_WIDTH)) {
                        return "Failed at A0403";
                    }
                    break;
                case 4: // append;
                    if (!testAppend(al, dl, random, TEST_SIZE, MAX_WIDTH)) {
                        return "Failed at A0404";
                    }
                    break;
                case 5: // prepend;
                    if (!testPrepend(al, dl, random, TEST_SIZE, MAX_WIDTH)) {
                        return "Failed at A0405";
                    }
                    break;

                case 6: // equals
                    if (!testEquals(al, dl, random)) {
                        return "Failed at A0406";
                    }
                    break;
                case 7:
                    // contains
                    if (!testContains(al, dl, random, TEST_SIZE, MAX_WIDTH)) {
                        return "Failed at A0406";
                    }
                    break;
                case 8:
                    // indexOf
                    if (!testIndexOf(al, dl, random, TEST_SIZE, MAX_WIDTH)) {
                        return "Failed at A0406";
                    }
                    break;
                case 9:
                    // lastIndexOf
                    if (!testLastIndexOf(al, dl, random, TEST_SIZE, MAX_WIDTH)) {
                        return "Failed at A0406";
                    }
                    break;
                case 10:
                    // remove
                    if (!testRemove(al, dl, random, TEST_SIZE, MAX_WIDTH)) {
                        return "Failed at A0406";
                    }
                    break;
                default:
                    return "Failed at A0420";
            } // switch/case
        } // for 

        // now some copy tests: 
        DLList<String> theSecondDeepList = new DLList<>(dl);
        DLList<String> theThirdDeepList;
        theThirdDeepList = dl.cloneIsh();
        DLList<String> theFourthShallowList;
        theFourthShallowList = dl;
        // Check all the same
        if (!theSecondDeepList.sameContentsAs(dl)) {
            return "Failed at A0421";
        }
        if (!theThirdDeepList.sameContentsAs(dl)) {
            return "Failed at A0422";
        }
        if (!theFourthShallowList.sameContentsAs(dl)) {
            return "Failed at A0423";
        }
        // now change theTree and compare
        Iterator<String> theSetIterator = dl.iterator();
        // pick 4 items from the middle 
        if (dl.size() < 4) {
            return "Failed at A0424";
        } else {
            for (int i = 0; i < dl.size() / 3; ++i) {
                theSetIterator.next();
            }
        }
        String s1 = theSetIterator.next();
        String s2 = theSetIterator.next();
        String s3 = theSetIterator.next();
        String s4 = theSetIterator.next();

        //  System.out.println("dl is " + dl);
        //  System.out.println("secondDeep is " + theSecondDeepList);
        int s1Index = dl.indexOf(s1);
        if (false == dl.remove(s1)) {
            return "Failed at A0335";
        }
        //  System.out.println("from dl removed " + s1 + " at position " + s1Index);
        //  System.out.println("dl is " + dl + "\n\tand has size " + dl.getCount());
        int s2Index = theSecondDeepList.indexOf(s2);
        if (false == theSecondDeepList.remove(s2)) {
            return "Failed at A0336";
        }
        int s3Index = theThirdDeepList.indexOf(s3);
        if (false == theThirdDeepList.remove(s3)) {
            return "Failed at A0337";
        }
        int s4Index = theFourthShallowList.indexOf(s4);
        if (false == theFourthShallowList.remove(s4)) {
            return "Failed at A0338";
        }
        //  System.out.println("from theFourthShallowList removed " + s4 + " at position " + s4Index);
        //  System.out.println("theFourthShallowList is " + theFourthShallowList + "\n\tand has size" + theFourthShallowList.getCount());
        //  System.out.println("dl is " + dl + "\n\tand has size " + dl.getCount());

        /*        System.out.println("dl is " + dl);
        System.out.println("secondDeep is " + theSecondDeepList);
         */
        // Check only shallow the same
        if (theSecondDeepList.sameContentsAs(dl)) {
            return "Failed at A0421";
        }
        if (theThirdDeepList.sameContentsAs(dl)) {
            return "Failed at A0422";
        }
        if (!theFourthShallowList.sameContentsAs(dl)) {
            return "Failed at A0423";
        }
        // add them back, starting at the end
        if (false == theFourthShallowList.addAt(s4Index, s4)) {
            return "Failed at A0338";
        }
        if (false == theThirdDeepList.addAt(s3Index, s3)) {
            return "Failed at A0338";
        }
        if (false == theSecondDeepList.addAt(s2Index, s2)) {
            return "Failed at A0338";
        }
        if (false == dl.addAt(s1Index, s1)) {
            return "Failed at A0338";
        }
        // Check all the same
        if (!theSecondDeepList.sameContentsAs(dl)) {
            return "Failed at A0421";
        }
        if (!theThirdDeepList.sameContentsAs(dl)) {
            return "Failed at A0422";
        }
        if (!theFourthShallowList.sameContentsAs(dl)) {
            return "Failed at A0423";
        }

        System.out.println("Test counts:");
        System.out.println("\tpublic T getItemAt(int position) :" + testCount[0]);
        System.out.println("\t public T setDataInNodeAt(int position, T newData) :" + testCount[1]);
        System.out.println("\tpublic int getCount() :" + testCount[2]);
        System.out.println("\t public boolean addAt(int position, T addMe) :" + testCount[3]);
        System.out.println("\tpublic void append(T addMe) :" + testCount[4]);
        System.out.println("\tpublic void prepend(T addMe) :" + testCount[5]);
        System.out.println("\tboolean equals(ArrayList<T> compareWith) :" + testCount[6]);
        System.out.println("\t public boolean contains(T findMe) :" + testCount[7]);
        System.out.println("\tpublic int indexOf(T findMe) :" + testCount[8]);
        System.out.println("\tpublic int lastIndexOf(T findMe) :" + testCount[9]);
        System.out.println("\tboolean remove(T removeMe) :" + testCount[10]);
        System.out.println("\tFinal size :" + dl.size());
        System.out.println("\tAs a point of information, the hash of this dll is " 
                + dl.hashCode() +" (note that this is a 32 bit int and may be negative)");
        return "";
    }

    // creates a padded numeric string of desiredWidth chars with 
    // a number from ~0...maxRandomNumber
    String getThickString(Random random, int maxRandomNumber, int desiredWidth) {
        StringBuilder sb = new StringBuilder();
        int aNumber = random.nextInt(maxRandomNumber - 1) + 1;
        String sNumber = Integer.toString(aNumber);
        while (sb.length() < desiredWidth - sNumber.length()) {
            sb.append("0");
        }
        sb.append(sNumber);
        sNumber = sb.toString();
        return sNumber;
    }

    boolean testContains(ArrayList<String> al, DLList<String> dl, Random random, int maxRandomNumber, int desiredWidth) {
        String newStringNumber = getThickString(random, maxRandomNumber, desiredWidth);
        // return true iff they are the same
        return al.contains(newStringNumber) == dl.contains(newStringNumber);
    }

    boolean testIndexOf(ArrayList<String> al, DLList<String> dl, Random random, int maxRandomNumber, int desiredWidth) {
        String newStringNumber = getThickString(random, maxRandomNumber, desiredWidth);
        // return true iff they are the same
        return al.indexOf(newStringNumber) == dl.indexOf(newStringNumber);
    }

    boolean testLastIndexOf(ArrayList<String> al, DLList<String> dl, Random random, int maxRandomNumber, int desiredWidth) {
        String newStringNumber = getThickString(random, maxRandomNumber, desiredWidth);
        // return true iff they are the same
        return al.lastIndexOf(newStringNumber) == dl.lastIndexOf(newStringNumber);
    }

    boolean testRemove(ArrayList<String> al, DLList<String> dl, Random random, int maxRandomNumber, int desiredWidth) {
        // return true iff they are the same
        String newStringNumber = getThickString(random, maxRandomNumber, desiredWidth);
        return al.remove(newStringNumber) == dl.remove(newStringNumber);
    }

    boolean testGetItemAt(ArrayList<String> al, DLList<String> dl, Random random) {
        if (al.size() < 5) {
            // pointless
            return true;
        }
        int position = random.nextInt(al.size() - 1);
        String alString = al.get(position);
        String dlString = dl.getItemAt(position);
        // return true iff they are the same
        return alString.equals(dlString);
    }

    boolean testSetDataInNodeAt(ArrayList<String> al, DLList<String> dl, Random random, int maxRandomNumber, int desiredWidth) {
        if (al.size() < 5) {
            // pointless
            return true;
        }
        int position = random.nextInt(al.size() - 1);
        String newStringNumber = getThickString(random, maxRandomNumber, desiredWidth);
        al.set(position, newStringNumber);
        dl.setDataInNodeAt(position, newStringNumber);
        return true;
    }

    boolean testSize(ArrayList<String> al, DLList<String> dl, Random random) {
        int alSize = al.size();
        int dlSize = dl.size();
        // return true iff they are the same
        return alSize == dlSize;
    }

    boolean testAddAt(ArrayList<String> al, DLList<String> dl, Random random, int maxRandomNumber, int desiredWidth) {
        if (al.size() < 5) {
            // pointless
            return true;
        }
        int position = random.nextInt(al.size() - 1);
        String newStringNumber = getThickString(random, maxRandomNumber, desiredWidth);
        al.add(position, newStringNumber);
        // if it fails, al.add should have thrown IndexOutOfBoundsException
        return dl.addAt(position, newStringNumber);
    }

    boolean testAppend(ArrayList<String> al, DLList<String> dl, Random random, int testSize, int maxWidth) {
        // add 10 items to each
        for (int i = 0; i < 10; ++i) {
            String sNumber = getThickString(random, testSize, maxWidth);
            al.add(sNumber);
            dl.append(sNumber);
        }
        return true;
    }

    boolean testPrepend(ArrayList<String> al, DLList<String> dl, Random random, int testSize, int maxWidth) {
        // add 10 items to each
        for (int i = 0; i < 10; ++i) {
            String sNumber = getThickString(random, testSize, maxWidth);
            al.add(0, sNumber);
            dl.prepend(sNumber);
        }
        return true;
    }

    boolean testEquals(ArrayList<String> al, DLList<String> dl, Random random) {
        //see my comment in DLList as to why we don't override equals(Object)
        // although, maybe...
        // return true iff they are the same
        return dl.sameContentsAs(al);
    }

    public String getTestName() {
        return "TestCases2";
    }
}
