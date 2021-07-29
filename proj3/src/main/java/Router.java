//import com.sun.tools.classfile.ConstantPool;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class provides a shortestPath method for finding routes between two points
 * on the map. Start by using Dijkstra's, and if your code isn't fast enough for your
 * satisfaction (or the autograder), upgrade your implementation by switching it to A*.
 * Your code will probably not be fast enough to pass the autograder unless you use A*.
 * The difference between A* and Dijkstra's is only a couple of lines of code, and boils
 * down to the priority you use to order your vertices.
 */
public class Router {
    /**
     * Return a List of longs representing the shortest path from the node
     * closest to a start location and the node closest to the destination
     * location.
     * @param g The graph to use.
     * @param stlon The longitude of the start location.
     * @param stlat The latitude of the start location.
     * @param destlon The longitude of the destination location.
     * @param destlat The latitude of the destination location.
     * @return A list of node id's in the order visited on the shortest path.
     */

    private static GraphDB.Node stNode;
    private static GraphDB.Node desNode;
    private static GraphDB gragh;

    protected static double disToDes(long id) {
        GraphDB.Node node = gragh.nodeMap.get(id);
        return GraphDB.distance(node.lon, node.lat, desNode.lon, desNode.lat);
    }

    public static List<Long> shortestPath(GraphDB g, double stlon, double stlat,
                                          double destlon, double destlat) {
        gragh = g;
        stNode = gragh.nodeMap.get(g.closest(stlon, stlat));
        desNode = gragh.nodeMap.get(g.closest(destlon, destlat));
        PriorityQueue<SearchNode> pq = new PriorityQueue<>();
        Map<Long, Boolean> vis = new HashMap<>();
        List<Long> paths = new ArrayList<>();
        pq.offer(new SearchNode(stNode.id, 0, null));
        while (!pq.isEmpty() && !isGoal(pq.peek())) {
            SearchNode front = pq.poll();
            vis.put(front.id, true);
            for (long l : g.adjacent(front.id)) {
                if (!vis.containsKey(l) || !vis.get(l)) {
                    pq.offer(new SearchNode(l, front.dis + disBetweenNode(l, front.id), front));
                }
            }
        }
        SearchNode tmp = pq.peek();
        while (tmp != null) {
            paths.add(tmp.id);
            tmp = tmp.preNode;
        }
        Collections.reverse(paths);
        return paths; // FIXME
    }

    private static double disBetweenNode(long l, long front) {
        GraphDB.Node n1 = gragh.nodeMap.get(l);
        GraphDB.Node n2 = gragh.nodeMap.get(front);
        return GraphDB.distance(n1.lon, n1.lat, n2.lon, n2.lat);
    }

    private static boolean isGoal(SearchNode peek) {
        return disToDes(peek.id) == 0;
    }

    /**
     * Create the list of directions corresponding to a route on the graph.
     * @param g The graph to use.
     * @param route The route to translate into directions. Each element
     *              corresponds to a node from the graph in the route.
     * @return A list of NavigatiionDirection objects corresponding to the input
     * route.
     */
    public static List<NavigationDirection> routeDirections(GraphDB g, List<Long> route) {

        ArrayList<NavigationDirection> nav = new ArrayList<>();
        double dis = 0;
        int dir = NavigationDirection.START;
        ArrayList<GraphDB.Edge> edges = getEdges(g, route);
        if (edges.size() == 1) {
            nav.add(new NavigationDirection(dir, edges.get(0).name, edges.get(0).dis));
            return nav;
        }
        for (int i = 1; i < edges.size(); ++i) {
            GraphDB.Edge curEdge = edges.get(i - 1);
            GraphDB.Edge nextEdge = edges.get(i);

            String curEdgename = curEdge.name;
            String nextEdgename = nextEdge.name;

            long preNode = route.get(i - 1);
            long curNode = route.get(i);
            long nextNode = route.get(i + 1);

            dis += curEdge.dis;
            if (!curEdgename.equals(nextEdgename)) {
                nav.add(new NavigationDirection(dir, curEdgename, dis));
                double curBear = g.bearing(preNode, curNode);
                double nextBear = g.bearing(curNode, nextNode);
                dir = getDirection(curBear, nextBear);
                dis = 0;
            }
            if (i == edges.size() - 1) {
                dis += nextEdge.dis;
                nav.add(new NavigationDirection(dir, nextEdgename, dis));
            }
        }
        return nav; // FIXME
    }

    private static int getDirection(double curBear, double nextBear) {
        double relaBear = curBear - nextBear;
        double absBear = Math.abs(relaBear);
        int dir = 0;
        if (absBear > 180) {
            absBear = 360 - absBear;
            relaBear = -relaBear;
        }
        if (absBear <= 15) {
            dir = 1;
        } else if (absBear <= 30) {
            dir = relaBear > 0 ? 2 : 3;
        } else if (absBear <= 100) {
            dir = relaBear > 0 ? 5 : 4;
        } else {
            dir = relaBear > 0 ? 6 : 7;
        }
        return dir;
    }

