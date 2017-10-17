package ics240.util;


/**
 * A linked list implementation. 
 * Operations include: add, remove (single and array), indexOf and toString.
 * @author Daniel Acton
 *
 */
public class LinkedList {
    
    private ListNode firstNode;
    private ListNode lastNode;
    private int size;
    
    /**
     * For the no-args constructor, the data and next will be null (empty list)
     */
    public LinkedList() {
        this.firstNode = new ListNode();
        this.lastNode = this.firstNode;
        this.size = 0;
    }
    
    /**
     * Construct a list with a single node
     * @param inputData The item to start the list off with
     */
    public LinkedList(Object inputData) {
        this();
        this.add(inputData);
    }
    
    /**
     * Construct a list with an array of nodes
     * @param inputList the nodes to start with
     */
    public LinkedList(Object [] inputList) {
        this();
        
        if (inputList.length > 0) {
            for (int i = 0; i < inputList.length; i++) {
                this.add(inputList[i]);
            }
        }
    }
    
    /**
     * Return a string representation of the list
     */
    public String toString() {
        ListNode currentNode = this.firstNode;
        
        StringBuffer buffer = new StringBuffer();
        
        buffer.append("{");
        for (int i = 0; currentNode != null; i++) {
            if (i > 0) {
                buffer.append(",");
            }
            Object dataObject = currentNode.getData();
            
            buffer.append(dataObject == null ? "" : dataObject);
            currentNode = currentNode.getNext();
        }
        buffer.append("}");
        return buffer.toString();
    }
    
    /**
     * Add a node to the list
     * @param inputData The data for the node
     */
    public void add(Object inputData) {
        ListNode node = new ListNode(inputData);

        /* Make sure we cater for the case where the list is empty */
        if (this.firstNode.getData() == null) { 
            this.firstNode = node;
            this.lastNode = node;
        }
        else {
            this.lastNode.setNext(node);
            this.lastNode = node;
        }

        this.size++;
    }
    
    /**
     * Add an array of nodes to the list
     * @param inputArray The nodes to add
     */
    public void add(Object [] inputArray) {
        for (int i = 0; i < inputArray.length; i++) {
            this.add(inputArray[i]);
        }
    }

    /**
     * Find a node in the linked list
     * This relies on the fact that the specific class of inputData will have
     * a meaningful equals() method.
     */
    public int indexOf(Object inputData) {
        ListNode currentNode = this.firstNode;
        int position = 0;
        boolean found = false;
        
        for (; ; position++) {
            if (currentNode == null) {
                break;
            }
            
            if (inputData.equals(currentNode.getData())) {
                found = true;
                break;
            }
                
            currentNode = currentNode.getNext();
        }
        
        if (!found) {
            position = -1;
        }
        
        return position;
    }
    
    /**
     * Get the size of the list
     * @return The number of items in the list
     */
    public int size() {
        return this.size;
    }
    
    /**
     * Delete a node from the linked list
     * This relies on the fact that the specific class of inputData will have
     * a meaningful equals() method. If the object to remove is not found, 
     * nothing will happen.
     */
    public void remove(Object inputData) {
        ListNode currentNode = this.firstNode;

        if (this.size == 0) {
            return;
        }
        
        boolean wasDeleted = false;
                        
        /* Are we deleting the first node? */
        if (inputData.equals(currentNode.getData())) {
            
            /* Only one node in list, be careful! */
            if (currentNode.getNext() == null) {
                this.firstNode.setData(null);
                this.firstNode = new ListNode();
                this.lastNode = this.firstNode;
                this.size--;
                return;
            }
            
            currentNode.setData(null);
            currentNode = currentNode.getNext(); 
            this.firstNode = currentNode;
            this.size--;
            return;
        }
        
        while (true) {
            /* If end of list, stop */
            if (currentNode == null) {
                wasDeleted = false;
                break;
            }

            /* Check if the data of the next is what we're looking for */
            ListNode nextNode = currentNode.getNext();
            if (nextNode != null) {
                if (inputData.equals(nextNode.getData())) {
                    
                    /* Found the right one, loop around the node */
                    ListNode nextNextNode = nextNode.getNext();
                    currentNode.setNext(nextNextNode);
                    
                    nextNode = null;
                    wasDeleted = true;
                    break;
                }
            }
                
            currentNode = currentNode.getNext();
        }
        
        if (wasDeleted) {
            this.size--;
        }
        
    }

    /**
     * Remove an array of items from the list
     * @param inputArray The items to remove from the list
     */
    public void remove(Object [] inputArray) {
        for (int i = 0; i < inputArray.length; i++) {
            this.remove(inputArray[i]);
        }
    }

    /**
     * Get the element at a specific position
     * @param inputPosition The position of the element
     * @return The element in the given position
     */
    public Object elementAt(int inputPosition) {
        
        if (inputPosition >= this.size || inputPosition < 0) {
            return null;
        }
        
        ListNode currentNode = this.firstNode;
        
        for (int position = 0; position < inputPosition ; position++) {
            currentNode = currentNode.getNext();
        }
        
        return currentNode.getData();
        
    }
        
    /**
     * An inner class to be used only for the LinkedList class
     * The ListNode class is basically a Java Bean for data and next
     * @author Daniel Acton
     *
     */
    private class ListNode {
        
        private Object data;
        private ListNode next;
        
        public ListNode() {
            this.data = null;
            this.next = null;
        }
        
        public ListNode(Object inputData) {
            this.data = inputData;
            this.next = null;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }

        public ListNode getNext() {
            return next;
        }

        public void setNext(ListNode next) {
            this.next = next;
        }
        
    }
}