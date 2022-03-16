/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.farmingdale.m03linkedlist;

import java.util.ArrayList;

/**
 *
 * @author gerstl
 */
public class SmallLinkedListTest implements RunTest {

    public String runTest() {
        DLList<String> dl = new DLList<>();
        if (0 != dl.size()) {
            return "Failed at A0001";
        }
        dl.append("A");
        if (1 != dl.size()) {
            return "Failed at A0002";
        }
        // list is: A
        String s;
        s = dl.getItemAt(0);
        if (!s.equals("A")) {
            return "Failed at A0003";

        }
        dl.append("B");
        dl.prepend("C");
        // list is : C A B
        System.out.println("At Location 1, dl is " + dl);
        s = dl.getItemAt(0);
        if (!s.equals("C")) {
            return "Failed at A0004";

        }
        s = dl.getItemAt(1);
        if (!s.equals("A")) {
            return "Failed at A0005";

        }
        s = dl.getItemAt(2);
        if (!s.equals("B")) {
            return "Failed at A0006";

        }
        if (3 != dl.size()) {
            return "Failed at A0007";
        }
        ArrayList<String> al = new ArrayList<String>();
        al.add("C");
        al.add("A");
        al.add("B");
        System.out.println("At Location 2, ArrayList is " + al + " and dl is " + dl);

        if (!dl.sameContentsAs(al)) {
            return "Failed at A0008";
        }
        dl.setDataInNodeAt(1, "E");
        // list is : C E B
        if (dl.sameContentsAs(al)) {
            return "Failed at A0009";
        }
        al.set(1, "E");
        System.out.println("At Location 3, ArrayList is " + al + " and dl is " + dl);
        if (!dl.sameContentsAs(al)) {
            return "Failed at A0010";
        }

        dl.addAt(1, "F");
        // list is : C F E B
        if (dl.sameContentsAs(al)) {
            return "Failed at A0011";
        }
        al.add(1, "F");
        System.out.println("At Location 4, ArrayList is " + al + " and dl is " + dl);
        if (!dl.sameContentsAs(al)) {
            return "Failed at A0012";
        }
        dl.prepend("E");
        // list is : E C F E B
        if (0 != dl.indexOf("E")) {
            return "Failed at A0013";
        }
        dl.prepend("C");
        // list is : C E C F E B
        System.out.println("At Location 5, dl is " + dl);
        if (1 != dl.indexOf("E")) {
            return "Failed at A0014";
        }
        if (4 != dl.lastIndexOf("E")) {
            return "Failed at A0015";
        }
        return "";
    }
}
