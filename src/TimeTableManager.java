import java.io.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TimeTableManager {

    static class Lecture implements Serializable {
        String subject;
        String day;
        LocalTime startTime;
        LocalTime endTime;
        String room;

        public Lecture(String subject, String day, LocalTime startTime, LocalTime endTime, String room) {
            this.subject = subject;
            this.day = day;
            this.startTime = startTime;
            this.endTime = endTime;
            this.room = room;
        }

        public boolean isOverlap(Lecture other) {
            if (!this.day.equals(other.day)) return false;
            return !(this.endTime.isBefore(other.startTime) || this.startTime.isAfter(other.endTime));
        }

        @Override
        public String toString() {
            return day + " | " + subject + " | " + startTime + " ~ " + endTime + " | " + room;
        }
    }

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

        public boolean removeLecture(String subject) {
            return lectures.removeIf(l -> l.subject.equalsIgnoreCase(subject));
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

                    for (Lecture other : lectures) {
                        if (!other.subject.equalsIgnoreCase(subject) && other.isOverlap(temp)) {
                            return false;
                        }
                    }

                    lec.startTime = newStart;
                    lec.endTime = newEnd;
                    lec.room = newRoom;
                    return true;
                }
            }
            return false;
        }

        public void saveToFile(String filename) {
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
                out.writeObject(lectures);
                System.out.println("💾 저장 완료: " + filename);
            } catch (IOException e) {
                System.out.println("❌ 저장 실패: " + e.getMessage());
            }
        }

        @SuppressWarnings("unchecked")
        public void loadFromFile(String filename) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
                this.lectures = (List<Lecture>) in.readObject();
                System.out.println("📂 불러오기 완료: " + filename);
            } catch (FileNotFoundException e) {
                System.out.println("❗ 저장된 파일이 없습니다.");
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("❌ 불러오기 실패: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TimeTable timeTable = new TimeTable();

        while (true) {
            System.out.println("\n--- 시간표 관리 프로그램 ---");
            System.out.println("1. 수업 추가");
            System.out.println("2. 수업 삭제");
            System.out.println("3. 시간표 보기");
            System.out.println("4. 저장하기");
            System.out.println("5. 수업 검색");
            System.out.println("6. 수업 수정");
            System.out.println("7. 종료");
            System.out.print("선택: ");
            int choice = -1;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("❌ 숫자만 입력하세요.");
                continue;
            }

            switch (choice) {
                case 1: {
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
                    break;
                }
                case 2: {
                    System.out.print("삭제할 과목명: ");
                    String subject = scanner.nextLine();
                    if (timeTable.removeLecture(subject)) {
                        System.out.println("🗑️ 수업이 삭제되었습니다.");
                    } else {
                        System.out.println("❌ 삭제 실패: 해당 과목을 찾을 수 없습니다.");
                    }
                    break;
                }
                case 3:
                    timeTable.printLectures();
                    break;
                case 4:
                    timeTable.saveToFile("timetable.dat");
                    break;
                case 5: {
                    System.out.print("검색할 과목명 키워드: ");
                    String keyword = scanner.nextLine();
                    timeTable.searchLecture(keyword);
                    break;
                }
                case 6: {
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
                    break;
                }
                case 7:
                    System.out.println("👋 프로그램을 종료합니다.");
                    scanner.close();
                    return;
                default:
                    System.out.println("❌ 잘못된 선택입니다.");
            }
        }
    }
}
