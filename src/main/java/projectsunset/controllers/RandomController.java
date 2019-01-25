package projectsunset.controllers;

import java.util.ArrayList;
import java.util.Random;

import projectsunset.Controller;
import projectsunset.Elevator;
import projectsunset.Person;

public class RandomController extends Controller {

    private Random rand = new Random(1);

    public RandomController(ArrayList<Elevator> elevators){
        super(elevators);
    }

    public Elevator requestElevator(Person p, int fromFloor, int toFloor){
        Elevator selected = elevators.get(rand.nextInt(3));
        
        int currentFrom = selected.stops.indexOf(fromFloor);
        int currentTo = selected.stops.lastIndexOf(toFloor);
        if(currentFrom == -1){
            selected.stops.add(fromFloor);
            selected.stops.add(toFloor);
        }
        else {
            if(currentFrom > currentTo){
                selected.stops.add(toFloor);
            }
        }
        return selected;
    }

}