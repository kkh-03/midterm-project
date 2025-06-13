import java.util.List;

// 프로그램 실행 시작점 (메인 클래스)
public class TimeTableManager {
    public static void main(String[] args) {
        // 저장된 수업 불러오기
        List<Lecture> loadedLectures = FileManager.loadLectures("timetable.dat");

        // 시간표 생성 및 UI 실행
        TimeTable timeTable = new TimeTable();
        timeTable.getLectures().addAll(loadedLectures);

        TimeTableUI ui = new TimeTableUI(timeTable);
        ui.startUI();  // 사용자 인터페이스 시작
    }
}
