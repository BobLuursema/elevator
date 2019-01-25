package projectsunset;

import java.util.ArrayList;
import java.util.Random;
import projectsunset.controllers.*;

public class Run {
    public static void main(String[] args){

        Random rand = new Random();

        int amountOfElevators = 3;
        int amountOfFloors = 5;
        int amountOfPeople = 100;

        int breakAt = 119999;


        ArrayList<Floor> floors = new ArrayList<>();
        for(int i = 0; i < amountOfFloors; i++){
            floors.add(new Floor());
        }
        ArrayList<Elevator> elevators = new ArrayList<>();
        for(int i = 0; i < amountOfElevators; i++){
            elevators.add(new Elevator(floors));
        }
        Controller controller = new RandomController(elevators);

        ArrayList<Person> persons = new ArrayList<>();
        for(int i = 0; i < amountOfPeople; i++){
            persons.add(new Person(rand.nextInt(4)+1, controller));
        }
        for(Person p : persons){
            floors.get(0).addOccupant((p));
        }
        for(int i = 80000; i < 180000; i++){
            if(i == breakAt){
                assert 1 == 2;
            }
            for(Person p : persons){
                p.updateGoalFloor(i);
            }
            for(Elevator e : elevators){
                e.nextTick(i);
            }
            if(i%100 == 0){
                printState(i, floors, elevators, persons);
            }
        }
        printResults(persons);
    }
    public static String previousOutput = "";
    public static void printState(int time, ArrayList<Floor> floors, ArrayList<Elevator> elevators, ArrayList<Person> persons){
        StringBuilder output = new StringBuilder();
        output.append("\n------------------------");
        for(int i = 0; i < floors.size(); i++){
            output.append("\n");
            output.append(floors.get(i));
        }
        for(int i = 0; i < elevators.size(); i++){
            output.append("\n");
            output.append(elevators.get(i));
        }
        if(output.toString().equals(previousOutput)){
            return;
        }
        previousOutput = output.toString();
        System.out.print("\n\nCurrent time: " + time);
        System.out.println(output);
    }

    public static void printResults(ArrayList<Person> persons){
        Long totalTime = persons.stream().mapToLong(p -> p.totalTime).sum();
        System.out.println("\n\nAverage time per person: " + totalTime/100);
    }
}