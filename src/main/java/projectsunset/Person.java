package projectsunset;

import java.util.HashMap;

public class Person {
    protected int currentFloor;
    protected HashMap<Integer, Integer> times = new HashMap<>();
    private Integer goalFloor;
    private Integer leaveTime;
    protected Long totalTime = 0L;
    private Controller controller;
    protected Elevator waitingFor;

    Person(int homeFloor, Controller controller){
        this.currentFloor = 0;
        this.controller = controller;
        this.times.put(90000, homeFloor);
        this.times.put(120000, 0);
        this.times.put(124000, homeFloor);
        this.times.put(170000, 0);
    }

    public void updateGoalFloor(int currentTime){
        Integer goalFloor = this.times.get(currentTime);
        if(goalFloor != null){
            this.goalFloor = goalFloor;
            this.leaveTime = currentTime;
            this.waitingFor = this.controller.requestElevator(this, currentFloor, goalFloor);
        }
    }

    public void arrived(int currentTime){
        this.currentFloor = goalFloor;
        this.goalFloor = null;
        this.totalTime += (currentTime - leaveTime);
        this.waitingFor = null;
    }

    public boolean hasArrived(Integer floor){
        return goalFloor == floor;
    }
}