import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TimeTableManager {

    // Lecture 클래스는 main 밖에 있어야 함
    static class Lecture implements Serializable {
        String subject;         // 과목명
        String day;             // 요일 (예: MON, TUE)
        LocalTime startTime;    // 시작 시간 (HH:mm)
        LocalTime endTime;      // 종료 시간
        String room;            // 강의실

        public boolean isOverlap(Lecture other) {
            if (!this.day.equals(other.day)) return false; // 요일 다르면 겹치지 않음
            return !(this.endTime.isBefore(other.startTime) || this.startTime.isAfter(other.endTime));
        }

        public Lecture(String subject, String day, LocalTime startTime, LocalTime endTime, String room) {
            this.subject = subject;
            this.day = day;
            this.startTime = startTime;
            this.endTime = endTime;
            this.room = room;
        }

        @Override
        public String toString() {
            return day + " | " + subject + " | " + startTime + " ~ " + endTime + " | " + room;
        }
    }

    // TimeTable 클래스도 main 밖에 있어야 함
    static class TimeTable {
        List<Lecture> lectures = new ArrayList<>();

        public boolean addLecture(Lecture newLecture) {
            for (Lecture l : lectures) {
                if (l.isOverlap(newLecture)) {
                    return false;
                }
            }
            lectures.add(newLecture);
            return true;
        }

        public void printLectures() {
            if (lectures.isEmpty()) {
                System.out.println("⛔등록된 수업이 없습니다.");
                return;
            }
            for (Lecture l : lectures) {
                System.out.println(l);
            }
        }
        public void searchLecture(String keyword) {
            boolean found = false;
            for (Lecture l : lectures) {
                if (l.subject.toLowerCase().contains(keyword.toLowerCase())) {
                    System.out.println(l);
                    found = true;
                }
            }
            if (!found) {
                System.out.println("🔍 해당 과목을 찾을 수 없습니다.");
            }
        }
        public boolean editLecture(String subject, LocalTime newStart, LocalTime newEnd, String newRoom) {
            for (Lecture lec : lectures) {
                if (lec.subject.equalsIgnoreCase(subject)) {
                    Lecture temp = new Lecture(lec.subject, lec.day, newStart, newEnd, newRoom);

                    // 겹침 검사 (본인 제외)
                    for (Lecture other : lectures) {
                        if (!other.subject.equalsIgnoreCase(subject) && other.isOverlap(temp)) {
                            return false;
                        }
                    }

                    // 수정
                    lec.startTime = newStart;
                    lec.endTime = newEnd;
                    lec.room = newRoom;
                    return true;
                }
            }
            return false;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TimeTable timeTable = new TimeTable();

        while (true) {
            System.out.println("\n--- 시간표 관리 프로그램 ---");
            System.out.println("1. 수업 추가");
            System.out.println("2. 수업 삭제");  // 아직 구현 안 됐지만 출력만 유지
            System.out.println("3. 시간표 보기");
            System.out.println("4. 저장하기");  // 아직 구현 안 됐지만 출력만 유지
            System.out.println("5. 종료");
            System.out.print("선택: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // 엔터 제거

            switch (choice) {
                case 1:
                    System.out.print("과목명: ");
                    String subject = scanner.nextLine();
                    System.out.print("요일(MON, TUE, ...): ");
                    String day = scanner.nextLine().toUpperCase();
                    System.out.print("시작 시간 (HH:mm): ");
                    LocalTime start = LocalTime.parse(scanner.nextLine());
                    System.out.print("종료 시간 (HH:mm): ");
                    LocalTime end = LocalTime.parse(scanner.nextLine());
                    System.out.print("강의실: ");
                    String room = scanner.nextLine();

                    Lecture lec = new Lecture(subject, day, start, end, room);
                    if (timeTable.addLecture(lec)) {
                        System.out.println("✅ 수업이 추가되었습니다.");
                    } else {
                        System.out.println("❌ 시간 겹침으로 추가할 수 없습니다.");
                    }

                    case 3 -> timeTable.printLectures();

                    case 6 -> {
                        System.out.print("검색할 과목명 키워드: ");
                        String keyword = scanner.nextLine();
                        timeTable.searchLecture(keyword);
                    }

                    case 7 -> {
                        System.out.print("수정할 과목명: ");
                        String subject = scanner.nextLine();
                        System.out.print("새 시작 시간 (HH:mm): ");
                        LocalTime newStart = LocalTime.parse(scanner.nextLine());
                        System.out.print("새 종료 시간 (HH:mm): ");
                        LocalTime newEnd = LocalTime.parse(scanner.nextLine());
                        System.out.print("새 강의실: ");
                        String newRoom = scanner.nextLine();

                        if (timeTable.editLecture(subject, newStart, newEnd, newRoom)) {
                            System.out.println("✏️ 수업이 수정되었습니다.");
                        } else {
                            System.out.println("❌ 수정 실패: 시간 겹침이 있거나 과목을 찾을 수 없습니다.");
                        }
                    }

                    case 5 -> {
                        System.out.println("👋 프로그램을 종료합니다.");
                        return;
                    }

                    default -> System.out.println("❌ 잘못된 선택입니다.");
            }
        }
    }
}
