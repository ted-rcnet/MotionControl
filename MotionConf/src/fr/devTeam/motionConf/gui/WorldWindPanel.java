/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.devTeam.motionConf.gui;



import fr.devTeam.motionConf.msp.dao.PointPlacemarkBean;
import fr.devTeam.motionConf.util.kmlToMwc;
import fr.devTeam.motionConf.util.mwcToKml;
import fr.devTeam.motionConf.wwUtils.MovablePointPlacemark;
import gov.nasa.worldwind.Model;
import gov.nasa.worldwind.WorldWind;
import gov.nasa.worldwind.WorldWindow;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.awt.WorldWindowGLCanvas;
import gov.nasa.worldwind.event.SelectEvent;
import gov.nasa.worldwind.event.SelectListener;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.layers.CompassLayer;
import gov.nasa.worldwind.layers.Layer;
import gov.nasa.worldwind.layers.LayerList;
import gov.nasa.worldwind.layers.RenderableLayer;
import gov.nasa.worldwind.layers.ViewControlsLayer;
import gov.nasa.worldwind.layers.ViewControlsSelectListener;
import gov.nasa.worldwind.layers.WorldMapLayer;
import gov.nasa.worldwind.ogc.kml.KMLAbstractFeature;
import gov.nasa.worldwind.ogc.kml.KMLRoot;
import gov.nasa.worldwind.ogc.kml.impl.KMLController;
import gov.nasa.worldwind.render.Offset;
import gov.nasa.worldwind.render.PointPlacemarkAttributes;
import gov.nasa.worldwind.util.BasicDragger;
import gov.nasa.worldwind.util.StatusBar;
import gov.nasa.worldwind.util.WWIO;
import gov.nasa.worldwind.util.WWUtil;
import gov.nasa.worldwind.util.layertree.KMLLayerTreeNode;
import gov.nasa.worldwind.util.layertree.KMLNetworkLinkTreeNode;
import gov.nasa.worldwind.util.layertree.LayerTree;
import gov.nasa.worldwind.util.layertree.LayerTreeNode;
import gov.nasa.worldwind.util.tree.BasicTreeLayout;
import gov.nasa.worldwindx.examples.ClickAndGoSelectListener;
import gov.nasa.worldwindx.examples.kml.KMLApplicationController;
import gov.nasa.worldwindx.examples.util.BalloonController;
import gov.nasa.worldwindx.examples.util.HotSpotController;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.stream.XMLStreamException;

/**
 *
 * @author fanny
 */
public class WorldWindPanel extends JPanel {
    private final WorldWindowGLCanvas wwCanvas = new WorldWindowGLCanvas();
    private StatusBar statusBar;
    private final WorldWindPanel me = this;
    private final PointParameters ppm = new PointParameters();
    
    private final JPanel optionPanel = new JPanel();
    private final JButton openKMLButton = new JButton("Ouvrir KML");
    private final JButton openMWCButton = new JButton("Ouvrir MWC");
    private final JButton exportMWCButton = new JButton("Exporter MWC");
    private final JButton exportMissionButton = new JButton("Exp. mission");
    private final JButton importMissionButton = new JButton("Imp. mission");
    private final JFileChooser fileChooser = new JFileChooser();
    private final JFileChooser exportFileChooser = new JFileChooser();

    protected LayerTree layerTree;
    protected RenderableLayer hiddenLayer;
    protected RenderableLayer missionLayer;
    protected HotSpotController hotSpotController;
    protected KMLApplicationController kmlAppController;
    protected BalloonController balloonController;
    
    private ArrayList<MovablePointPlacemark> missionPlaces = new ArrayList();
    
    public WorldWindPanel () {
        super(new BorderLayout());
        
        fileChooser.setMultiSelectionEnabled(true);
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("KML/KMZ File", "kml", "kmz"));
        exportFileChooser.setMultiSelectionEnabled(false);
        
