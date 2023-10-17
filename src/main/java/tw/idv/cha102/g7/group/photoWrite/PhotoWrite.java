package tw.idv.cha102.g7.group.photoWrite;


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

class PhotoWrite {

    public static void main(String argv[]) {
        Connection con = null;
        PreparedStatement pstmt = null;
        InputStream fin = null;
        String url = "jdbc:mysql://localhost:3306/uandme?serverTimezone=Asia/Taipei";
        String userid = "root";
        String passwd = "0115";

        //圖片存放路徑
        String photos = "src/main/resources/static/tmp/Front/dist/img/groupjavaphotowrite"; //測試用圖片已置於【專案錄徑】底下的【resources/DB_photos1】目錄內

        //SQL語句
        String update = "UPDATE `group` SET cover = ? WHERE group_id = ?";

        int count = 1; //主鍵的id號
        try {
            con = DriverManager.getConnection(url, userid, passwd);
            pstmt = con.prepareStatement(update);
            File[] photoFiles = new File(photos).listFiles();
            //照片會直接按順序加入
            for (File f : photoFiles) {
                fin = new FileInputStream(f);
                pstmt = con.prepareStatement(update);

                //pstmt.setInt(2, count) 2代表第二個?參數的位置, count則是數值
                pstmt.setInt(2, count);

                //pstmt.setInt(2, count) 1代表第二個?參數的位置, fin=io流上傳的byte[]數值
                pstmt.setBinaryStream(1, fin);
                pstmt.executeUpdate();

                count++; //每增加一次主鍵+1
                System.out.print(" update the database...");
                System.out.println(f.toString());
            }

            fin.close();
            pstmt.close();
            System.out.println("加入圖片-更新成功.........");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
