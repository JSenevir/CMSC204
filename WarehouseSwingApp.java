import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.PriorityQueue;
import java.util.Comparator;

/**
 * WarehouseSwingApp
 * - Minimal GUI to visualize the "one order per minute" warehouse simulation.
 * - NO dependency on WarehouseSimulation internals: uses a tiny shadow model
 *   for logging (arrived/shipped) and reads time/totals from the Simulation interface.
 *
 * Buttons:
 *  - Load Sample: creates a new Simulation with a small Order[].
 *  - Tick: advances the simulation by 1 minute.
 *  - Auto Run: toggles a timer that ticks automatically.
 *  - Reset: clears UI & stops timer.
 *
 * Labels show: current minute, totals. The log shows which order arrived and shipped each minute.
 *
 * NOTE:
 *  If later you expose queue/returns snapshots via Simulation (e.g., Object[] getQueueSnapshot()),
 *  see the OPTIONAL section below to display actual queue/returns content instead of shadow state.
 */
public class WarehouseSwingApp extends JFrame {

    // ---- Simulation (only via the interface) ----
    private WarehouseSimulation sim = null;

    // ---- Shadow model for GUI logging only (no sim dependency) ----
    private static final class ShadowOrder {
        final String id;
        final int deadline;
        final int arrival;
        ShadowOrder(String id, int deadline, int arrival) {
            this.id = id; this.deadline = deadline; this.arrival = arrival;
        }
        @Override public String toString() {
            return id + " (dl=" + deadline + ", arr=" + arrival + ")";
        }
    }
    private static final class ShadowCmp implements Comparator<ShadowOrder> {
        @Override public int compare(ShadowOrder a, ShadowOrder b) {
            int c = Integer.compare(a.deadline, b.deadline);
            if (c != 0) return c;
            return Integer.compare(a.arrival, b.arrival);
        }
    }

    private Order[] seedOrders = null;                // same array used to build the simulation
    private int guiNextIndex = 0;                     // next order to release in GUI view
    private final PriorityQueue<ShadowOrder> shadowPQ =
            new PriorityQueue<>(new ShadowCmp());

    // ---- UI ----
    private final JLabel minuteLabel = new JLabel("t = 0");
    private final JLabel arrLabel    = new JLabel("Arrived: 0");
    private final JLabel shipLabel   = new JLabel("Shipped: 0");
    private final JLabel lateLabel   = new JLabel("Late: 0");

    private final JTextArea log = new JTextArea(12, 60);

    private final JButton loadBtn = new JButton("Load Sample");
    private final JButton tickBtn = new JButton("Tick");
    private final JButton autoBtn = new JButton("Auto Run");
    private final JButton resetBtn = new JButton("Reset");

    private Timer timer;

    public WarehouseSwingApp() {
        super("Warehouse Simulation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        buildUI();
        wireActions();
        pack();
        setLocationRelativeTo(null);
    }

    private void buildUI() {
        setLayout(new BorderLayout(8,8));

        JPanel top = new JPanel(new GridLayout(1, 4, 8, 8));
        top.add(minuteLabel);
        top.add(arrLabel);
        top.add(shipLabel);
        top.add(lateLabel);
        add(top, BorderLayout.NORTH);

        log.setEditable(false);
        log.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        add(new JScrollPane(log), BorderLayout.CENTER);

        JPanel controls = new JPanel(new FlowLayout(FlowLayout.LEFT));
        controls.add(loadBtn);
        controls.add(tickBtn);
        controls.add(autoBtn);
        controls.add(resetBtn);
        add(controls, BorderLayout.SOUTH);
    }

    private void wireActions() {
        timer = new Timer(500, new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                doTick();
            }
        });

        loadBtn.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                // Build a sample orders array
                seedOrders = createSampleOrders();
                guiNextIndex = 0;
                shadowPQ.clear();

                // Build a real simulation using ONLY the interface/class you already have
                sim = new WarehouseSimulation(seedOrders);

                log.setText("");
                append("Loaded sample orders. Press Tick or Auto Run.");
                refreshStats();
            }
        });

        tickBtn.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                doTick();
            }
        });

        autoBtn.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                if (timer.isRunning()) {
                    timer.stop();
                    autoBtn.setText("Auto Run");
                } else {
                    if (ensureSim()) {
                        timer.start();
                        autoBtn.setText("Stop");
                    }
                }
            }
        });

        resetBtn.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                if (timer.isRunning()) timer.stop();
                autoBtn.setText("Auto Run");
                sim = null;
                seedOrders = null;
                guiNextIndex = 0;
                shadowPQ.clear();
                log.setText("");
                minuteLabel.setText("t = 0");
                arrLabel.setText("Arrived: 0");
                shipLabel.setText("Shipped: 0");
                lateLabel.setText("Late: 0");
            }
        });
    }

    private boolean ensureSim() {
        if (sim == null) {
            JOptionPane.showMessageDialog(this, "Load orders first.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        return true;
    }

    private void doTick() {
        if (!ensureSim()) return;

        if (sim.isFinished()) {
            append("Simulation finished.");
            if (timer.isRunning()) { timer.stop(); autoBtn.setText("Auto Run"); }
            refreshStats();
            return;
        }

        // Current minute BEFORE advancing
        int t = sim.getCurrentMinute();

        // --- Shadow: release one order (if any), using the same rule as the sim ---
        ShadowOrder arrivedShadow = null;
        if (seedOrders != null && guiNextIndex < seedOrders.length) {
            Order o = seedOrders[guiNextIndex++];
            arrivedShadow = new ShadowOrder(o.getId(), o.getDeadlineMinute(), t);
            shadowPQ.offer(arrivedShadow);
        }

        // --- Shadow: ship one (if any) with same comparator rules as sim ---
        ShadowOrder shippedShadow = shadowPQ.isEmpty() ? null : shadowPQ.poll();

        // --- Advance REAL simulation by 1 minute ---
        sim.tick();

        // --- Log using shadow view (what happened this minute) + real totals ---
        append(String.format("Tick -> t=%d | arrived: %s | shipped: %s | totals: Arr=%d Ship=%d Late=%d",
                sim.getCurrentMinute(),
                (arrivedShadow == null ? "(none)" : arrivedShadow.toString()),
                (shippedShadow == null ? "(none)" : shippedShadow.toString()),
                sim.getTotalArrived(), sim.getTotalShipped(), sim.getTotalLate()
        ));

        if (sim.isFinished()) {
            append("Simulation finished.");
            if (timer.isRunning()) { timer.stop(); autoBtn.setText("Auto Run"); }
        }

        refreshStats();
    }

    private void refreshStats() {
        if (sim == null) return;
        minuteLabel.setText("t = " + sim.getCurrentMinute());
        arrLabel.setText("Arrived: " + sim.getTotalArrived());
        shipLabel.setText("Shipped: " + sim.getTotalShipped());
        lateLabel.setText("Late: " + sim.getTotalLate());
    }

    private void append(String s) {
        log.append(s + "\n");
        log.setCaretPosition(log.getDocument().getLength());
    }

    // Sample data (released one per minute in this array order)
    private Order[] createSampleOrders() {
        return new Order[] {
            new Order("A", 2),  // released at t=0
            new Order("B", 1),  // released at t=1
            new Order("C", 5),  // released at t=2
            new Order("D", 2)   // released at t=3
        };
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override public void run() {
                new WarehouseSwingApp().setVisible(true);
            }
        });
    }


}
