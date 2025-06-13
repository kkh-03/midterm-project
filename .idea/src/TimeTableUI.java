import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

// 사용자와 상호작용하는 콘솔 UI 클래스
public class TimeTableUI {
    private final Scanner scanner = new Scanner(System.in);  // 사용자 입력
    private final TimeTable timeTable;                        // 시간표 객체

    public TimeTableUI(TimeTable timeTable) {
        this.timeTable = timeTable;
    }

    // UI 메인 루프
    public void startUI() {
        while (true) {
            printMenu();
            int choice = inputInt("선택: ");
            switch (choice) {
                case 1 -> addLecture();                    // 수업 추가
                case 2 -> deleteLecture();                 // 수업 삭제
                case 3 -> timeTable.printLectures();       // 시간표 보기
                case 4 -> FileManager.saveLectures("timetable.dat", timeTable.getLectures());  // 저장
                case 5 -> {
                    System.out.print("검색어: ");
                    timeTable.searchLecture(scanner.nextLine());  // 검색
                }
                case 6 -> editLecture();                   // 수업 수정
                case 7 -> timeTable.printWeeklySchedule();
                case 8 -> {
                    System.out.println("👋 종료합니다.");
                    return;                                // 종료
                }
                default -> System.out.println("❌ 잘못된 선택입니다.");
            }
        }
    }

    // 메뉴 출력
    private void printMenu() {
        System.out.println("\n--- 시간표 관리 프로그램 ---");
        System.out.println("1. 수업 추가");
        System.out.println("2. 수업 삭제");
        System.out.println("3. 시간표 보기");
        System.out.println("4. 저장하기");
        System.out.println("5. 수업 검색");
        System.out.println("6. 수업 수정");
        System.out.println("7. 주간 수업시간표 보기");
        System.out.println("8. 종료");
    }

    // 수업 추가
    private void addLecture() {
        String subject = input("과목명: ").toUpperCase();
        String day = input("요일(MON~FRI): ").toUpperCase();
        LocalTime start = inputTime("시작 시간 (HH:mm): ");
        LocalTime end = inputTime("종료 시간 (HH:mm): ");

        if (!start.isBefore(end)) {
            System.out.println("❌ 시작 시간은 종료 시간보다 이전이어야 합니다.");
            return;
        }

        String room = input("강의실: ");
        if (timeTable.addLecture(new Lecture(subject, day, start, end, room))) {
            System.out.println("✅ 수업 추가 완료");
        } else {
            System.out.println("❌ 수업 추가 실패: 시간이 겹칩니다.");
        }
    }

    // 수업 삭제
    private void deleteLecture() {
        String subject = input("삭제할 과목명: ").toUpperCase();
        String day = input("요일: ").toUpperCase();
        LocalTime start = inputTime("시작 시간 (HH:mm): ");
        LocalTime end = inputTime("종료 시간 (HH:mm): ");

        if (timeTable.removeLecture(subject, day, start, end)) {
            System.out.println("🗑️ 삭제 완료");
        } else {
            System.out.println("❌ 삭제 실패: 찾을 수 없습니다.");
        }
    }

    // 수업 수정
    private void editLecture() {
        String subject = input("수정할 과목명: ");
        LocalTime start = inputTime("새 시작 시간: ");
        LocalTime end = inputTime("새 종료 시간: ");

        if (!start.isBefore(end)) {
            System.out.println("❌ 시작 시간은 종료 시간보다 이전이어야 합니다.");
            return;
        }

        String room = input("새 강의실: ");
        if (timeTable.editLecture(subject, start, end, room)) {
            System.out.println("✏️ 수정 완료");
        } else {
            System.out.println("❌ 수정 실패: 해당되는 과목이 없습니다.");
        }
    }

    // 문자열 입력 받기
    private String input(String msg) {
        System.out.print(msg);
        return scanner.nextLine();
    }

    // 정수 입력 받기
    private int inputInt(String msg) {
        try {
            System.out.print(msg);
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;  // 입력 오류 시 -1 반환
        }
    }

    // 시간 입력 받기
    private LocalTime inputTime(String msg) {
        System.out.print(msg);
        return LocalTime.parse(scanner.nextLine());
    }
}
