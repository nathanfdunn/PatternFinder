package tests;

import behaviorClassification.ChunkList;
import behaviorClassification.ClassifiedChunkList;
import behaviorClassification.CsvToTable;
import behaviorClassification.RawTimeSeriesTable;

public class CsvReaderTest {

	public static void main(String[] args) {
		String fileName = "../../DataSets_R/nfdunn_Moulton1.csv";
		RawTimeSeriesTable table = CsvToTable.readCsv(fileName);
		Pnt.pnt(table);
		
		Pnt.pnt(table.timeSpan());
		Pnt.pnt(table.getForwardIndicator());
//		ChunkList cl = new ChunkList(table, 20);
//		Pnt.pnt(cl);
//		
//		ClassifiedChunkList ccl = new ClassifiedChunkList();
		
		
	}

}
