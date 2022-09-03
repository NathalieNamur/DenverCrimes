package it.polito.tdp.crimes.db;

public class TestDao {

	public static void main(String[] args) {
		
		EventsDao dao = new EventsDao();
		
		System.out.println("Caterories: "+dao.getAllCategories().size());
		
		System.out.println("Months: "+dao.getAllMonths().size());
		
		System.out.println("Vertici (robbery - 1): "+dao.getVertici("robbery", 1).size());
	}

}
