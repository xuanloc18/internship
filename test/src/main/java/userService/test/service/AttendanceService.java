package userService.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import userService.test.entity.Attendance;

import userService.test.entity.AttendanceSalary;
import userService.test.entity.User;
import userService.test.respository.AttendanceRepository;
import userService.test.respository.AttendanceSalaryRepository;
import userService.test.respository.UserRespository;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class AttendanceService {

    @Autowired
    AttendanceRepository attendanceRepository;
    @Autowired
    UserRespository userRepository;

    @Autowired
    AttendanceSalaryRepository attendanceSalaryRepository;

    final LocalTime timeCheckIn=LocalTime.of(7,30);
    final LocalTime timeCheckOut=LocalTime.of(17,30);


    public List<Attendance> getbyuserID(String userID){
        return attendanceRepository.findByUserId(userID);
     }
    public List<Attendance> getall(){
        return attendanceRepository.findAll();
    }


    public User finduser(String id){
        User user=userRepository.findById(id).orElseThrow(()->new RuntimeException("user not found"));
        return user;
    }

    public Attendance createattendance(String id) {
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
        attendance.setStatus(false);// Đặt trạng thái ban đầu
        if(attendance.getCheckInTime().isAfter(timeCheckIn)){
            attendance.setLateTime(Duration.between(timeCheckIn,attendance.getCheckInTime()).toMinutes()/60.0);
        }else {
            attendance.setLateTime(0.0);
        }


        attendanceRepository.save(attendance);
        return attendance;
    }
    public Attendance update(String id) {
        Attendance attendance = attendanceRepository.findByWorkDateAndUserId(LocalDate.now(), id)
                .orElseThrow(() -> new RuntimeException("Attendance not found"));

        attendance.setCheckOutTime(LocalTime.now());
        attendance.setStatus(true);
        if(attendance.getCheckOutTime().isAfter(timeCheckOut)){
            attendance.setOverTime(Duration.between(timeCheckOut, attendance.getCheckOutTime()).toMinutes()/60.0);
        }   else {
        attendance.setOverTime(0.0);
    }
        // Tính toán số giờ làm việc
        long totalMinutes = ChronoUnit.MINUTES.between(attendance.getCheckInTime(), attendance.getCheckOutTime());
        double hoursWorked = totalMinutes / 60.0; // Chia cho 60 để chuyển đổi sang giờ

        // Cập nhật bigDecimal với số giờ, giới hạn 2 chữ số thập phân
        attendance.setBigDecimal(BigDecimal.valueOf(hoursWorked).setScale(2, BigDecimal.ROUND_HALF_UP));

        attendanceRepository.save(attendance); // Ghi cập nhật đối tượng Attendance
        return attendance;
    }
    public AttendanceSalary creAttendanceSalary(String id, int month, int years, Long valueSalary) {
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
        Double sumTimeLate=0.0;
        Double sumTimeOver=0.0;
        int sumDay=0;
        // Duyệt qua các bản ghi để tính toán tổng giờ làm việc
        for (Attendance attendance : attendances) {
            totalHours = totalHours.add(attendance.getBigDecimal()); // Cộng dồn giờ làm
            sumTimeLate=sumTimeLate+attendance.getLateTime();
            sumTimeOver=sumTimeOver+attendance.getOverTime();
            sumDay++;
        }

        attendanceSalary.setUserID(user.getUserID());
        attendanceSalary.setUserName(user.getUserName());
        attendanceSalary.setMonth(month);
        attendanceSalary.setYear(years);
        attendanceSalary.setSumDay(sumDay);
        attendanceSalary.setSumLateTime(sumTimeLate);


        // Tính lương
        BigDecimal salary = totalHours.multiply(BigDecimal.valueOf(valueSalary));
        attendanceSalary.setSumTime(totalHours.doubleValue()); // Chuyển đổi sang Double nếu cần
        attendanceSalary.setSalary(salary.setScale(2, BigDecimal.ROUND_HALF_UP)); // Định dạng với 2 chữ số thập phân

        return attendanceSalaryRepository.save(attendanceSalary);
    }
    public List<AttendanceSalary> salaryList(int month,int year){
         return attendanceSalaryRepository.findListAttendanceSalary(month, year);
     }
    public BigDecimal sumsalary(int month,int year){
        BigDecimal totalHours = BigDecimal.ZERO;
         for (AttendanceSalary attendanceSalary :attendanceSalaryRepository.findListAttendanceSalary(month, year) ){
             totalHours = totalHours.add(attendanceSalary.getSalary());

         }
         return totalHours;

    }
}
