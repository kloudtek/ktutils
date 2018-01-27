package com.kloudtek.util;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public class SortUtils {
    /**
     * Perform a topological sort of objects.
     * @param objects collection of unique object (
     * @param comparator Comparator that compares two objects and return what kind of dependency exist between the two
     * @return Sorted list
     * @throws CircularDependencyException If a circular dependency was found.
     * @throws IllegalArgumentException If object list is non-unique
     */
    public static <X> List<X> topologicalSort(Collection<X> objects, TopologicalSortComparator<X> comparator) throws CircularDependencyException, IllegalArgumentException {
        if (objects.isEmpty()) {
            return Collections.emptyList();
        }
        //  L ← Empty list that will contain the sorted nodes
        List<Node<X>> sortedList = new ArrayList<Node<X>>();
        //  S ← Set of all nodes with no outgoing edges
        NodeList<X> nodes = objectToNodeList(objects, comparator);
        List<Node<X>> edgeNodes = findEdgeNodes(nodes);
        if (edgeNodes.isEmpty()) {
            throw new CircularDependencyException("Circular dependency between all resources");
        }
        //  for each node n in S do
        for (Node<X> node : edgeNodes) {
            ArrayList<Node<X>> stack = new ArrayList<Node<X>>();
            // visit(n)
            visit(node, sortedList, stack, comparator);
        }
        return nodesToObjects(sortedList);
    }

    private static <X> void visit(Node<X> n, List<Node<X>> sortedList, ArrayList<Node<X>> stack, TopologicalSortComparator<X> comparator) throws CircularDependencyException {
        // if n has not been visited yet then
        if (stack.contains(n)) {
            StringBuilder err = new StringBuilder("Circular dependency: ");
            boolean first = true;
            for (Node<X> node : stack.subList(stack.indexOf(n), stack.size())) {
                if (first) {
                    first = false;
                } else {
                    err.append(" -> ");
                }
                err.append(comparator.getObjectRepresentation(node.obj));
            }
            err.append(" -> ").append(comparator.getObjectRepresentation(n.obj));
            throw new CircularDependencyException(err.toString());
        }
        if (!n.visited) {
            // mark n as visited
            n.visited = true;
            stack.add(n);
            // for each node m with an edge from m to n do
            for (Node<X> dep : n.outDependencies) {
                // visit(m)
                visit(dep, sortedList, new ArrayList<Node<X>>(stack), comparator);
            }
            // add n to L
            sortedList.add(n);
        }
    }

    private static <X> NodeList<X> objectToNodeList(Collection<X> objects, TopologicalSortComparator<X> comparator) {
        HashSet<X> dupList = new HashSet<X>();
        NodeList<X> nodes = new NodeList<X>(objects.size());
        for (X object : objects) {
            if( dupList.contains(object) ) {
                throw new IllegalArgumentException("List contains duplicated object: "+object.toString());
            }
            dupList.add(object);
            nodes.add(new Node<X>(object));
        }
        for (Node<X> source : nodes) {
            for (Node<X> target : nodes) {
                if( source != target ) {
                    TopologicalSortRelationship dependency = comparator.getRelationship(source.obj, target.obj);
                    if( dependency == TopologicalSortRelationship.STRONG ) {
                        source.outDependencies.add(target);
                        target.inDependencies.add(source);
                    }
                }
            }
        }
        return nodes;
    }

    @NotNull
    private static <X> List<X> nodesToObjects(List<Node<X>> sortedList) {
        ArrayList<X> sortedObjectList = new ArrayList<X>(sortedList.size());
        for (Node<X> node : sortedList) {
            sortedObjectList.add(node.obj);
        }
        return sortedObjectList;
    }

    private static <X> List<Node<X>> findEdgeNodes(NodeList<X> allNodes) {
        ArrayList<Node<X>> list = new ArrayList<Node<X>>();
        for (Node<X> n : allNodes) {
            if (n.inDependencies.size() == 0) {
                list.add(n);
            }
        }
        return list;
    }

    private static class NodeList<X> extends ArrayList<Node<X>> {
        // this will be used to support weak references
        NodeList(int initialCapacity) {
            super(initialCapacity);
        }
    }

    static class Node<X> {
        final X obj;
        final HashSet<Node<X>> inDependencies = new HashSet<Node<X>>();
        final HashSet<Node<X>> outDependencies = new HashSet<Node<X>>();
        boolean visited;

        Node(X obj) {
            this.obj = obj;
        }
    }

    public enum TopologicalSortRelationship {
        STRONG, NONE
    }
}
