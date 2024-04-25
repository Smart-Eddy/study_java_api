package excel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;

public class PoiTutorial2 {
	// Poi : Excel Image
	public static void main(String[] args) {
		
		try {
			Workbook wb = new HSSFWorkbook();
			// Sheet 생성
			Sheet sheet = wb.createSheet("My Sample Excel");
			// Sheet에 그릴 이미지 파일에 InputStream 연결
			InputStream is = new FileInputStream(System.getProperty("user.dir") + "/src/excel/resources/java_book.jpg");
			byte[] bytes = IOUtils.toByteArray(is); // poi에서 제공되는 메서드, 데이터를 byte단위로 읽고 byte[]로 리턴한다.
			
			// addPicture(이미지의 byte[], 이미지의 Type) 메서드로 Workbook에 이미지를 추가한다.
			// Excel 파일에 여러 이미지가 있을 수 있기 때문에 반환받은 pictureIdx값으로 각 이미지를 식별할 수 있다. 
			int pictureIdx = wb.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG); 
			is.close();
			
			// Workbook내에서 여러가지 객체를 생성하거나 이미지삽입 셀 형식 설정 등을 도와주는 객체들
			CreationHelper helper = wb.getCreationHelper();
			Drawing drawing = sheet.createDrawingPatriarch();
			ClientAnchor anchor = helper.createClientAnchor();
			
			// image파일을 그릴 anchor를 지정한다.
			anchor.setCol1(1);
			anchor.setRow1(2);
			anchor.setCol2(2);
			anchor.setRow2(3);
			
			// 지정한 영역(anchor)에 이미지 생성
			Picture pict =  drawing.createPicture(anchor, pictureIdx);
			
			// 2행 1열에 cell생성
			Cell cell = sheet.createRow(2).createCell(1);
			int with = 20 * 256; // 20 픽셀만큼 너비 지정
			sheet.setColumnWidth(1, with);
			short height = 120 * 20; // 120 픽셀만큼 높이 지정
			cell.getRow().setHeight(height);
			// 내보낼 파일명(경로)로 FileOutputStream 생성
			FileOutputStream fos = new FileOutputStream(System.getProperty("user.dir") + "/src/excel/resources/myExcelFile.xls");
			// 셋팅이 끝난 Workbook에 FileOutputStream을 연결 후 내보내기
			wb.write(fos);
			fos.close();
			System.out.println("Excel파일이 생성되었습니다!");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
}
