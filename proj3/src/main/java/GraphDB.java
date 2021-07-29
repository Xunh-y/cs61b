import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.*;

/**
 * Graph for storing all of the intersection (vertex) and road (edge) information.
 * Uses your GraphBuildingHandler to convert the XML files into a graph. Your
 * code must include the vertices, adjacent, distance, closest, lat, and lon
 * methods. You'll also need to include instance variables and methods for
 * modifying the graph (e.g. addNode and addEdge).
 *
 * @author Alan Yao, Josh Hug
 */
public class GraphDB {
    /** Your instance variables for storing the graph. You should consider
     * creating helper classes, e.g. Node, Edge, etc. */

    //储存所有node
    public Map<Long, Node> nodeMap = new HashMap<>();
    //储存所有地点 可能没有与任何node相连
    private Map<Long, Node> locations = new HashMap<>();
    //储存所有name， 每个name都可能对应不只一个node id
    private Map<String, ArrayList<Long>> names = new HashMap<>();
    //储存该node id 相邻的所有node id
    private Map<Long, ArrayList<Long>> adjNodes = new HashMap<>();
    //储存该node id 相邻的所有edge
    private Map<Long, ArrayList<Edge>> adjEdges = new HashMap<>();

    /**
     * Example constructor shows how to create and start an XML parser.
     * You do not need to modify this constructor, but you're welcome to do so.
     * @param dbPath Path to the XML file to be parsed.
     */
    public GraphDB(String dbPath) {
        try {
            File inputFile = new File(dbPath);
            FileInputStream inputStream = new FileInputStream(inputFile);
            // GZIPInputStream stream = new GZIPInputStream(inputStream);

            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            GraphBuildingHandler gbh = new GraphBuildingHandler(this);
            saxParser.parse(inputStream, gbh);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        clean();
    }

    /**
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     * @param s Input string.
     * @return Cleaned string.
     */
    static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

    /**
     *  Remove nodes with no connections from the graph.
     *  While this does not guarantee that any two nodes in the remaining graph are connected,
     *  we can reasonably assume this since typically roads are connected.
     */
    private void clean() {
        //由于每次addnode()时会在adjnodes里创建一个新的表 故遍历所有adjnodes中的表 若表内无value 即说明该node没有连接
        Iterator<Map.Entry<Long, ArrayList<Long>>> it = adjNodes.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Long, ArrayList<Long>> entry = it.next();
            if (entry.getValue().isEmpty()) {
                nodeMap.remove(entry.getKey());
                it.remove();
            }
        }
    }

    /**
     * Returns an iterable of all vertex IDs in the graph.
     * @return An iterable of id's of all vertices in the graph.
     */
    Iterable<Long> vertices() {
        return nodeMap.keySet();
    }

    /**
     * Returns ids of all vertices adjacent to v.
     * @param v The id of the vertex we are looking adjacent to.
     * @return An iterable of the ids of the neighbors of v.
     */
    Iterable<Long> adjacent(long v) {
        return adjNodes.get(v);
    }

    Iterable<Edge> adjedges(long v) {
        return adjEdges.get(v);
    }

    /**
     * Returns the great-circle distance between vertices v and w in miles.
     * Assumes the lon/lat methods are implemented properly.
     * <a href="https://www.movable-type.co.uk/scripts/latlong.html">Source</a>.
     * @param v The id of the first vertex.
     * @param w The id of the second vertex.
     * @return The great-circle distance between the two locations from the graph.
     */
    double distance(long v, long w) {
        return distance(lon(v), lat(v), lon(w), lat(w));
    }

    static double distance(double lonV, double latV, double lonW, double latW) {
        double phi1 = Math.toRadians(latV);
        double phi2 = Math.toRadians(latW);
        double dphi = Math.toRadians(latW - latV);
        double dlambda = Math.toRadians(lonW - lonV);

        double a = Math.sin(dphi / 2.0) * Math.sin(dphi / 2.0);
        a += Math.cos(phi1) * Math.cos(phi2) * Math.sin(dlambda / 2.0) * Math.sin(dlambda / 2.0);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return 3963 * c;
    }

