import java.awt.*;
import javax.swing.*;

public class PlanoCartesiano extends JPanel {

        private double x = 0;//x representa la coordenada horizontal.
        private double y = 0;//y representa la coordenada vertical.     //Ambas inician en (0,0).

        private JLabel coordenadas; //Estas variables guardan  referencias a componentes gráficos. 
        private JTextArea historial;//Es una etiqueta que muestra la posición actual del punto.

        private double prevX = 0; //Estas variables almacenan la posición anterior del punto antes de realizar un movimiento.
        private double prevY = 0; //Saber desde dónde venía el punto.
//Calcular el desplazamiento en X.
//Calcular el desplazamiento en Y.
//Calcular la distancia diagonal.

        public PlanoCartesiano(JLabel coords, JTextArea historial)
        {
                this.coordenadas = coords; //Guarda la etiqueta recibida en la variable de la clase.
                this.historial = historial; //Guarda el área de texto recibida para poder escribir movimientos posteriormente.
        }

        @Override
        protected void paintComponent(Graphics g){

                super.paintComponent(g);
                int ancho = getWidth();
                int alto = getHeight();

                int escala = 30;//La escala indica cuántos píxeles representa una unidad del plano cartesiano.
                int unidades = 10;//El número de unidades que se mostrarán en cada dirección desde el centro del plano.

                int centroX = ancho / 2; //Calcula dónde está el centro del panel en el eje X.
                int centroY = alto / 2; //Calcula dónde está el centro del panel en el eje Y.

                g.setColor(Color.BLACK);
                g.drawLine(centroX, centroY - unidades * escala,
                        centroX, centroY + unidades * escala );

                g.drawLine(centroX - unidades * escala, centroY,
                        centroX + unidades * escala, centroY);

                // Etiquetas de ejes
                g.setFont(new Font("Arial", Font.BOLD, 16));
                g.drawString("X", centroX + unidades * escala + 20, centroY);
                g.drawString("-X", centroX - unidades * escala - 35, centroY);
                g.drawString("Y", centroX, centroY - unidades * escala - 15);
                g.drawString("-Y", centroX, centroY + unidades * escala + 25);

        // Cuadrícula
        for (int i = -unidades; i <= unidades; i++) //que dibuja las líneas y números del -10 al 10.
        {

            int xLinea = centroX + i * escala;
            int yLinea = centroY - i * escala;

        g.setColor(Color.LIGHT_GRAY);

        g.drawLine(
                xLinea,
                    centroY - unidades * escala,
                xLinea,
                    centroY + unidades * escala
        );

        g.drawLine(
                    centroX - unidades * escala,
                yLinea,
                    centroX + unidades * escala,
                yLinea
        );

// Etiquetas de la cuadrícula,numeros en los ejes

        g.setColor(Color.BLUE);

        g.drawString(
                String.valueOf(i),
                xLinea - 5,
                centroY + 5
        );

        g.drawString(
                String.valueOf(i),
                centroX - 5,
                yLinea + 5
        );
        }

        // Punto verde
        int px = centroX + (int) (x * escala);
        int py = centroY - (int) (y * escala);

        g.setColor(Color.GREEN);
        g.fillOval(px - 10, py - 10, 20, 20);

}

        public void setCoordenadas(double newX, double newY) {

        prevX = x;
        prevY = y;

                if (newX >= -10 && newX <= 10 &&
                newY >= -10 && newY <= 10) {

        x = newX;
        y = newY;

        coordenadas.setText(
                "Coordenadas actuales: (X" +
                (int)x + ", Y" + (int)y + ")"
        );

        double dx = x - prevX;
        double dy = y - prevY;

                double diagonal =
                        Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));

                historial.append(
                        "Origen: (X" + (int)prevX +
                        ", Y" + (int)prevY + ")" +
                        " → Destino: (X" + (int)x +
                        ", Y" + (int)y + ")" +
                        " | ΔX = " + (int)dx +
                        " | ΔY = " + (int)dy +
                        " | Diagonal = " +
                        String.format("%.2f", diagonal) +
                        "\n"
                );

        repaint();

        } else {

        JOptionPane.showMessageDialog(
                this,
                "Las coordenadas deben estar entre -10 y 10"
        );
        }
}

        public static void main(String[] args) {

        JFrame frame = new JFrame("Plano Cartesiano");

        JLabel coords =
                new JLabel("Coordenadas actuales: (X0, Y0)");

        JTextArea historial = new JTextArea();
        historial.setEditable(false);

        JScrollPane scroll =
                new JScrollPane(historial);

        PlanoCartesiano panel =
                new PlanoCartesiano(coords, historial);

        // Panel izquierdo
        JPanel izquierda =
                new JPanel(new GridLayout(3, 2, 5, 5));

        JTextField campoX = new JTextField();
        JTextField campoY = new JTextField();

        izquierda.add(new JLabel("Nueva X:"));
        izquierda.add(campoX);

        izquierda.add(new JLabel("Nueva Y:"));
        izquierda.add(campoY);

        JButton mover = new JButton("Mover Punto");

        izquierda.add(mover);

        mover.addActionListener(e -> {

        try {

                double newX =
                        Double.parseDouble(campoX.getText());

                double newY =
                        Double.parseDouble(campoY.getText());

                panel.setCoordenadas(newX, newY);

        } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                        frame,
                        "Ingrese valores numéricos válidos"
                );
        }
        });

        // Panel derecho
        JPanel derecha = new JPanel(new BorderLayout());

        JLabel titulo =
                new JLabel("Historial de movimientos");

        titulo.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        derecha.add(titulo, BorderLayout.NORTH);
        derecha.add(scroll, BorderLayout.CENTER);

        // Botón salir
        JButton salir = new JButton("Salir");
        salir.addActionListener(e -> System.exit(0));

        JPanel abajo = new JPanel();
        abajo.add(coords);
        abajo.add(salir);

        frame.setLayout(new BorderLayout());

        frame.add(panel, BorderLayout.CENTER);
        frame.add(izquierda, BorderLayout.WEST);
        frame.add(derecha, BorderLayout.EAST);
        frame.add(abajo, BorderLayout.SOUTH);

        frame.setSize(1000, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        }
}