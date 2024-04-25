package excel;


import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import excel.vo.ExcelVO;

public class PoiTutorial1 {
	// Poi : Excel Data
	public static void main(String[] args) {
		String sysPath = System.getProperty("user.dir");
		String fileName = sysPath + "/src/excel/resources/bookList.xls";
		List<ExcelVO> data = new ArrayList<ExcelVO>();
		
		try(FileInputStream fis = new FileInputStream(fileName)){
			
			HSSFWorkbook workBook = new HSSFWorkbook(fis);
			// excel파일의 0번째 시트를 가져온다
			HSSFSheet sheet = workBook.getSheetAt(0);
			
			// excel파일의 Rows를 가져온다
			Iterator<Row> rows = sheet.rowIterator();
			rows.next(); // 0번째 row는 컬럼명이기 때문에 건너뛴다
			
			// Excel Data를 담아놓을 임시배열
			String[] temp = new String[5];
			while(rows.hasNext()) { // excel파일에 row가 존재하면 true
				HSSFRow row = (HSSFRow)rows.next();
				Iterator<Cell> cells = row.cellIterator();
				int i = 0;
				
				while(cells.hasNext()) { // 해당 row의 cell의 데이터가 존재하면 true
					HSSFCell cell = (HSSFCell) cells.next();
					temp[i] = cell.toString();
					i++;
				}
				// 임시로 담아놓은 배열의 데이터를 VO형식으로 묶어준다
				ExcelVO excelVO = new ExcelVO(temp[0], temp[1], temp[2], temp[3], temp[4]);
				// VO형식으로 묶인 데이터를 ArrayList에 추가한다
				data.add(excelVO);
			}
			
			showExcelData(data);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public static void showExcelData(List<ExcelVO> data) {
		for(ExcelVO excelVO : data) {
			System.out.println(excelVO.toString());
		}
	}

}
