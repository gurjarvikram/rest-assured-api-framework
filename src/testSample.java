import java.io.IOException;
import java.util.ArrayList;

import resources.DataDriven;

public class testSample {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		DataDriven d = new DataDriven();
		ArrayList data = d.getData("Add Profile","testDataSheet");
		
		System.out.println(data.get(0));
		System.out.println(data.get(1));
		System.out.println(data.get(2));
		System.out.println(data.get(3));
	}

}