        initPanel();
        initListeners();
    }
    
    private void initListeners () {
        openKMLButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int status = fileChooser.showOpenDialog(SwingUtilities.getWindowAncestor(optionPanel));
                if (status == JFileChooser.APPROVE_OPTION)
                    for (File file : fileChooser.getSelectedFiles())
                        new WorkerThread(file, me).start();
            }
        });
        
        openMWCButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int status = fileChooser.showOpenDialog(SwingUtilities.getWindowAncestor(optionPanel));
                    if (status == JFileChooser.APPROVE_OPTION)
                        for (File file : fileChooser.getSelectedFiles()) {
                            InputStream test = new ByteArrayInputStream(mwcToKml.convert(file).getBytes());
                            new WorkerThread(test, me).start();
                        }
                } catch (FileNotFoundException ex) {
                    JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(me), ex.getMessage(), "load error",  JOptionPane.ERROR_MESSAGE);
                    Logger.getLogger(WorldWindPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        exportMWCButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int status = exportFileChooser.showOpenDialog(SwingUtilities.getWindowAncestor(optionPanel));
                if (status == JFileChooser.APPROVE_OPTION) {
                    File file = exportFileChooser.getSelectedFile();
                    try {
                        kmlToMwc.convert(file, missionPlaces);
                    } catch (FileNotFoundException ex) {
                        JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(me), ex.getMessage(), "save error",  JOptionPane.ERROR_MESSAGE);
                        Logger.getLogger(WorldWindPanel.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(me), ex.getMessage(), "save error",  JOptionPane.ERROR_MESSAGE);
                        Logger.getLogger(WorldWindPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        
        exportMissionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int status = exportFileChooser.showOpenDialog(SwingUtilities.getWindowAncestor(optionPanel));
                if (status == JFileChooser.APPROVE_OPTION) {
                    File file = exportFileChooser.getSelectedFile();
                    try {
                        FileOutputStream fos = new FileOutputStream(file.getAbsolutePath());
                        ObjectOutputStream oos = new ObjectOutputStream(fos);
                        ArrayList<PointPlacemarkBean> list = new ArrayList<PointPlacemarkBean>();
                        for (MovablePointPlacemark mpp : missionPlaces) {
                            list.add(new PointPlacemarkBean(mpp));
                        }
                        oos.writeObject(list);
                    } catch (java.io.IOException ex) {
                        Logger.getLogger(WorldWindPanel.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(me), ex.getMessage(), "save error",  JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        
        importMissionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int status = fileChooser.showOpenDialog(SwingUtilities.getWindowAncestor(optionPanel));
                if (status == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    try {
                        FileInputStream fis = new FileInputStream(file.getAbsolutePath());
                        ObjectInputStream ois = new ObjectInputStream(fis);
                        ArrayList<PointPlacemarkBean> list = (ArrayList)ois.readObject();
                        for (PointPlacemarkBean ppb : list) {
                            addPlacemark(ppb);
                        }
                    } catch (java.io.IOException ex) {
                        Logger.getLogger(WorldWindPanel.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(me), ex.getMessage(), "load error",  JOptionPane.ERROR_MESSAGE);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(WorldWindPanel.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(me), ex.getMessage(), "load error",  JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        
        wwCanvas.getInputHandler().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed (MouseEvent e) {
                if (e.isShiftDown()) {
                    Position p = new Position(wwCanvas.getCurrentPosition().getLatitude(), wwCanvas.getCurrentPosition().getLongitude(), 10);
                    addPlacemark(p);
                }
            }
        });
        
               // Set up to drag
        wwCanvas.addSelectListener(new SelectListener() {
            private final BasicDragger dragger = new BasicDragger(wwCanvas);

            @Override
            public void selected(SelectEvent event) {
                if (event.isLeftDoubleClick()) {
                    ppm.setPm((MovablePointPlacemark)event.getTopObject());
                    ppm.setVisible(true);
                }
                
                if (event.isRightClick()) {
                    missionPlaces.remove((MovablePointPlacemark)event.getTopObject());
                    missionLayer.removeRenderable((MovablePointPlacemark)event.getTopObject());
                }
                
                // Delegate dragging computations to a dragger.
                this.dragger.selected(event);
            }
        });
    }
    
    private void initPanel () {
        this.add(optionPanel,BorderLayout.WEST);
        optionPanel.setLayout(new BoxLayout(optionPanel, BoxLayout.Y_AXIS));
        openKMLButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        optionPanel.add(openKMLButton);
        optionPanel.add(Box.createRigidArea(new Dimension(20,10)));
        openMWCButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        optionPanel.add(openMWCButton);
        optionPanel.add(Box.createRigidArea(new Dimension(20,10)));
        exportMissionButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        optionPanel.add(exportMissionButton);
        optionPanel.add(Box.createRigidArea(new Dimension(20,10)));
        importMissionButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        optionPanel.add(importMissionButton);
        optionPanel.add(Box.createRigidArea(new Dimension(20,10)));
        exportMWCButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        optionPanel.add(exportMWCButton);
                
        this.wwCanvas.setPreferredSize(new Dimension(500,500));
        this.setPreferredSize(new Dimension(500,500));
        Model m = (Model) WorldWind.createConfigurationComponent(AVKey.MODEL_CLASS_NAME);
        this.wwCanvas.setModel(m);
        
        // Setup a select listener for the worldmap click-and-go feature
        this.wwCanvas.addSelectListener(new ClickAndGoSelectListener(wwCanvas, WorldMapLayer.class));

        // Create and install the view controls layer and register a controller for it with the World Window.
        ViewControlsLayer viewControlsLayer = new ViewControlsLayer();
        insertBeforeCompass(wwCanvas, viewControlsLayer);
        wwCanvas.addSelectListener(new ViewControlsSelectListener(wwCanvas, viewControlsLayer));
        
        this.add(wwCanvas, BorderLayout.CENTER);
        this.statusBar = new StatusBar();
        this.add(statusBar, BorderLayout.PAGE_END);
        this.statusBar.setEventSource(wwCanvas);

        // Add the on-screen layer tree, refreshing model with the WorldWindow's current layer list. We
        // intentionally refresh the tree's model before adding the layer that contains the tree itself. This
        // prevents the tree's layer from being displayed in the tree itself.
        this.layerTree = new LayerTree(new Offset(20d, 160d, AVKey.PIXELS, AVKey.INSET_PIXELS));
        this.layerTree.getModel().refresh(wwCanvas.getModel().getLayers());
        BasicTreeLayout layout = (BasicTreeLayout)this.layerTree.getLayout();
        layout.getFrame().setFrameTitle("Calques/KML");

        // Set up a layer to display the on-screen layer tree in the WorldWindow. This layer is not displayed in
        // the layer tree's model. Doing so would enable the user to hide the layer tree display with no way of
        // bringing it back.
        this.hiddenLayer = new RenderableLayer();
        this.hiddenLayer.addRenderable(this.layerTree);
        wwCanvas.getModel().getLayers().add(this.hiddenLayer);

        // Add a controller to handle input events on the layer selector and on browser balloons.
        this.hotSpotController = new HotSpotController(wwCanvas);

        // Add a controller to handle common KML application events.
        this.kmlAppController = new KMLApplicationController(wwCanvas);

        // Add a controller to display balloons when placemarks are clicked. We override the method addDocumentLayer
        // so that loading a KML document by clicking a KML balloon link displays an entry in the on-screen layer
        // tree.
        this.balloonController = new BalloonController(wwCanvas)
        {
            @Override
            protected void addDocumentLayer(KMLRoot document)
            {
                addKMLLayer(document);
            }
        };

        // Give the KML app controller a reference to the BalloonController so that the app controller can open
        // KML feature balloons when feature's are selected in the on-screen layer tree.
        this.kmlAppController.setBalloonController(balloonController);

        
        for (Layer layer : wwCanvas.getModel().getLayers())
        {
            if ("MS Virtual Earth Aerial".equals(layer.getName()) || "Bing Imagery".equals(layer.getName())) {
                layer.setEnabled(true);
                System.out.println(layer.getName());
            }
        }
        
        // Set up the mission layer and make it visible in the layer tree
        missionLayer = new RenderableLayer();
        missionLayer.setName("mission");
        insertBeforeCompass(wwCanvas, missionLayer);
        LayerTreeNode layerNode = new LayerTreeNode(missionLayer);
        this.layerTree.getModel().addLayer(layerNode);
        this.layerTree.makeVisible(layerNode.getPath());
    }
    
    /**
     * Adds the specified <code>kmlRoot</code> to this app frame's <code>WorldWindow</code> as a new
     * <code>Layer</code>, and adds a new <code>KMLLayerTreeNode</code> for the <code>kmlRoot</code> to this app
     * frame's on-screen layer tree.
     * <p/>
     * This expects the <code>kmlRoot</code>'s <code>AVKey.DISPLAY_NAME</code> field to contain a display name
     * suitable for use as a layer name.
     *
     * @param kmlRoot the KMLRoot to add a new layer for.
     */
    protected void addKMLLayer(KMLRoot kmlRoot)
    {
        // Create a KMLController to adapt the KMLRoot to the World Wind renderable interface.
        KMLController kmlController = new KMLController(kmlRoot);

        // Adds a new layer containing the KMLRoot to the end of the WorldWindow's layer list. This
        // retrieves the layer name from the KMLRoot's DISPLAY_NAME field.
        RenderableLayer layer = new RenderableLayer();
        layer.setName((String) kmlRoot.getField(AVKey.DISPLAY_NAME));
        layer.addRenderable(kmlController);
        wwCanvas.getModel().getLayers().add(layer);

        // Adds a new layer tree node for the KMLRoot to the on-screen layer tree, and makes the new node visible
        // in the tree. This also expands any tree paths that represent open KML containers or open KML network
        // links.
        KMLLayerTreeNode layerNode = new KMLLayerTreeNode(layer, kmlRoot);
        this.layerTree.getModel().addLayer(layerNode);
        this.layerTree.makeVisible(layerNode.getPath());
        layerNode.expandOpenContainers(this.layerTree);

        // Listens to refresh property change events from KML network link nodes. Upon receiving such an event this
        // expands any tree paths that represent open KML containers. When a KML network link refreshes, its tree
        // node replaces its children with new nodes created from the refreshed content, then sends a refresh
        // property change event through the layer tree. By expanding open containers after a network link refresh,
        // we ensure that the network link tree view appearance is consistent with the KML specification.
        layerNode.addPropertyChangeListener(AVKey.RETRIEVAL_STATE_SUCCESSFUL, new PropertyChangeListener()
        {
            public void propertyChange(final PropertyChangeEvent event)
            {
                if (event.getSource() instanceof KMLNetworkLinkTreeNode)
                {
                    // Manipulate the tree on the EDT.
                    SwingUtilities.invokeLater(new Runnable()
                    {
                        public void run()
                        {
                            ((KMLNetworkLinkTreeNode) event.getSource()).expandOpenContainers(layerTree);
                            wwCanvas.redraw();
                        }
                    });
                }
            }
        });
    }
    
        
    /** A <code>Thread</code> that loads a KML file and displays it in an <code>AppFrame</code>. */
    public static class WorkerThread extends Thread
    {
        /** Indicates the source of the KML file loaded by this thread. Initialized during construction. */
        protected Object kmlSource;
        /** Indicates the <code>AppFrame</code> the KML file content is displayed in. Initialized during construction. */
        protected WorldWindPanel appFrame;

        /**
         * Creates a new worker thread from a specified <code>kmlSource</code> and <code>appFrame</code>.
         *
         * @param kmlSource the source of the KML file to load. May be a {@link File}, a {@link URL}, or an {@link
         *                  java.io.InputStream}, or a {@link String} identifying a file path or URL.
         * @param appFrame  the <code>AppFrame</code> in which to display the KML source.
         */
        public WorkerThread(Object kmlSource, WorldWindPanel appFrame)
        {
            this.kmlSource = kmlSource;
            this.appFrame = appFrame;
        }

        /**
         * Loads this worker thread's KML source into a new <code>{@link gov.nasa.worldwind.ogc.kml.KMLRoot}</code>,
         * then adds the new <code>KMLRoot</code> to this worker thread's <code>AppFrame</code>. The
         * <code>KMLRoot</code>'s <code>AVKey.DISPLAY_NAME</code> field contains a display name created from either the
         * KML source or the KML root feature name.
         * <p/>
         * If loading the KML source fails, this prints the exception and its stack trace to the standard error stream,
         * but otherwise does nothing.
         */
        @Override
        public void run()
        {
            try
            {
                KMLRoot kmlRoot = this.parse();

                // Set the document's display name
                kmlRoot.setField(AVKey.DISPLAY_NAME, formName(this.kmlSource, kmlRoot));

                // Schedule a task on the EDT to add the parsed document to a layer
                final KMLRoot finalKMLRoot = kmlRoot;
                SwingUtilities.invokeLater(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        appFrame.addKMLLayer(finalKMLRoot);
                    }
                });
            } catch (XMLStreamException ex) {
                Logger.getLogger(WorldWindPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(WorldWindPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        /**
         * Parse the KML document.
         *
         * @return The parsed document.
         *
         * @throws IOException        if the document cannot be read.
         * @throws XMLStreamException if document cannot be parsed.
         */
        protected KMLRoot parse() throws IOException, XMLStreamException
        {
            // KMLRoot.createAndParse will attempt to parse the document using a namespace aware parser, but if that
            // fails due to a parsing error it will try again using a namespace unaware parser. Note that this second
            // step may require the document to be read from the network again if the kmlSource is a stream.
            return KMLRoot.createAndParse(this.kmlSource);
        }
    }

    protected static String formName(Object kmlSource, KMLRoot kmlRoot)
    {
        KMLAbstractFeature rootFeature = kmlRoot.getFeature();

        if (rootFeature != null && !WWUtil.isEmpty(rootFeature.getName()))
            return rootFeature.getName();

        if (kmlSource instanceof File)
            return ((File) kmlSource).getName();

        if (kmlSource instanceof String && WWIO.makeURL((String) kmlSource) != null)
            return WWIO.makeURL((String) kmlSource).getPath();

        return "KML Layer";
    }
    
    public static void insertBeforeCompass(WorldWindow wwd, Layer layer)
    {
        // Insert the layer into the layer list just before the compass.
        int compassPosition = 0;
        LayerList layers = wwd.getModel().getLayers();
        for (Layer l : layers)
        {
            if (l instanceof CompassLayer)
                compassPosition = layers.indexOf(l);
        }
        layers.add(compassPosition, layer);
    }
    
    public void addPlacemark(Position p) {
            MovablePointPlacemark pm = new MovablePointPlacemark(p);
            PointPlacemarkAttributes attrs = new PointPlacemarkAttributes();
            attrs.setImageAddress("img/imageicon-64.png");
            attrs.setImageOffset(new Offset(0.5, 0.5, AVKey.FRACTION, AVKey.FRACTION));
            attrs.setHeading(0d);
            pm.setAttributes(attrs);
            pm.setAltitudeMode(WorldWind.RELATIVE_TO_GROUND);
            pm.setLabelText("0");
            pm.setLineEnabled(true);
            missionPlaces.add(pm);
            missionLayer.addRenderable(pm);
    }
    
    public void addPlacemark(PointPlacemarkBean ppb) {
        MovablePointPlacemark pm = ppb.toMovablePointPlacemark();
        missionPlaces.add(pm);
        missionLayer.addRenderable(pm);
    }
}
