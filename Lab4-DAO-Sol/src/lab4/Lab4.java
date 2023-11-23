package lab4;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Lab4 {
   
	private static CompanyDao compDao = new CompanyDao();
    
	public static void main(String[] args) {		
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("부서명을 입력하시오: ");
		String deptName = scanner.next();
        System.out.println();
		
	    Department dept = compDao.findDeptByName(deptName);  	    
		// dept 객체의 필드 값 출력
	    if (dept != null) {  	// 질의 결과 존재 확인 필요! (최대 하나의 행)
            System.out.println("<부서정보>");
            System.out.println("부서번호: " + dept.getDeptNo());
            System.out.println("부서명: " + deptName);
            System.out.println("관리자사번: " + dept.getMgrNo());
            System.out.println("사원 수: " + dept.getNumOfEmps());
            System.out.println();
        }          
		
	    List<Employee> empList = compDao.getAllEmpsInDept(dept.getDeptNo());  	    
		if (empList.isEmpty() == false) {		// Employee 객체 존재			
			// empList에 포함된 모든 Employee 객체들의 필드 값을 출력 
		    // Employee 객체들을 하나씩 접근하기 위해 empList로부터 Iterator<Employee>를 구해서 활용
		   		    
		    System.out.println("사번        이름           직무          급여         수당");
	        System.out.println("------------------------------------------------------");	        
		    Iterator<Employee> iter = empList.iterator();
	        while (iter.hasNext()) {     	
	        	Employee emp = iter.next();
		        System.out.printf("%d %10s %15s %10.2f %10.2f\n", 
	            	emp.getEmpNo(), emp.getEmpName(), emp.getJob(), emp.getSal(), emp.getComm());
	        }
	        System.out.println();
	        
			System.out.print("새 관리자 사번과 관리자 보직수당을 입력하시오: ");
			int managerNo  = scanner.nextInt();
			double commission = scanner.nextDouble();
			
			Appointment appo = new Appointment(dept.getDeptNo(), managerNo, commission);		
		    Employee oldMgr = compDao.replaceManagerOfDept(appo);	 	       
		    
	        System.out.println("기존 관리자: "); // oldMgr의 필드 값 출력
	        System.out.println(oldMgr);
	        
			Employee newMgr = compDao.findEmployee(managerNo);   
	        System.out.println("새 관리자:");  // newMgr의 필드 값 출력 
	        System.out.println(newMgr);
	    }	    
		scanner.close();
	}
}