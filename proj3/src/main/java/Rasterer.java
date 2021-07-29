import java.util.HashMap;
import java.util.Map;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {

    private static final double ROOT_ULLAT = MapServer.ROOT_ULLAT;
    private static final double ROOT_ULLON = MapServer.ROOT_ULLON;
    private static final double ROOT_LRLAT = MapServer.ROOT_LRLAT;
    private static final double ROOT_LRLON = MapServer.ROOT_LRLON;
    private static final double TILE_SIZE = MapServer.TILE_SIZE;
    private static final double W = Math.abs(ROOT_LRLON - ROOT_ULLON);
    private static final double H = Math.abs(ROOT_LRLAT - ROOT_ULLAT);
    public Rasterer() {
        // YOUR CODE HERE
    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     *                    forget to set this to true on success! <br>
     */
    public Map<String, Object> getMapRaster(Map<String, Double> params) {
        Map<String, Object> results = new HashMap<>();
        double lrlon = params.get("lrlon");
        double ullon = params.get("ullon");
        double w = params.get("w");
        double h = params.get("h");
        double ullat = params.get("ullat");
        double lrlat = params.get("lrlat");
        int depth = computeDepth(lrlon, ullon, w);
        boolean query_success = true;
        if (lrlat >= ullat || lrlon <= ullon || lrlat >= ROOT_ULLAT || lrlon <= ROOT_ULLON || ullat <= ROOT_LRLAT ||ullon >= ROOT_LRLON) {
            query_success = false;
        }
        int numImg = (int) Math.pow(2, depth);
        double wPerImg = W / Math.pow(2.0, depth);
        double hPerImg = H / Math.pow(2.0, depth);
        int xSt = (int) Math.floor(Math.abs(ullon - ROOT_ULLON) / wPerImg);
        int ySt = (int) Math.floor(Math.abs(ullat - ROOT_ULLAT) / hPerImg);
        int xEn = (int) Math.floor(Math.abs(lrlon - ROOT_ULLON) / wPerImg);
        int yEn = (int) Math.floor(Math.abs(lrlat - ROOT_ULLAT) / hPerImg);
        String[][] render_grid = new String[yEn - ySt + 1][xEn - xSt + 1];
        double raster_ul_lon = ROOT_ULLON + xSt * wPerImg;
        double raster_ul_lat = ROOT_ULLAT - ySt * hPerImg;
        double raster_lr_lon = ROOT_ULLON + (xEn + 1) * wPerImg;
        double raster_lr_lat = ROOT_ULLAT - (yEn + 1) * hPerImg;
        if (ullon < ROOT_ULLON) {
            xSt = 0;
            raster_ul_lon = ROOT_ULLON;
        }
        if (ullat > ROOT_ULLAT) {
            ySt = 0;
            raster_ul_lat = ROOT_ULLAT;
        }
        if (lrlon > ROOT_LRLON) {
            xEn = numImg - 1;
            raster_lr_lon = ROOT_LRLON;
        }
        if (lrlat < ROOT_LRLAT) {
            yEn = numImg - 1;
            raster_lr_lat = ROOT_LRLAT;
        }
        for (int i = 0; i < xEn - xSt + 1; ++i) {
            for (int j = 0; j < yEn - ySt + 1; ++j) {
                int x = i + xSt;
                int y = j + ySt;
                render_grid[j][i] = "d" + depth + "_x" + x + "_y" + y + ".png";
            }
        }
        results.put("render_grid", render_grid);
        results.put("raster_ul_lon", raster_ul_lon);
        results.put("raster_ul_lat", raster_ul_lat);
        results.put("raster_lr_lon", raster_lr_lon);
        results.put("raster_lr_lat", raster_lr_lat);
        results.put("depth", depth);
        results.put("query_success", query_success);
        return results;
    }

    private int computeDepth(double lrlon, double ullon, double w) {
        int depth = 0;
        double LonDPP = Math.abs(lrlon - ullon) / w;
        while (true) {
            double myLonDPP = W / (TILE_SIZE * Math.pow(2, depth));
            if (myLonDPP < LonDPP) {
                break;
            }
            depth++;
        }
        return depth >= 7 ? 7 : depth;
    }

}
