package gui;

import javax.swing.JFrame;

import functionality.AirportSimulator;

public class AirportSimulatorGui extends JFrame{
    Canvas panel;
    AirportSimulatorGui(){
        panel = new Canvas(new AirportSimulator());

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(panel);
        this.pack();//устанавливает оптимальный размер диалоговых окон
        this.setLocationRelativeTo(null);
    }

    public void start() {
        panel.start();
        setVisible(true);
    }

    public static void main(String[] args) {
        new AirportSimulatorGui().start();
    }


}
