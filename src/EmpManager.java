import java.util.Iterator;
import java.util.List;
import java.util.TimerTask;

public class EmpManager extends TimerTask {
        // This class is used to check the lifetime of the missiles at specific time intervals using the ticker clases.
    // Will need to iterate through the list of missiles that currently exist. Once lifetime is exceeded, the missile
    // should be removed ina concurrently safe manner. The missiles list might need to become atomic as the Play class
    // is constantly looping over it to draw and do collision detection.

    public static final int TIME_STEP = 100;
    private List<Emp> emps;

    public EmpManager(List<Emp> emps){
        this.emps = emps;
    }

    @Override
    public void run(){
        Iterator<Emp> empIterator = emps.iterator();
        while(empIterator.hasNext()){
            Emp emp = empIterator.next();
            if(emp.getLifetime() > Emp.MAX_LIFETIME){
                empIterator.remove();
            } else {
                emp.incrementLifetime(TIME_STEP);
            }
        }
    }
}
