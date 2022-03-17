/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.farmingdale.m03linkedlist;

import java.util.ArrayList;
import java.util.Iterator;
import com.google.gson.*; // for cloning
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.NoSuchElementException;

/**
 *
 * @author gerstl This is somewhat based on the code from Open Data Structures
 * by Pat Morin, Java Edition but with lots of my modifications
 * @param <T>
 */
//public class DLList<T extends Comparable<T> & Cloneable> implements SortTestable<T> , Iterable<T>, Cloneable  {
public class DLList<T extends Comparable<T>> implements SortTestable<T>, Iterable<T> {

    class Node<T> {

        T data;
        Node<T> next;
        Node<T> previous;

    }

    int n; // number of elements
    Node<T> dummy;

    /**
     * default ctor
     */
    public DLList() {
        // remember to create dummy
        dummy = new Node<T>();
        dummy.next = dummy;
        dummy.previous = dummy;
        n = 0;
    }

    /**
     * Copy ctor
     *
     * @param copyMe the list to copy
     */
    public DLList(DLList copyMe) {
        DLList<T> other = copyMe.cloneIsh();
        dummy = other.dummy;
        n = other.n;
    }

    /**
     *
     * @return String representation of the list. Note that this uses the
     * iterator, so that must be implemented. Format returned mirrors that
     * returned by ArrayList<>.toString()
     */
    @Override
    public String toString() {
        Iterator<T> iter = iterator();
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        while (iter.hasNext()) {
            sb.append(iter.next());
            if (iter.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * Produces a possible deep copy. See
     * https://www.baeldung.com/java-deep-copy and
     * https://stackoverflow.com/questions/1138769/why-is-the-clone-method-protected-in-java-lang-object
     * for some flavor on this. NOTE THAT THIS IS NOT REALLY TESTED WELL
     *
     * @return Another DLList with the same items
     */
    public DLList<T> cloneIsh() {
        DLList<T> rv = new DLList<>();
        Iterator<T> otherIterator = iterator();
        // See the first article above
        Gson gson = new Gson();
        while (otherIterator.hasNext()) {
            // O(1) operation
            T otherItem = otherIterator.next();
            Type typeOfT = new TypeToken<T>() {
            }.getType();
            T otherItemCopy = gson.fromJson(gson.toJson(otherItem), typeOfT);
            rv.append(otherItemCopy);
        }
        return rv;
    }

    /**
     *
     * @return an iterator implementing next() and hasNext(). Extra credit for
     * implementing remove() [see the assignment sheet]
     */
    @Override
    public Iterator<T> iterator() {
        Iterator<T> iterRv; // anon inner class iterator
        iterRv = new Iterator<T>() {
            Node<T> current = dummy.next;
            int numItems = n;

            @Override
            public boolean hasNext() {
                return numItems > 0;
            } // hasNext()

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                T theData = current.data;
                current = current.next;
                numItems --;
                return theData;
            } // next()

            // note that remove removes the prior element (i.e., you must
            // call next() first, then remove will remove the element that 
            // next pointed to in that prior call
            @Override
            public void remove() {
                // For student version, add exception
                throw new UnsupportedOperationException();
                // make sure there is an element to move to 
                // (next() must have been called)
                // otherwise, throw new IllegalStateException();
            } // remove()
        };
        return iterRv;
    }

    /**
     *
     * @return true iff the item is in the list
     */
    public boolean contains(T findMe) {
        return (-1 != indexOf(findMe));
    }

    /**
     *
     * @return first index of item in the list, -1 if not found. Note that the
     * first actual item (not dummy) is 0.
     */
    public int indexOf(T findMe) {
        Node<T> current = dummy.next;
        int index = 0;
        while (current.next != dummy) {
            if (current.data == findMe) {
                return index;
            } else {
                current = current.next;
                index++;
            }
        }
        return -1;
    }

    /**
     *
     * @return last index of item in the list, -1 if not found. Note that the
     * first actual item (not dummy) is 0.
     */
    public int lastIndexOf(T findMe) {
        Node<T> current = dummy.next;
        int index = 0;
        int indexFound = 0;
        boolean found = false;
        while (current.next != dummy) {
            if (current.data == findMe) {
                indexFound = index;
                found = true;
                current = current.next;
                index++;
            } else {
                current = current.next;
                index++;
            }
        }
        if (found) {
            return indexFound;
        } else {
            return -1;
        }
    }

    /**
     *
     * @return true iff the item was found and removed
     */
    public boolean remove(T removeMe) {
        Node<T> current = dummy.next;
        int index = 0;
        while (current != dummy) {
            if (current.data == removeMe) {
                setNext(current.previous, current.next);
                setPrevious(current.next, current.previous);
                n--;
                return true;
            } else {
                current = current.next;
                index++;
            }
        }
        return false;
    }

    // Internal method to get a node
    /**
     *
     * @return The data at position. Note that the first actual item (not dummy)
     * is 0.
     */
    public T getItemAt(int position) {
        Node<T> current = dummy.next;
        int i = 0;
        while (i < position && current.next != dummy) {
            current = current.next;
            i++;
        }
        if (i == position) {
            return current.data;
        } else {
            throw new NoSuchElementException();
        }
    }

    /**
     *
     * Changes the data stored in the existing item at position (undefined if
     * that item doesn't exist)
     *
     * @return The data previously stored at that position
     */
    public T setDataInNodeAt(int position, T newData) {
        Node<T> current = dummy.next;
        int i = 0;
        while (i < position && current.next != dummy) {
            current = current.next;
            i++;
        }
        if (i == position) {
            T theData = current.data;
            current.data = newData;
            return theData;
        } else {
            throw new NoSuchElementException();
        }
    }

    /**
     *
     * @return The number of items in the list
     */
    public int size() {
        return n;
    }

    // required by SortTestable. 
    public int getCount() {
        return size();
    }

    /**
     * Adds a new data item so it will be at position (displacing all later
     * items).
     *
     * @return true if the item was added (you can't add a node to position
     * "position" if that position will not exist when you add the item
     */
    public boolean addAt(int position, T addMe) {
         Node<T> current = dummy.next;
        int i = 0;
        while (i < position && current.next != dummy) {
            current = current.next;
            i++;
        }
        if (i == position) {
            Node<T> theNode = new Node();
            theNode.data = addMe;
            setNext(theNode , current);
            setPrevious(theNode, current.previous);
            setNext(current.previous , theNode);
            setPrevious(current , theNode);
            n++;
            
            return true;
        } else {
            throw new NoSuchElementException();
        }
    }

    /**
     *
     * Remove the item at position
     *
     * @return The data previously stored at that position (null if not
     * successful)
     */
    public T removeAt(int position) {
         Node<T> current = dummy.next;
        int i = 0;
        while (i < position && current.next == dummy) {
            current = current.next;
            i++;
        }
        if (i == position) {
            T theData = current.data;
            setNext(current.previous , current.next);
            setPrevious(current.next , current.previous);
            n--;
            return theData;
        } else {
            throw new NoSuchElementException();
        }
    }

    // all this should be the MERGESORT assignment. AddAtTail, equals (arrayList)
    // added by me for sorting. 
    public void append(T addMe) {
        Node<T> theNode = new Node();
        theNode.data = addMe;
        setNext(theNode , dummy);
        setPrevious(theNode, dummy.previous);
        setNext(dummy.previous , theNode);
        setPrevious(dummy , theNode);
        n++;
    }

    /**
     *
     * add to the beginning of the list
     *
     */
    public void prepend(T addMe) {
        Node<T> theNode = new Node();
        theNode.data = addMe;
        setNext(theNode , dummy.next);
        setPrevious(dummy.next , theNode);
        setNext(dummy , theNode);
        setPrevious(theNode , dummy);
        n++;
    }

    /**
     *
     * Note that this requires working iterators
     *
     * @param compareWith the ArrayList to compare with
     * @return true iff the ArrayList and DLL contain the same data in the same
     * order
     */
    public boolean sameContentsAs(ArrayList<T> compareWith) {
        // O(1) check--if they are not hte same length, they are not 
        // the same
        if (compareWith.size() != n) {
            return false;
        }
        Iterator<T> myIterator = iterator();
        Iterator<T> cwIterator = compareWith.iterator();
        // now element by element
        // note that we start at the pre- for cwIterator, but in the element
        // for llIterator. We need to do this to process a 1 element correctly
        while (myIterator.hasNext() && cwIterator.hasNext()) {
            // get and advance the cw iterator
            T cwElement = cwIterator.next();
            T llElement = myIterator.next();
            if (!cwElement.equals(llElement)) {
                return false;
            }
        }
        // if we get here, we found no differences
        return true;
    }

    /**
     *
     * Note that this requires working iterators
     *
     * @param compareWith the DLList to compare with
     * @return true iff both us and the param contain the same data in the same
     * order
     */
    public boolean sameContentsAs(DLList<T> compareWith) {
        if (this == compareWith) {
            return true;
        }
        // you need to write this
        Iterator<T> myIterator = iterator();
        Iterator<T> cwIterator = compareWith.iterator();
        
         while (myIterator.hasNext() && cwIterator.hasNext()) {
            T cwElement = cwIterator.next();
            T llElement = myIterator.next();
            if (!cwElement.equals(llElement)) {
                return false;
            }
        }
       
        return true;
       
    }

    /**
     *
     * @return an integer hashcode
     */
    @Override
    public int hashCode() {
        // just hash the data.
        int rv = 0;
        Iterator i = iterator();
        while (i.hasNext()) {
            // note that this ideally should account for position, but 
            // this does meet the requirements that
            // if a.equals(b), then a.hashCode()==b.hashCode()
            rv += i.next().hashCode();
        }
        // negative values are possible since rv is a 32 bit twos-complement
        // int and since this can overflow safely
        return rv;
    }

    /**
     *
     * @param obj object to check if we are equal
     * @return true iff both are DLList holding the same data
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DLList<?> other = (DLList<?>) obj;
        if (this.n != other.n) {
            return false;
        }
        return sameContentsAs((DLList<T>) other);
    }

    /**
     * This is for the Mergesort Module (5) only. Implement mergesort on the
     * list
     */
    @Override
    public void sort() {
        // do not implement this. This is M5
    }

    //setNext method
    private void setNext(Node<T> current, Node<T> theNext) {
        current.next = theNext;
    }

    //setPrevious method
    private void setPrevious(Node<T> current, Node<T> thePrevious) {
        current.previous = thePrevious;
    }
}
