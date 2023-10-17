package tw.idv.cha102.g7.schedule.controller.exception;

public class ScheduleNotFoundException extends RuntimeException {
    // 測試中待開發
    public ScheduleNotFoundException(Integer id) {
        super("找不到此行程，ID: " + id);
    }
}

