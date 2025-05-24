package utils;

import java.time.LocalDate;

public class Constants {

	public static final int Col_Index =0 ;
	public static final int Col_TestName =1 ;
	public static final int Col_Result =2 ;
	public static final int Col_Time =3 ;
	
	public static final String Test_Result_Path = "Output/TestResult/";
	public static final String Test_File_Name = "TestResult" + LocalDate.now() + ".xlsx";
	public static final String Sheet_Name = "Test Result";
}
