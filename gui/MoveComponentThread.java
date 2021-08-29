package gui;

import java.util.Iterator;
import java.util.List;
import java.util.Queue;

public class MoveComponentThread extends Thread {



    private List<GraphicsComponent> graphicsComponent;

    public MoveComponentThread(List<GraphicsComponent> graphicsComponent) {

        this.graphicsComponent = graphicsComponent;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Iterator<GraphicsComponent> iterator = graphicsComponent.iterator();
            while (iterator.hasNext()) {
                GraphicsComponent component = iterator.next();
                if (component.isStart()) {
                    if (component.checkMoveX() == true || component.checkMoveY() == true) {
                        component.move();
                    } else {
                        iterator.remove();
                    }
                }
            }
        }
    }
}