    /**
     * Returns the initial bearing (angle) between vertices v and w in degrees.
     * The initial bearing is the angle that, if followed in a straight line
     * along a great-circle arc from the starting point, would take you to the
     * end point.
     * Assumes the lon/lat methods are implemented properly.
     * <a href="https://www.movable-type.co.uk/scripts/latlong.html">Source</a>.
     * @param v The id of the first vertex.
     * @param w The id of the second vertex.
     * @return The initial bearing between the vertices.
     */
    double bearing(long v, long w) {
        return bearing(lon(v), lat(v), lon(w), lat(w));
    }

    static double bearing(double lonV, double latV, double lonW, double latW) {
        double phi1 = Math.toRadians(latV);
        double phi2 = Math.toRadians(latW);
        double lambda1 = Math.toRadians(lonV);
        double lambda2 = Math.toRadians(lonW);

        double y = Math.sin(lambda2 - lambda1) * Math.cos(phi2);
        double x = Math.cos(phi1) * Math.sin(phi2);
        x -= Math.sin(phi1) * Math.cos(phi2) * Math.cos(lambda2 - lambda1);
        return Math.toDegrees(Math.atan2(y, x));
    }

    /**
     * Returns the vertex closest to the given longitude and latitude.
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    long closest(double lon, double lat) {
        double tmpdis = Double.MAX_VALUE;
        long ans = 0;
        for (long id : vertices()) {
            if (distance(lon, lat, lon(id), lat(id)) < tmpdis) {
                ans = id;
                tmpdis = distance(lon, lat, lon(id), lat(id));
            }
        }
        return ans;
    }

    /**
     * Gets the longitude of a vertex.
     * @param v The id of the vertex.
     * @return The longitude of the vertex.
     */
    double lon(long v) {
        return nodeMap.get(v).lon;
    }

    /**
     * Gets the latitude of a vertex.
     * @param v The id of the vertex.
     * @return The latitude of the vertex.
     */
    double lat(long v) {
        return nodeMap.get(v).lat;
    }

    void addNode(long id, double lon, double lat) {
        Node node = new Node(id, lon, lat);
        nodeMap.put(id, node);
        adjEdges.put(id, new ArrayList<>());
        adjNodes.put(id, new ArrayList<>());
        locations.put(id, node);
    }

    public void addName(long id, double lon, double lat, String name) {
        String lowerNmae = cleanString(name);
        if (!names.containsKey(lowerNmae)) {
            names.put(lowerNmae, new ArrayList<>());
        }
        names.get(lowerNmae).add(id);
        nodeMap.get(id).name = name;
        locations.get(id).name = name;
    }

    public void addWay(ArrayList<Long> ways, String wayname) {
        for (int i = 1; i < ways.size(); ++i) {
            addEdge(ways.get(i - 1), ways.get(i), wayname);
        }
    }

    private void addEdge(Long v, Long u, String wayname) {
        adjNodes.get(v).add(u);
        adjNodes.get(u).add(v);
        adjEdges.get(v).add(new Edge(v, u, distance(u, v), wayname));
        adjEdges.get(u).add(new Edge(v, u, distance(u, v), wayname));
    }

    public static class Node {
        public long id;
        public double lon;
        public double lat;
        public String name = null;

        public Node(long _id, double _lon, double _lat) {
            id = _id;
            lon = _lon;
            lat = _lat;
        }
    }

    public static class Edge {
        public long v;
        public long u;
        public double dis;
        public String name;

        public Edge(long _v, long _u, double _dis, String _name) {
            v = _v;
            u = _u;
            dis = _dis;
            name = _name;
        }

        public long getOther(long l) {
            return v == l ? u : v;
        }
    }
}
