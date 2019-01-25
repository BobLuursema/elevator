package projectsunset;

import java.util.ArrayList;
import java.util.Iterator;

public class Elevator {
    private ArrayList<Person> people = new ArrayList<>();
    private ArrayList<Floor> floors;
    public ArrayList<Stop> stops = new ArrayList<>();
    private final int speed = 10;
    protected final int capacity = 12;
    private int ticksUntilNextStop = 0;
    private boolean isStopped = true;
    private int currentFloor = 0;

    Elevator(ArrayList<Floor> floors){
        this.floors = floors;
    }

    public class Stop {
        public int floor;
        public ArrayList<Person> people = new ArrayList<>();

        Stop(int floor){
            this.floor = floor;
        }

        @Override
        public boolean equals(Object obj) {
            if(obj == null) return false;
            if(getClass() != obj.getClass()) return false;
            Stop stop = (Stop) obj;
            return this.floor == stop.floor;
        }

        public void addPerson(Person p){
            people.add(p);
        }
    }

    public void addStop(int floor){
        stops.add(new Stop(floor));
    }

    public void addStop(int index, int floor){
        stops.add(index, new Stop(floor));
    }

    public int firstStopAt(int floor){
        return stops.indexOf(new Stop(floor));
    }

    public int lastStopAt(int floor){
        return stops.lastIndexOf(new Stop(floor));
    }

    public void addPlannedOccupant(Person p, int fromFloor, int toFloor){
        boolean passedFromFloor = false;
        for(Stop s : stops){
            if(passedFromFloor && s.floor == toFloor){
                return;
            }
            else if(passedFromFloor){
                s.addPerson(p);
            }
            else if(s.floor == fromFloor){
                s.addPerson(p);
                passedFromFloor = true;
            }
        }
    }

    public String toString(){
        return String.format("Elevator(currentFloor=%d stops=%d isStopped=%b, occupants=%d)", currentFloor, stops.size(), isStopped, people.size());
    }

    public ArrayList<Person> getPeople(){
        return people;
    }

    public void arrivedAtFloor(int floor, int currentTime){
        Iterator<Person> i = people.iterator();
        while(i.hasNext()){
            Person p = i.next();
            if(p.hasArrived(floor)){
                floors.get(floor).addOccupant(p);
                p.arrived(currentTime);
                i.remove();
            }
        }
        for(Person p : floors.get(floor).getOccupants()){
            if(p.waitingFor == this){
                floors.get(floor).removeOccupant(p);
                people.add(p);
            }
        }
    }

    public int calculateTicksToStop(int destinationFloor){
        return Math.abs(currentFloor - destinationFloor) * speed;
    }

    public void nextTick(int currentTime){
        if(isStopped){
            if(stops.size() > 0){
                isStopped = false;
                ticksUntilNextStop = calculateTicksToStop(stops.get(0).floor);
            }
        }
        else {
            if(ticksUntilNextStop == 0){
                this.currentFloor = stops.remove(0).floor;
                arrivedAtFloor(currentFloor, currentTime);
                if(stops.size() != 0){
                    ticksUntilNextStop = calculateTicksToStop(stops.get(0).floor);
                }
                else {
                    isStopped = true;
                }
            }
            else {
                ticksUntilNextStop--;
            }
        }
    }
}