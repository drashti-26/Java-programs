package service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TaskServiceTest {
    @Test
    public void addingTaskIncreasesCount() {
        TaskService service = new TaskService();
        service.addTask("Complete Java practical");
        assertEquals(1, service.getTaskCount());
    }

    @Test
    public void addingSecondTaskIncreasesCountAgain() {
        TaskService service = new TaskService();
        service.addTask("Study");
        service.addTask("Practice");
        assertEquals(2, service.getTaskCount());
    }

    @Test
    public void emptyTaskNameThrowsException() {
        TaskService service = new TaskService();
        assertThrows(IllegalArgumentException.class,
                () -> service.addTask(""));
    }
}
