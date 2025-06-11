import java.util.List;

public class TimeTableManager {
    public static void main(String[] args) {
        List<Lecture> loadedLectures = FileManager.load("timetable.dat");
        TimeTable timeTable = new TimeTable();
        timeTable.getLectures().addAll(loadedLectures);

        TimeTableUI ui = new TimeTableUI(timeTable);
        ui.run();
    }
}
