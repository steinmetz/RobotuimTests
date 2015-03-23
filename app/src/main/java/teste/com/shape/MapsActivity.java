package teste.com.shape;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import org.nocrala.tools.gis.data.esri.shapefile.ShapeFileReader;
import org.nocrala.tools.gis.data.esri.shapefile.header.ShapeFileHeader;
import org.nocrala.tools.gis.data.esri.shapefile.shape.AbstractShape;
import org.nocrala.tools.gis.data.esri.shapefile.shape.PointData;
import org.nocrala.tools.gis.data.esri.shapefile.shape.shapes.MultiPointZShape;
import org.nocrala.tools.gis.data.esri.shapefile.shape.shapes.PointShape;
import org.nocrala.tools.gis.data.esri.shapefile.shape.shapes.PolygonShape;

import java.io.FileInputStream;
import java.util.Random;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    Polygon polygon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();




    }

    private void ler() throws Exception {
        String path = Environment.getExternalStorageDirectory().toString() + "/Campeiro/shp/AREA2.shp";
        FileInputStream is = new FileInputStream(path);
        ShapeFileReader r = new ShapeFileReader(is);

        ShapeFileHeader h = r.getHeader();
        System.out.println("The shape type of this files is " + h.getShapeType());

        int total = 0;
        AbstractShape s;
        while ((s = r.next()) != null) {

            switch (s.getShapeType()) {
                case POINT:
                    PointShape aPoint = (PointShape) s;
                    // Do something with the point shape...
                    break;
                case MULTIPOINT_Z:
                    MultiPointZShape aMultiPointZ = (MultiPointZShape) s;
                    // Do something with the MultiPointZ shape...
                    break;
                case POLYGON:
                    PolygonShape aPolygon = (PolygonShape) s;
                    System.out.println("I read a Polygon with "
                            + aPolygon.getNumberOfParts() + " parts and "
                            + aPolygon.getNumberOfPoints() + " points");
                    PolygonOptions options = new PolygonOptions();


                    if (polygon == null) {
                        for (int i = 0; i < aPolygon.getNumberOfParts(); i++) {
                            PointData[] points = aPolygon.getPointsOfPart(i);
                            System.out.println("- part " + i + " has " + points.length
                                    + " points.");

                            for (PointData point : points) {
                                options.add(new LatLng(point.getY(), point.getX()));
                            }
                            Random rand = new Random();

                            options.strokeColor(Color.RED)
                                    .fillColor(Color.argb(255, rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));
                            Polygon polygon = mMap.addPolygon(options);

                        }
                    }
                    break;
                default:
                    System.out.println("Read other type of shape.");
            }
            total++;
        }

        System.out.println("Total shapes read: " + total);

        is.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {

        try {
            mMap.addMarker(new MarkerOptions().position(polygon.getPoints().get(0)).title("Marker"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Polygon polygon = mMap.addPolygon(new PolygonOptions()
                .add(new LatLng(0, 0), new LatLng(0, 5), new LatLng(3, 5), new LatLng(0, 0))
                .strokeColor(Color.RED)
                .fillColor(Color.BLUE));

        try {
            ler();
        } catch (Exception e) {
            e.printStackTrace();
        }


        //
    }
}
