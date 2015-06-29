public class EmpManager {
    // Might want to change the name of hte class

    // This class is used to check the lifetime of the missiles at specific time intervals using the ticker clases.
    // Will need to iterate through the list of missiles that currently exist. Once lifetime is exceeded, the missile
    // should be removed ina concurrently safe manner. The missiles list might need to become atomic as the Play class
    // is constantly looping over it to draw and do collision detection.
}
