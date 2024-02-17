import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PixelGrid extends JFrame implements ActionListener {
    static public int gridSize = 20;
    private JPanel[][] grid;
    static public Color[][] colors;

    static int posX;
    static int posY;

    Color currentBrushColor;

    private JLabel coordinatesLabel;

    public PixelGrid(int rows, int cols, int pixelSize) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Pixel Grid");
        setLayout(new BorderLayout());

        grid = new JPanel[rows][cols];
        colors = new Color[rows][cols]; // array for colors

        JPanel gridPanel = new JPanel(new GridLayout(rows, cols));

        // Initialize grid with default color and add mouse listener
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {

                final int row = i + 1;
                final int col = j + 1;

                colors[i][j] = Color.WHITE;

                JPanel pixel = new JPanel();
                pixel.setPreferredSize(new Dimension(pixelSize, pixelSize)); // Set pixel size
                pixel.setBackground(colors[i][j]); // Default color
                pixel.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Border for better visibility
                pixel.setLayout(new BorderLayout()); // Set layout to BorderLayout

                JLabel topLabel = new JLabel(String.valueOf(row), SwingConstants.RIGHT); // Left alignment for row
                                                                                         // number
                JLabel bottomLabel = new JLabel(String.valueOf(col), SwingConstants.RIGHT); // Right alignment for
                                                                                            // column number

                topLabel.setForeground(Color.BLUE);
                pixel.add(topLabel, BorderLayout.NORTH); // Add top label
                pixel.add(bottomLabel, BorderLayout.SOUTH); // Add bottom label

                pixel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        pixel.setBackground(Color.RED); // Change color on hover

                        posX = row - 1;
                        posY = col - 1;

                        coordinatesLabel.setText("Hovered Pixel: [" + row + ", " + col + "]");
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        pixel.setBackground(colors[row - 1][col - 1]); // Restore default color when mouse exits
                        coordinatesLabel.setText("Hovered Pixel: None");
                    }

                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (e.getButton() == MouseEvent.BUTTON2) {
                            colors[row - 1][col - 1] = Color.GRAY;
                        } // Left click
                        else if (e.getButton() == MouseEvent.BUTTON1) {
                            colors[row - 1][col - 1] = Color.GREEN;
                        } // Wheel click
                        else if (e.getButton() == MouseEvent.BUTTON3) {
                            colors[row - 1][col - 1] = Color.ORANGE;
                        } // Right click
                    }

                });
                pixel.addMouseMotionListener(new MouseAdapter() {
                    @Override
                    public void mouseDragged(MouseEvent e) {
                        int b1 = MouseEvent.BUTTON1_DOWN_MASK;
                        int b2 = MouseEvent.BUTTON2_DOWN_MASK;
                        int b3 = MouseEvent.BUTTON3_DOWN_MASK;

                        if ((e.getModifiersEx() & (b1 | b3)) == b3) {
                            currentBrushColor = Color.PINK;
                        } else if ((e.getModifiersEx() & (b1 | b2)) == b1) {
                            currentBrushColor = Color.GRAY;
                        }

                        if (colors[posX][posY] != Color.ORANGE && colors[posX][posY] != Color.GREEN) {
                            colors[posX][posY] = currentBrushColor;
                        }

                    }
                });

                grid[i][j] = pixel;
                gridPanel.add(pixel);
            }
        }

        coordinatesLabel = new JLabel("Hovered Pixel: None", JLabel.CENTER);

        JPanel leftPanel = new JPanel(new FlowLayout());
        JButton jbutton = new JButton("Wipe");
        jbutton.setPreferredSize(new Dimension(90, 25));

        jbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                for (int i = 0; i < 20; i++) {
                    for (int j = 0; j < 20; j++) {
                        colors[i][j] = Color.WHITE;
                        grid[i][j].setBackground(colors[i][j]);
                    }
                }
                JOptionPane.showMessageDialog(null, "Yokyo!");

            }
        });

        JButton Find = new JButton("Find");
        Find.setPreferredSize(new Dimension(90, 25));

        Find.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DStarLite Finder = new DStarLite();
                Finder.main(null);
                for (int i = 0; i < 20; i++) {
                    for (int j = 0; j < 20; j++) {
                        grid[i][j].setBackground(colors[i][j]);
                    }
                }
            }
        });

        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setPreferredSize(new Dimension(140, getHeight())); // Set width of left panel

        leftPanel.add(Box.createVerticalGlue());
        leftPanel.add(jbutton, BorderLayout.CENTER);
        leftPanel.add(Find, BorderLayout.CENTER);
        leftPanel.add(Box.createRigidArea(new Dimension(45, 600)));

        add(leftPanel, BorderLayout.WEST);
        add(gridPanel, BorderLayout.CENTER);
        add(coordinatesLabel, BorderLayout.SOUTH);

        // add(jbutton, BorderLayout.NORTH);

        pack();
        setLocationRelativeTo(null); // Center the frame
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PixelGrid(20, 20, 40)); // Adjust grid size and pixel size as needed
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(null, "Yosyo!");
    }
}