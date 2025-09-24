# Console-Based-PayRollSystem
Payroll Management System (Java + MySQL) A simple Payroll Management System built using Java (JDBC + OOPs) and MySQL Database. This project allows adding employee details, managing salary, deductions, and generating payroll records in a structured way.

#MySql Queries
* create database payrollsys;
use payrollsys;

* create table employee (id int primary key auto_increment,EmployeeName varchar(200),EmployeePost varchar(200),EmployeePhoneNo long,BasicSalary long);
 select * from employee;
 
* create table payrollTB(Payrollid int primary key auto_increment,EmployeeName varchar(200),BasicSalary long,Deductions long,NetSalary long,PaymentDate varchar(200));
 select * from payrollTB;
