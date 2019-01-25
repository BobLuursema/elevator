package projectsunset;

import java.util.ArrayList;

public class Floor {
    private ArrayList<Person> occupants = new ArrayList<>();

    public ArrayList<Person> getOccupants(){
        return new ArrayList<Person>(occupants);
    }

    public String toString(){
        long waitingForElevator = occupants.stream().filter(p -> p.waitingFor != null).count();
        return String.format("Floor(occupants=%d, waitingForElevator=%d)", occupants.size(), waitingForElevator);
    }

    public void addOccupant(Person a){
        this.occupants.add(a);
    }

    public void removeOccupant(Person a){
        this.occupants.remove(a);
    }
    
}