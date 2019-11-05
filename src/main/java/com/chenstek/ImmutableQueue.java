package com.chenstek;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Optional;
import java.util.Objects;

public class ImmutableQueue<T> implements Queue<T> {

    // use logger for debugging
    private Logger logger = LoggerFactory.getLogger(ImmutableQueue.class);

    // use linked list to implement the Queue, as we only deal with enQueue, deQueue and head.
    // no requirement on iterating or size of the queue.
    // linkHead stores the reference to head of the linked list for deQueue
    private LinkedNode linkHead;
    // linkTail stores the reference to tail of the linked list for enQueue
    private LinkedNode linkTail;

    // default constructor
    public ImmutableQueue() {
        linkHead = null;
        linkTail = null;
        logger.info("new empty Queue: {}", this);
    }

    // constructor with provide head and tail
    public ImmutableQueue(LinkedNode linkHead, LinkedNode linkTail) {
        this.linkHead = linkHead;
        this.linkTail = linkTail;
        logger.info("new Queue: {}, current head {}, current tail {}", this, linkHead, linkTail);
    }

    // enQueue function, return a new ImmutableQueue
    @Override
    @SuppressWarnings (value="unchecked")
    public Queue enQueue(T o) {
        logger.info("enQueuing: {}, current head {}, current tail {}", o, linkHead, linkTail);
        // if enQueue object is null, skip, but still return a new immutable queue
        if (o == null) {
            return new ImmutableQueue(linkHead, linkTail);
        }
        LinkedNode data = new LinkedNode(o);
        // if tail exists, set the next reference to newly enqueued object
        Optional.ofNullable(linkTail).ifPresent(tail -> {
            // set the next reference
            tail.setNext(data);
            // only update the tail if data is not null
            linkTail = data;
        });
        // return a new instance of ImmutableQueue with linkHead and linkTail
        // if head doesnt exist, the new Queue would have the head and tail of newly enqueued object
        // otherwise it's the same head and updated tail.
        return new ImmutableQueue(Optional.ofNullable(linkHead).orElse(data),
                                  Optional.ofNullable(linkTail).orElse(data));
    }

    // deQueue function, returns a new immutableQueue
    @Override
    @SuppressWarnings (value="unchecked")
    public Queue deQueue() {
        logger.info("deQueuing: current head {}, current tail {}", linkHead, linkTail);
        // if head has reference to next object, return a new queue with such piece of data
        if (Optional.ofNullable(linkHead).map(LinkedNode::getNext).isPresent()) {
            return new ImmutableQueue(linkHead.getNext(), linkTail);
        }
        // otherwise return an empty queue
        return new ImmutableQueue();
    }

    // head function, returns the head of queue
    @Override
    public T head() {
        // if head has data, returns it. fallback to null if its not configured
        return Optional.ofNullable(linkHead).map(LinkedNode::getData).orElse(null);
    }

    // isEmpty function, returns whether there is item in the queue
    @Override
    public boolean isEmpty() {
        // checking if queue is empty is same as checking whether there is a head in the linked list
        return !Optional.ofNullable(linkHead).isPresent();
    }

    // wrapper class for node of linked list
    private class LinkedNode {

        // property data holds data
        private T data;
        // property next holds reference to next linked node.
        private LinkedNode next;

        public LinkedNode(T data) {
            this.data = data;
            this.next = null;
        }

        // setter for reference to next linked node.
        public void setNext(LinkedNode data) {
            this.next = data;
        }

        // getter for data
        public T getData() {
            return data;
        }

        // getter for reference to next linked node
        public LinkedNode getNext() {
            return next;
        }

        // override toString for printing and debugging
        @Override
        public String toString() {
            return String.format("<Object: %s, hasNext: %s>", data, next != null);
        }
    }
}
