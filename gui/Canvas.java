package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import functionality.AirportSimulator;
import functionality.Event;

import javax.swing.*;


public class Canvas extends JPanel implements ActionListener, Observer{

    final int PANEL_WIDTH = 1900;
    final int PANEL_HEIGHT = 1500;
    final AirportSimulator airportSimulator;
    //boolean left,right,up,down;
    Timer timer;
    private List<GraphicsComponent> graphicsComponentList = new CopyOnWriteArrayList<>();


    AirportSimulator observable = new AirportSimulator();



    public Canvas(AirportSimulator airportSimulator) {
        this.airportSimulator = airportSimulator;

        observable.addObserver(this);//Я ДОБАВИЛ ПОДПИСЧИКА

        this.setPreferredSize(new Dimension(PANEL_WIDTH,PANEL_HEIGHT));
        this.setBackground(Color.white);
        timer = new Timer(1,this);
        catchEvent(Event.DEFAULT_PLANE_PLACE);
        catchEvent(Event.DEFAULT_FIRST_STEWARDESS_PLACE);
        catchEvent(Event.DEFAULT_SECOND_STEWARDESS_PLACE);
        catchEvent(Event.DEFAULT_THIRD_STEWARDESS_PLACE);
        catchEvent(Event.DEFAULT_CHECK_IN_PLACE);
        catchEvent(Event.DEFAULT_TRAIN_PLACE);
        catchEvent(Event.ARRIVE_OF_PASSENGER);

        graphicsComponentList.get(0).start();
        graphicsComponentList.get(1).start();
        graphicsComponentList.get(2).start();
        graphicsComponentList.get(3).start();
        graphicsComponentList.get(4).start();
        graphicsComponentList.get(5).start();
        graphicsComponentList.get(6).start();
    }

    public void start() {
        timer.start();
        airportSimulator.start();
        new MoveComponentThread(graphicsComponentList).start();
    }

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2D = (Graphics2D) g;
        for (GraphicsComponent component : graphicsComponentList) {
            g2D.drawImage(component.getImage(), component.getX(),component.getY(),null);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }


    //pattern Observable
    public void catchEvent(Event event) {
        switch(event) {
            case ARRIVE_OF_PASSENGER:
                graphicsComponentList.add(new GraphicsComponent(
                        new ImageIcon("D:\\PROGRAMMER_ARKADII_CHNTU\\JavaProjects\\src\\gui\\conor.png").getImage(),
                        800, 1500, 800,850,false,false,true,false));
                break;
		/*case BOUGHT_TICKET:
			graphicsComponentList.add(new GraphicsComponent(
					new ImageIcon("D:\\PROGRAMMER_ARKADII_CHNTU\\javaNEW\\Release\\src\\gui\\conor.png").getImage(), 500, 0, 0, 0));
			break;
		case PASSENGER_GOING_TO_PLANE:////////////////////////////////
			graphicsComponentList.add(new GraphicsComponent(
					new ImageIcon("D:\\PROGRAMMER_ARKADII_CHNTU\\javaNEW\\Release\\src\\gui\\conor.png").getImage(), 500, 0, 0, 0));
			break;
		case STEWARDESS_GOING_TO_PLANE:
			graphicsComponentList.add(new GraphicsComponent(
					new ImageIcon("D:\\PROGRAMMER_ARKADII_CHNTU\\javaNEW\\Release\\src\\gui\\stewardess.jpg").getImage(), 500, 0, 0, 0));
			break;*/
            case GOING_TO_TRAIN:
                graphicsComponentList.add(new GraphicsComponent(
                        new ImageIcon("D:\\PROGRAMMER_ARKADII_CHNTU\\JavaProjects\\src\\gui\\plane.jpg").getImage(),
                        500, 0, 0, 0,false,true,false,false));
                break;
            case TAKE_OFF:
                graphicsComponentList.add(new GraphicsComponent(
                        new ImageIcon("D:\\PROGRAMMER_ARKADII_CHNTU\\JavaProjects\\src\\gui\\plane.jpg").getImage(),
                        300, 0, 0, 0,true,false,false,false));
                break;
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            case DEFAULT_FIRST_STEWARDESS_PLACE:
                graphicsComponentList.add(new GraphicsComponent(
                        new ImageIcon("D:\\PROGRAMMER_ARKADII_CHNTU\\JavaProjects\\src\\gui\\stewardess.jpg").getImage(),
                        100, 300, 0, 0,false,false,false,false));
                break;
            case DEFAULT_SECOND_STEWARDESS_PLACE:
                graphicsComponentList.add(new GraphicsComponent(
                        new ImageIcon("D:\\PROGRAMMER_ARKADII_CHNTU\\JavaProjects\\src\\gui\\stewardess.jpg").getImage(),
                        200, 300, 0, 0,false,false,false,false));
                break;
            case DEFAULT_THIRD_STEWARDESS_PLACE:
                graphicsComponentList.add(new GraphicsComponent(
                        new ImageIcon("D:\\PROGRAMMER_ARKADII_CHNTU\\JavaProjects\\src\\gui\\stewardess.jpg").getImage(),
                        300, 300, 0, 0,false,false,false,false));
                break;
            case DEFAULT_CHECK_IN_PLACE:
                graphicsComponentList.add(new GraphicsComponent(
                        new ImageIcon("D:\\PROGRAMMER_ARKADII_CHNTU\\JavaProjects\\src\\gui\\ticket.png").getImage(),
                        750, 550, 0, 0,false,false,false,false));
                break;
            case DEFAULT_TRAIN_PLACE:
                graphicsComponentList.add(new GraphicsComponent(
                        new ImageIcon("D:\\PROGRAMMER_ARKADII_CHNTU\\JavaProjects\\src\\gui\\train.jpg").getImage(),
                        1550, 680, 0, 0,false,false,false,false));
                break;
            case DEFAULT_PLANE_PLACE:
                graphicsComponentList.add(new GraphicsComponent(
                        new ImageIcon("D:\\PROGRAMMER_ARKADII_CHNTU\\JavaProjects\\src\\gui\\plane.jpg").getImage(),
                        1550, 0, 0, 0,false,false,false,false));
                break;

        }
    }



}



