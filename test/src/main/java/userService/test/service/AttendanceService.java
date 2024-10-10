package userService.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import userService.test.entity.Attendance;

import userService.test.entity.AttendanceSalary;
import userService.test.entity.User;
import userService.test.respository.AttendanceRepository;
import userService.test.respository.UserRepository;
import userService.test.respository.attendanceSalaryRepository;

import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AttendanceService {

    @Autowired
    AttendanceRepository attendanceRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    attendanceSalaryRepository attendanceSalaryRepository;

     public List<Attendance> getbyuserID(Long userID){
        return attendanceRepository.findByUserId(userID);
     }
    public List<Attendance> getall(){
        return attendanceRepository.findAll();
    }
    public User finduser(Long id){
        User user=userRepository.findById(id).orElseThrow(()->new RuntimeException("user not found"));
        return user;
    }

    public Attendance createattendance(Long id) {
        // Kiểm tra xem đã có bản ghi Attendance nào cho ngày hôm nay và user chưa
        attendanceRepository.findByWorkDateAndUserId(LocalDate.now(), id)
                .ifPresent(a -> { // Nếu đã tồn tại, ném ngoại lệ
                    throw new RuntimeException("Attendance has already been created for today");
                });

        User user = finduser(id);
        Attendance attendance = new Attendance();
        attendance.setCheckInTime(LocalTime.now());
        attendance.setCheckOutTime(null); // Không nên đặt checkOutTime ở đây
        attendance.setUserID(user.getUserID());
        attendance.setWorkDate(LocalDate.now());
        attendance.setStatus(false); // Đặt trạng thái ban đầu

        attendanceRepository.save(attendance);
        return attendance;
    }



    public Attendance update(Long id) {
        Attendance attendance = attendanceRepository.findByWorkDateAndUserId(LocalDate.now(), id)
                .orElseThrow(() -> new RuntimeException("Attendance not found"));

        attendance.setCheckOutTime(LocalTime.now());
        attendance.setStatus(true);

        // Tính toán số giờ làm việc
        long totalMinutes = ChronoUnit.MINUTES.between(attendance.getCheckInTime(), attendance.getCheckOutTime());
        double hoursWorked = totalMinutes / 60.0; // Chia cho 60 để chuyển đổi sang giờ

        // Cập nhật bigDecimal với số giờ, giới hạn 2 chữ số thập phân
        attendance.setBigDecimal(BigDecimal.valueOf(hoursWorked).setScale(2, BigDecimal.ROUND_HALF_UP));

        attendanceRepository.save(attendance); // Ghi cập nhật đối tượng Attendance
        return attendance;
    }
    public AttendanceSalary creAttendanceSalary(Long id, int month, int years, Long valueSalary) {
        AttendanceSalary attendanceSalary = new AttendanceSalary();
        // Tìm kiếm danh sách AttendanceSalary
        Optional<AttendanceSalary> attendanceSalaryList = attendanceSalaryRepository.findAttendanceSalary(id, years, month);

        // Nếu danh sách không rỗng, xóa bản ghi cũ (nếu cần)
        if (!attendanceSalaryList.isEmpty()) {
            // Giả sử bạn muốn xóa bản ghi đầu tiên trong danh sách
            attendanceSalary=attendanceSalaryList.get();
        }
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("user not found"));
        List<Attendance> attendances = attendanceRepository.findByUserIdAndMonthAndYear(id, years, month);

        BigDecimal totalHours = BigDecimal.ZERO; // Tổng giờ làm việc

        // Duyệt qua các bản ghi để tính toán tổng giờ làm việc
        for (Attendance attendance : attendances) {
            totalHours = totalHours.add(attendance.getBigDecimal()); // Cộng dồn giờ làm
        }

        attendanceSalary.setUserID(user.getUserID());
        attendanceSalary.setUserName(user.getUserName());
        attendanceSalary.setMonth(month);
        attendanceSalary.setYear(years);

        // Tính lương
        BigDecimal salary = totalHours.multiply(BigDecimal.valueOf(valueSalary));
        attendanceSalary.setSumTime(totalHours.doubleValue()); // Chuyển đổi sang Double nếu cần
        attendanceSalary.setSalary(salary.setScale(2, BigDecimal.ROUND_HALF_UP)); // Định dạng với 2 chữ số thập phân

        return attendanceSalaryRepository.save(attendanceSalary);
    }









}