    private static ArrayList<GraphDB.Edge> getEdges(GraphDB g, List<Long> route) {
        ArrayList<GraphDB.Edge> edges = new ArrayList<>();
        for (int i = 1; i < route.size(); ++i) {
            long nextVertex = route.get(i);
            long Vertex = route.get(i - 1);
            for (GraphDB.Edge e : g.adjedges(Vertex)) {
                if (e.getOther(Vertex) == nextVertex) {
                    edges.add(e);
                }
            }
        }
        return edges;
    }


    /**
     * Class to represent a navigation direction, which consists of 3 attributes:
     * a direction to go, a way, and the distance to travel for.
     */
    public static class NavigationDirection {

        /** Integer constants representing directions. */
        public static final int START = 0;
        public static final int STRAIGHT = 1;
        public static final int SLIGHT_LEFT = 2;
        public static final int SLIGHT_RIGHT = 3;
        public static final int RIGHT = 4;
        public static final int LEFT = 5;
        public static final int SHARP_LEFT = 6;
        public static final int SHARP_RIGHT = 7;

        /** Number of directions supported. */
        public static final int NUM_DIRECTIONS = 8;

        /** A mapping of integer values to directions.*/
        public static final String[] DIRECTIONS = new String[NUM_DIRECTIONS];

        /** Default name for an unknown way. */
        public static final String UNKNOWN_ROAD = "unknown road";
        
        /** Static initializer. */
        static {
            DIRECTIONS[START] = "Start";
            DIRECTIONS[STRAIGHT] = "Go straight";
            DIRECTIONS[SLIGHT_LEFT] = "Slight left";
            DIRECTIONS[SLIGHT_RIGHT] = "Slight right";
            DIRECTIONS[LEFT] = "Turn left";
            DIRECTIONS[RIGHT] = "Turn right";
            DIRECTIONS[SHARP_LEFT] = "Sharp left";
            DIRECTIONS[SHARP_RIGHT] = "Sharp right";
        }

        /** The direction a given NavigationDirection represents.*/
        int direction;
        /** The name of the way I represent. */
        String way;
        /** The distance along this way I represent. */
        double distance;

        /**
         * Create a default, anonymous NavigationDirection.
         */
        public NavigationDirection() {
            this.direction = STRAIGHT;
            this.way = UNKNOWN_ROAD;
            this.distance = 0.0;
        }

        public NavigationDirection(int _d, String _w, double _dis) {
            this.direction = _d;
            this.way = _w;
            this.distance = _dis;
        }

        public String toString() {
            return String.format("%s on %s and continue for %.3f miles.",
                    DIRECTIONS[direction], way, distance);
        }

        /**
         * Takes the string representation of a navigation direction and converts it into
         * a Navigation Direction object.
         * @param dirAsString The string representation of the NavigationDirection.
         * @return A NavigationDirection object representing the input string.
         */
        public static NavigationDirection fromString(String dirAsString) {
            String regex = "([a-zA-Z\\s]+) on ([\\w\\s]*) and continue for ([0-9\\.]+) miles\\.";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(dirAsString);
            NavigationDirection nd = new NavigationDirection();
            if (m.matches()) {
                String direction = m.group(1);
                if (direction.equals("Start")) {
                    nd.direction = NavigationDirection.START;
                } else if (direction.equals("Go straight")) {
                    nd.direction = NavigationDirection.STRAIGHT;
                } else if (direction.equals("Slight left")) {
                    nd.direction = NavigationDirection.SLIGHT_LEFT;
                } else if (direction.equals("Slight right")) {
                    nd.direction = NavigationDirection.SLIGHT_RIGHT;
                } else if (direction.equals("Turn right")) {
                    nd.direction = NavigationDirection.RIGHT;
                } else if (direction.equals("Turn left")) {
                    nd.direction = NavigationDirection.LEFT;
                } else if (direction.equals("Sharp left")) {
                    nd.direction = NavigationDirection.SHARP_LEFT;
                } else if (direction.equals("Sharp right")) {
                    nd.direction = NavigationDirection.SHARP_RIGHT;
                } else {
                    return null;
                }

                nd.way = m.group(2);
                try {
                    nd.distance = Double.parseDouble(m.group(3));
                } catch (NumberFormatException e) {
                    return null;
                }
                return nd;
            } else {
                // not a valid nd
                return null;
            }
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof NavigationDirection) {
                return direction == ((NavigationDirection) o).direction
                    && way.equals(((NavigationDirection) o).way)
                    && distance == ((NavigationDirection) o).distance;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hash(direction, way, distance);
        }
    }
}
