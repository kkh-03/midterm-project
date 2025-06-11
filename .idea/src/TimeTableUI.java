import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

public class TimeTableUI {
    private final Scanner scanner = new Scanner(System.in);
    private final TimeTable timeTable;

    public TimeTableUI(TimeTable timeTable) {
        this.timeTable = timeTable;
    }

    public void run() {
        while (true) {
            printMenu();
            int choice = inputInt("선택: ");
            switch (choice) {
                case 1 -> addLecture();
                case 2 -> deleteLecture();
                case 3 -> timeTable.printLectures();
                case 4 -> FileManager.save("timetable.dat", timeTable.getLectures());
                case 5 -> {
                    System.out.print("검색어: ");
                    timeTable.searchLecture(scanner.nextLine());
                }
                case 6 -> editLecture();
                case 7 -> System.out.println("📌 정해진 시간표가 없습니다");
                case 8 -> {
                    System.out.println("👋 종료합니다.");
                    return;
                }
                default -> System.out.println("❌ 잘못된 선택입니다.");
            }
        }
    }

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

    private void addLecture() {
        String subject = input("과목명: ");
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
            System.out.println("❌ 수업 추가 실패: 시간이 겹침니다.");
        }
    }

    private void deleteLecture() {
        String subject = input("삭제할 과목명: ");
        String day = input("요일: ").toUpperCase();
        LocalTime start = inputTime("시작 시간 (HH:mm): ");
        LocalTime end = inputTime("종료 시간 (HH:mm): ");

        if (timeTable.removeLecture(subject, day, start, end)) {
            System.out.println("🗑️ 삭제 완료");
        } else {
            System.out.println("❌ 삭제 실패: 찾을 수 없습니다.");
        }
    }

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

    private String input(String msg) {
        System.out.print(msg);
        return scanner.nextLine();
    }

    private int inputInt(String msg) {
        try {
            System.out.print(msg);
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private LocalTime inputTime(String msg) {
        System.out.print(msg);
        return LocalTime.parse(scanner.nextLine());
    }
}
