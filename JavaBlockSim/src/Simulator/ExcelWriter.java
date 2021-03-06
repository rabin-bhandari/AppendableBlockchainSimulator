package Simulator;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelWriter {
	
	public static void printToExcel(int simulationRunNumber, boolean detailReport) {
		
		// Generating DataFrames	
		ArrayList<Object[]> df1 = new ArrayList<>();
		df1.add(new Object[]{
				"No. of Gateways",
				"Total No. of Devices",
				"Total No. of Blocks",
				"Blocks per Chain",
				"Max TX List Size",
				"Total Transactions",
				"Average Transaction Latency",
				"Transaction Throughput",
				"Simulation Duration (secs)"});
		df1.add(new Object[]{
				InputConfig.getGn(),
				InputConfig.getGn()*InputConfig.getDn(),
				Statistics.total_blocks,
				Statistics.total_blocks/InputConfig.getGn(),
				InputConfig.getMaxTxListSize(),
				InputConfig.getGn()*InputConfig.getDn()*InputConfig.getTn(),
				Statistics.averageTransactionLatency,
				Statistics.transactionThroughput,
				Statistics.simulationDuration});
		

		ArrayList<Object[]> df2 = new ArrayList<>();
		df2.add(new Object[]{
				"No. of Gateways",
				"No. of Devices per Gateway",
				"Total No. of Devices",
				"Total No. of Nodes",
				"Transactions per Device"});
		df2.add(new Object[]{
				InputConfig.getGn(),
				InputConfig.getDn(),
				InputConfig.getGn()*InputConfig.getDn(),
				InputConfig.getNn(),
				InputConfig.getTn()});
		
		// Initialising new workbook
		XSSFWorkbook workbook = new XSSFWorkbook();
		
		// writing data frames to workbook
		writeData(df1, workbook, "Results");
		writeData(df2, workbook, "InputConfig");
		
		// Additional Data frames for detailed reports
		if (detailReport) {
			ArrayList<Object[]> df3 = new ArrayList<>();
			df3.add(new Object[]{
					"Gateway Node ID",
					"Block Depth",
					"Block ID",
					"Previous Block ID",
					"Block Timestamp",
					"No. of Transactions"});
			for (Object[] chain : Statistics.getChains()) {
				df3.add(chain);
			}
			
			ArrayList<Object[]> df4 = new ArrayList<>();
			df4.add(new Object[]{
					"Gateway Node Id",
					"Tx ID",
					"Sender Node ID",
					"Receiver Node ID",
					"Tx Creation Time",
					"Tx Reception Time",
					"Tx Insertion Time"});
			for (Object[] transaction : Statistics.getTransactions()) {
				df4.add(transaction);
			}
			
			ArrayList<Object[]> df5 = new ArrayList<>();
			df5.add(new Object[]{
					"TxID",
					"Latency",
					});
			for (Object[] transactionLatency : Statistics.getTransactionLatencies()) {
				df5.add(transactionLatency);
			}
			
			// Writing additional data frames to workbook
			writeData(df3, workbook, "GatewayBlockchains");
			writeData(df4, workbook, "GatewayTransactions");
			writeData(df5, workbook, "TransactionLatencies");
		}
				
		// Generate filename
		String fname = "Statistics-" + 
				LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyy-HH:mm:ss")) + 
				"-" + 
				(simulationRunNumber+1)+
				".xlsx";
		
		// Write to file
		try (FileOutputStream outputStream = new FileOutputStream(fname)) {
			workbook.write(outputStream);
			outputStream.close();
			
			// Open file
			Desktop.getDesktop().open(new File(fname));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	// Writes each Array within Data frame as a Row in the excel sheet.
	private static void writeData(ArrayList<Object[]> DataFrame, XSSFWorkbook workbook, String sheetName) {
				
		XSSFSheet sheet = workbook.createSheet(sheetName);
		
		int rowCount = 0;
		
		for (Object[] rowData : DataFrame) {
			XSSFRow row = sheet.createRow(++rowCount);
			
			int columnCount = 0;
			for (Object field : rowData) {
				XSSFCell cell = row.createCell(++columnCount);
				if (field instanceof String) {
					cell.setCellValue((String) field);
				} else if (field instanceof Integer) {
					cell.setCellValue((Integer) field);
				} else if (field instanceof Double) {
					cell.setCellValue((Double) field);
				} else if (field instanceof Long) {
					cell.setCellValue((Long) field);
				}
			}
		}
		
		// Basic aesthetic formating of Excel Sheet
		formatExcelSheet(DataFrame.get(0).length, sheet, workbook);

	}
	
	// Aesthetic formating of excel sheet
	private static void formatExcelSheet(int columns, XSSFSheet sheet, XSSFWorkbook workbook) {

		
		// Creating header font.
		XSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short)11);
		font.setBold(true);
		font.setColor(IndexedColors.WHITE.getIndex());
		
		//Setting header font and header filling.
		XSSFCellStyle style = workbook.createCellStyle();
		style.setFont(font);
		style.setFillBackgroundColor(IndexedColors.BLACK.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		
		XSSFRow header = sheet.getRow((short) 1);
		// Setting style for each cell in the header row.
		for (int i =1; i<header.getLastCellNum();i++) {
		header.getCell(i).setCellStyle(style);
	}
		
		// Resize column widths 
		for (int i = 1; i < columns+1; i++) {
			sheet.autoSizeColumn(i);
		}
	}

}
