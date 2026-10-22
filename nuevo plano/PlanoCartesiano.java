import java.awt.*;
import javax.swing.*;

public class PlanoCartesiano extends JPanel {
    private double x = 0; // posición inicial X
    private double y = 0; // posición inicial Y

    private JLabel coordenadas; // etiqueta para mostrar coordenadas
    private JLabel movimiento; // etiqueta para mostrar desplazamiento
    private double prevX = 0, prevY = 0; // coordenadas anteriores

    public PlanoCartesiano(JLabel coords, JLabel mov) {
        this.coordenadas = coords;
        this.movimiento = mov;
        setFocusable(true);
    }

    private void mover(double dx, double dy) {
        prevX = x;
        prevY = y;

        double nuevoX = x + dx;
        double nuevoY = y + dy;

        if (nuevoX >= -10 && nuevoX <= 10 && nuevoY >= -10 && nuevoY <= 10) {
            x = nuevoX;
            y = nuevoY;
            coordenadas.setText("Coordenadas: (X" + (int)x + ", Y" + (int)y + ")");
            movimiento.setText("El punto se movió " + (int)(x - prevX) + " en X y " + (int)(y - prevY) + " en Y");
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int ancho = getWidth();
        int alto = getHeight();
        int escala = 30;
        int unidades = 10;

        int centroX = ancho / 2;
        int centroY = alto / 2;

        g.setColor(Color.BLACK);
        g.drawLine(centroX, centroY - unidades * escala, centroX, centroY + unidades * escala);
        g.drawLine(centroX - unidades * escala, centroY, centroX + unidades * escala, centroY);

        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("X", centroX + unidades * escala + 30, centroY + 5);
        g.drawString("Y", centroX - 5, centroY - unidades * escala - 30);
        g.drawString("-X", centroX - unidades * escala - 30, centroY + 5);
        g.drawString("-Y", centroX - 5, centroY + unidades * escala + 30);

        for (int i = -unidades; i <= unidades; i++) {
            int xLinea = centroX + i * escala;
            int yLinea = centroY - i * escala;

            g.setColor(Color.LIGHT_GRAY);
            g.drawLine(xLinea, centroY - unidades * escala, xLinea, centroY + unidades * escala);
            g.drawLine(centroX - unidades * escala, yLinea, centroX + unidades * escala, yLinea);

            g.setColor(Color.BLUE);
            g.drawString(String.valueOf(i), xLinea - 5, centroY + 5);
            g.drawString(String.valueOf(i), centroX - 5, yLinea + 5);
        }

        int px = centroX + (int)(x * escala);
        int py = centroY - (int)(y * escala);

        g.setColor(Color.GREEN);
        g.fillOval(px - 10, py - 10, 20, 20);
    }

    public void setCoordenadas(double newX, double newY) {
        prevX = x;
        prevY = y;
        if (newX >= -10 && newX <= 10 && newY >= -10 && newY <= 10) {
            x = newX;
            y = newY;
            coordenadas.setText("Coordenadas: (X" + (int)x + ", Y" + (int)y + ")");
            movimiento.setText("El punto se movió " + (int)(x - prevX) + " en X y " + (int)(y - prevY) + " en Y");
            repaint();
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Plano Cartesiano");

        JLabel coords = new JLabel("Coordenadas: (X0, Y0)");
        JLabel mov = new JLabel("Movimiento: (X0, Y0)");

        PlanoCartesiano panel = new PlanoCartesiano(coords, mov);

        // Panel izquierdo: ingreso de coordenadas
        JPanel izquierda = new JPanel(new GridLayout(3, 2));
        izquierda.add(new JLabel("Nueva X:"));
        JTextField campoX = new JTextField();
        izquierda.add(campoX);

        izquierda.add(new JLabel("Nueva Y:"));
        JTextField campoY = new JTextField();
        izquierda.add(campoY);

        JButton aplicar = new JButton("Mover punto");
        izquierda.add(aplicar);

        aplicar.addActionListener(e -> {
            try {
                double newX = Double.parseDouble(campoX.getText());
                double newY = Double.parseDouble(campoY.getText());
                panel.setCoordenadas(newX, newY);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Ingrese valores numéricos válidos");
            }
        });

        // Panel derecho: explicación del movimiento
        JPanel derecha = new JPanel();
        derecha.add(mov);

        JButton salir = new JButton("Salir");
        salir.addActionListener(e -> System.exit(0));

        frame.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.CENTER);
        frame.add(izquierda, BorderLayout.WEST);
        frame.add(derecha, BorderLayout.EAST);

        JPanel abajo = new JPanel();
        abajo.add(coords);
        abajo.add(salir);
        frame.add(abajo, BorderLayout.SOUTH);

        frame.setSize(900, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

