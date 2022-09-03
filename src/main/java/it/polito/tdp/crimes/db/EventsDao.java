package it.polito.tdp.crimes.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.polito.tdp.crimes.model.Adiacenza;
import it.polito.tdp.crimes.model.Event;


public class EventsDao {
	
	//METODO CHE RESTITUISCE LA LISTA DI TUTTE LE CATEGORIE DI REATO DEL DB:
	public List<String> getAllCategories(){
		
		String sql = "SELECT DISTINCT offense_category_id "
				   + "FROM events "
				   + "ORDER BY offense_category_id";
		
		List<String> result = new ArrayList<>();
		
		try {
			
			Connection conn = DBConnect.getConnection();

			PreparedStatement st = conn.prepareStatement(sql);
			
			
			ResultSet res = st.executeQuery();
			while(res.next()) {
				result.add(res.getString("offense_category_id"));
			}
			
			conn.close();
			st.close();
			
			return result;

		} catch (SQLException e) {
			System.out.print("Errore nel metodo getAllCategories().");
			e.printStackTrace();
			return null ;
		}
	}
	
	
	//METODO CHE RESTITUISCE LA LISTA DI TUTTI I MESI DELLE DATE DEL DB:
	public List<Integer> getAllMonths(){
		
		String sql = "SELECT DISTINCT MONTH(reported_date) AS month "
				   + "FROM events";
		
		List<Integer> result = new ArrayList<>();
		
		try {
			
			Connection conn = DBConnect.getConnection();

			PreparedStatement st = conn.prepareStatement(sql);
			
			
			ResultSet res = st.executeQuery();
			while(res.next()) {
				result.add(res.getInt("month"));
			}
			
			conn.close();
			st.close();
			
			return result;

		} catch (SQLException e) {
			System.out.print("Errore nel metodo getAllMonths().");
			e.printStackTrace();
			return null ;
		}
	}
	
	
	//METODO CHE RESTITUISCE LA LISTA DEI VERTICI (CORRISPONDENTI AI VINCOLI):
	public List<String> getVertici(String category, Integer month){
		
		String sql = "SELECT DISTINCT offense_type_id "
				   + "FROM events "
				   + "WHERE offense_category_id = ? AND MONTH(reported_date) = ?";
		
		List<String> result = new ArrayList<>();
		
		try {
			
			Connection conn = DBConnect.getConnection();

			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, category);
			st.setInt(2, month);
			
			
			ResultSet res = st.executeQuery() ;
			while(res.next()) {
				result.add(res.getString("offense_type_id"));
			}
			
			conn.close();
			st.close();
			
			return result;

		} catch (SQLException e) {
			System.out.print("Errore nel metodo getVertici().");
			e.printStackTrace();
			return null ;
		}
	}
	
	
	//METODO CHE RESTITUISCE LA LISTA DEGLI ARCHI (ADIACENZE):
	public List<Adiacenza> getAdiacenze(String category, Integer month){
		
		String sql = "SELECT e1.offense_type_id AS v1, e2.offense_type_id AS v2, COUNT(DISTINCT e1.neighborhood_id) AS peso "
				   + "FROM events e1, events e2 "
				   + "WHERE e1.offense_category_id = ? AND e2.offense_category_id = e1.offense_category_id "
				   + "AND MONTH(e1.reported_date) = ? AND MONTH(e2.reported_date) = MONTH(e1.reported_date) "
				   + "AND e1.offense_type_id > e2.offense_type_id "
				   + "AND e1.neighborhood_id = e2.neighborhood_id "
				   + "GROUP BY e1.offense_type_id, e2.offense_type_id";
		
		List<Adiacenza> result = new ArrayList<>();
		
		try {
			
			Connection conn = DBConnect.getConnection();

			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, category);
			st.setInt(2, month);
			
			
			ResultSet res = st.executeQuery() ;
			while(res.next()) {
				
				Adiacenza a = new Adiacenza(res.getString("v1"),
											res.getString("v2"),
											res.getInt("peso"));
				
				result.add(a);
			}
			
			conn.close();
			st.close();
			
			return result;

		} catch (SQLException e) {
			System.out.print("Errore nel metodo getAdiacenze().");
			e.printStackTrace();
			return null ;
		}
	}


	/**
	public List<Event> listAllEvents(){
		String sql = "SELECT * FROM events" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Event> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Event(res.getLong("incident_id"),
							res.getInt("offense_code"),
							res.getInt("offense_code_extension"), 
							res.getString("offense_type_id"), 
							res.getString("offense_category_id"),
							res.getTimestamp("reported_date").toLocalDateTime(),
							res.getString("incident_address"),
							res.getDouble("geo_lon"),
							res.getDouble("geo_lat"),
							res.getInt("district_id"),
							res.getInt("precinct_id"), 
							res.getString("neighborhood_id"),
							res.getInt("is_crime"),
							res.getInt("is_traffic")));
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	*/
	
	
}
