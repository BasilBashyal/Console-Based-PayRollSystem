package com.payRollSysPack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

class Employee {
    private String employeeName;
    private String employeePost;
    private long employeePhoneNo;
    private long basicSalary;

    public Employee(String employeeName, String employeePost, long employeePhoneNo, long basicSalary) {
        this.employeeName = employeeName;
        this.employeePost = employeePost;
        this.employeePhoneNo = employeePhoneNo;
        this.basicSalary = basicSalary;
    }

    public String getEmployeeName() { return employeeName; }
    public String getEmployeePost() { return employeePost; }
    public long getEmployeePhoneNo() { return employeePhoneNo; }
    public long getBasicSalary() { return basicSalary; }
}

class PayRoll {
    private String employeeName;
    private long basicSalary;
    private long deductions;
    private long netSalary;
    private String paymentDate;

    public PayRoll(String employeeName, long basicSalary, long deductions, long netSalary, String paymentDate) {
        this.employeeName = employeeName;
        this.basicSalary = basicSalary;
        this.deductions = deductions;
        this.netSalary = netSalary;
        this.paymentDate = paymentDate;
    }

    public String getEmployeeName() { return employeeName; }
    public long getBasicSalary() { return basicSalary; }
    public long getDeductions() { return deductions; }
    public long getNetSalary() { return netSalary; }
    public String getPaymentDate() { return paymentDate; }
}

public class payRollSys {

    // Method to safely get long input
    private static long getValidLong(Scanner sc, String message) {
        System.out.println(message);
        while (!sc.hasNextLong()) {
            System.out.println(" Invalid input! Please enter digits only.");
            sc.next(); // discard wrong input
        }
        long value = sc.nextLong();
        sc.nextLine(); // clear buffer
        return value;
    }

    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);
        Connection conn = null;

        try {
            String url = "jdbc:mysql://localhost:3306/payrollsys"; // DB must exist
            String user = "root";
            String password = "MACINTOSH100";

            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
            System.out.println(" Database Connected Successfully!");

        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        // ----- Employee Entry -----
        System.out.println("\n--- Enter Employee Details ---");
        System.out.print("Enter Employee Name: ");
        String empName = sc.nextLine();

        System.out.print("Enter Employee Post: ");
        String empPost = sc.nextLine();

        long empPhone = getValidLong(sc, "Enter Employee Phone No:");
        long empBscSal = getValidLong(sc, "Enter Basic Salary:");

        Employee employee = new Employee(empName, empPost, empPhone, empBscSal);

        String empDB = "INSERT INTO employee(EmployeeName, EmployeePost, EmployeePhoneNo, BasicSalary) VALUES (?,?,?,?)";
        try (PreparedStatement pstm = conn.prepareStatement(empDB)) {
            pstm.setString(1, employee.getEmployeeName());
            pstm.setString(2, employee.getEmployeePost());
            pstm.setLong(3, employee.getEmployeePhoneNo());
            pstm.setLong(4, employee.getBasicSalary());
            pstm.executeUpdate();
            System.out.println("Employee Successfully Inserted");
        }

        // ----- Payroll Entry -----
        System.out.println("\n--- Enter Payroll Details ---");
        System.out.print("Enter Employee Name (for Payroll): ");
        String empNames = sc.nextLine();

        long bscSal = getValidLong(sc, "Enter Basic Salary:");
        long Ded = getValidLong(sc, "Enter Deductions:");
        long netSal = bscSal - Ded; // auto calculate Net Salary
        sc.nextLine(); // clear buffer

        System.out.print("Enter Payment Date (YYYY-MM-DD): ");
        String payDate = sc.nextLine();

        PayRoll payRoll = new PayRoll(empNames, bscSal, Ded, netSal, payDate);

        String ProllDB = "INSERT INTO payrollTB(EmployeeName, BasicSalary, Deductions, NetSalary, PaymentDate) VALUES (?,?,?,?,?)";
        try (PreparedStatement pstm1 = conn.prepareStatement(ProllDB)) {
            pstm1.setString(1, payRoll.getEmployeeName());
            pstm1.setLong(2, payRoll.getBasicSalary());
            pstm1.setLong(3, payRoll.getDeductions());
            pstm1.setLong(4, payRoll.getNetSalary());
            pstm1.setString(5, payRoll.getPaymentDate());
            pstm1.executeUpdate();
            System.out.println("Payroll Successfully Inserted");
        }

        // Close connection
        conn.close();
        sc.close();
    }
}
