package top10YVR.ui;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.viewer.*;
import top10YVR.model.*;
import top10YVR.parser.IAttractionParser;
import top10YVR.parser.XMLAttractionParser;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by jacobhsu on 2017-04-12.
 */
public class Top10VYRApp extends JFrame implements ISelectionListener {
    public static final int MAP_WIDTH = 800;
    public static final int MAP_HEIGHT = 600;
    private static final String DATA_SOURCE = "./data/attractions.xml";
    private FilterState filterState;
    private Guide registry;
    private JXMapViewer mapViewer;

    // EFFECTS: constructs YVR 10 application
    Top10VYRApp() {
        super("YVR 10");
        loadData();
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        buildMapViewer();
        add(BorderLayout.CENTER, mapViewer);
        add(BorderLayout.EAST, new ControlPanel(filterState, this));
        InfoBox infoBox = new InfoBox(filterState, mapViewer);
        mapViewer.addMouseListener(infoBox);
        mapViewer.setLayout(new GridBagLayout());
        mapViewer.add(infoBox);
        updateMarkers();
        pack();
        setVisible(true);
    }

    @Override
    public void update() {
        updateMarkers();
    }

    // MODIFIES: this
    // EFFECTS: add markers to map corresponding to selected resources
    private void updateMarkers() {
        Set<Attraction> attractions = filterState.getAttractionsWithSelectedFilters();
        Set<Waypoint> markers = new HashSet<>();

        for (Attraction next : attractions) {
            GeoPoint resourceLocn = next.getAttractionInfo().getGeoLocation();
            GeoPosition geoPosition = new GeoPosition(resourceLocn.getLatitude(), resourceLocn.getLongitude());
            markers.add(new DefaultWaypoint(geoPosition));
        }

        WaypointPainter<Waypoint> waypointPainter = new WaypointPainter<>();
        waypointPainter.setWaypoints(markers);
        mapViewer.setOverlayPainter(waypointPainter);
    }

    // MODIFIES: this
    // EFFECTS: load data from file into resource registry
    private void loadData() {
        try {
            IAttractionParser parser = new XMLAttractionParser(DATA_SOURCE);
            registry = parser.parse();
            filterState = new FilterState(registry);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    e.getMessage() + "\nCaused by: " + e.getCause().getMessage(),
                    "Parsing error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // MODIFIES: this
    // EFFECTS: build map viewer centred on Vancouver
    private void buildMapViewer() {
        mapViewer = new JXMapViewer();
        mapViewer.setPreferredSize(new Dimension(MAP_WIDTH, MAP_HEIGHT));

        TileFactoryInfo info = new OSMTileFactoryInfo();
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        tileFactory.setThreadPoolSize(8);
        mapViewer.setTileFactory(tileFactory);


        GeoPosition vancouver = new GeoPosition(49.25, -123.15);
        mapViewer.setZoom(7);
        mapViewer.setAddressLocation(vancouver);
    }

    public static void main(String[] args) {
        new Top10VYRApp();
    }
}