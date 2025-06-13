import java.io.*;
import java.util.ArrayList;
import java.util.List;

// 수업 정보를 파일에 저장하고 불러오는 클래스
public class FileManager {

    // 수업 목록 저장
    public static void saveLectures(String filename, List<Lecture> lectures) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(lectures);
            System.out.println("💾 저장 완료: " + filename);
        } catch (IOException e) {
            System.out.println("❌ 저장 실패: " + e.getMessage());
        }
    }

    // 수업 목록 불러오기
    @SuppressWarnings("unchecked")
    public static List<Lecture> loadLectures(String filename) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            return (List<Lecture>) in.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("❗ 저장된 파일이 없습니다.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("❌ 불러오기 실패: " + e.getMessage());
        }
        return new ArrayList<>();
    }
}
