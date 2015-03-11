package tests;

import behaviorClassification.ChunkList;
import behaviorClassification.ClassifiedChunkList;
import behaviorClassification.CsvToTable;
import behaviorClassification.RawDataTable;

public class CsvReaderTest {

	public static void main(String[] args) {
		String fileName = "../../DataSets_R/nfdunn_Moulton1.csv";
		RawDataTable o = CsvToTable.readCsv(fileName);
		Pnt.pnt(o);
		
		ChunkList cl = new ChunkList(o, 20);
		Pnt.pnt(cl);
		
		ClassifiedChunkList ccl = new ClassifiedChunkList();
		
		
	}

}
