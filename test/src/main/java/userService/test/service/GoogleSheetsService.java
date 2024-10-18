package userService.test.service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
// Import đúng JacksonFactory từ Google API Client
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import userService.test.entity.User;
import userService.test.respository.UserRespository;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class GoogleSheetsService {

    @Autowired
    private UserRespository userRepository; // Tự động tiêm UserRepository để truy cập dữ liệu người dùng

    private Sheets sheetsService; // Đối tượng để làm việc với Google Sheets API

    // Constructor để khởi tạo Google Sheets API
    public GoogleSheetsService() throws GeneralSecurityException, IOException {
        JsonFactory jsonFactory = GsonFactory.getDefaultInstance(); // Tạo JsonFactory để xử lý JSON
        FileInputStream serviceAccountStream = new FileInputStream("src/main/resources/cxl203-588e03143bb5.json"); // Đường dẫn đến file JSON chứa thông tin tài khoản dịch vụ

        // Tạo đối tượng GoogleCredentials từ file JSON
        GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccountStream)
                .createScoped(Collections.singleton(SheetsScopes.SPREADSHEETS)); // Quyền truy cập vào Google Sheets

        // Sử dụng HttpCredentialsAdapter để chuyển đổi các thông tin xác thực
        HttpRequestInitializer httpRequestInitializer = new HttpCredentialsAdapter(credentials);

        // Khởi tạo sheetsService với các thông tin cần thiết
        sheetsService = new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(), jsonFactory, httpRequestInitializer)
                .setApplicationName("Your Application Name") // Tên ứng dụng của bạn
                .build();
    }

    // Phương thức để import dữ liệu từ Google Sheets vào MySQL
    public void importDataFromGoogleSheets(String spreadsheetId, String range) throws IOException {
        // Lấy dữ liệu từ Google Sheets theo ID bảng và phạm vi đã chỉ định
        ValueRange response = sheetsService.spreadsheets().values()
                .get(spreadsheetId, range)
                .execute();
        List<List<Object>> values = response.getValues(); // Lưu trữ dữ liệu nhận được từ Google Sheets

        // Kiểm tra nếu có dữ liệu
        if (values != null && !values.isEmpty()) {
            // Lặp qua từng hàng dữ liệu
            for (List<Object> row : values) {
                String userID = (row.get(0).toString()); // Lấy ID người dùng từ cột đầu tiên
                // Kiểm tra xem người dùng đã tồn tại hay chưa
                Optional<User> existingUser = userRepository.findById(userID);

                // Nếu người dùng chưa tồn tại
                if (!existingUser.isPresent()) {
                    User user = new User(); // Tạo mới đối tượng User
                    user.setUserID(userID); // Thiết lập ID người dùng
                    user.setUserName(row.get(1).toString()); // Thiết lập tên người dùng
                    user.setUserPhone(row.get(2).toString()); // Thiết lập số điện thoại
                    user.setUserMail(row.get(3).toString()); // Thiết lập email
                    user.setDbo(LocalDate.parse(row.get(4).toString())); // Thiết lập tác giả
                    userRepository.save(user); // Lưu người dùng vào cơ sở dữ liệu
                }
            }
        }
    }

    // Phương thức để export dữ liệu từ MySQL ra Google Sheets
    public void exportDataToGoogleSheets(String spreadsheetId, String range) throws IOException {
        List<User> users = userRepository.findAll(); // Lấy tất cả người dùng từ cơ sở dữ liệu
        List<List<Object>> values = new ArrayList<>(); // Danh sách để lưu trữ dữ liệu sẽ được ghi vào Google Sheets

        // Lặp qua từng người dùng để tạo danh sách hàng
        for (User user : users) {
            List<Object> row = new ArrayList<>(); // Danh sách cho một hàng
            row.add(user.getUserID()); // Thêm ID người dùng
            row.add(user.getUserName()); // Thêm tên người dùng
            row.add(user.getUserPhone()); // Thêm số điện thoại
            row.add(user.getUserMail()); // Thêm email
            row.add(user.getDbo()); // Thêm tác giả
            values.add(row); // Thêm hàng vào danh sách
        }

        ValueRange body = new ValueRange().setValues(values); // Tạo đối tượng ValueRange với dữ liệu
        // Cập nhật dữ liệu vào Google Sheets
        sheetsService.spreadsheets().values().update(spreadsheetId, range, body)
                .setValueInputOption("RAW") // Cập nhật dữ liệu theo định dạng thô
                .execute(); // Thực hiện yêu cầu
    }
}
