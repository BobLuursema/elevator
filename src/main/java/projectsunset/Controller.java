package projectsunset;

import java.util.ArrayList;

abstract public class Controller {

    protected ArrayList<Elevator> elevators;

    public Controller(ArrayList<Elevator> elevators){
        this.elevators = elevators;
    }

    abstract public Elevator requestElevator(Person p, int fromFloor, int toFloor);

}