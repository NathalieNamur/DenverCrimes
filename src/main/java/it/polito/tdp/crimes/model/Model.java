package it.polito.tdp.crimes.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.crimes.db.EventsDao;

public class Model {
	
	private EventsDao dao;
	
	private List<String> categories; 
	private List<Integer> months; 
	
	private Graph<String,DefaultWeightedEdge> grafo;

	private List<Adiacenza> archi; 
	
	
	
	public Model() {
		
		dao = new EventsDao();
		
		categories = new ArrayList<>();
		categories = dao.getAllCategories();
		
		months = new ArrayList<>();
		months = dao.getAllMonths();
	}


	
	//METODO CHE RESTITUISCE LA LISTA DELLE CATEGORIE:
	public List<String> getCategories() {
		return categories;
	}

	//METODO CHE RESTITUISCE LA LISTA DEI MESI:
	public List<Integer> getMonths() {
		Collections.sort(months);
		return months;
	}
	
	
	//METODO DI CREAZIONE E POPOLAMENTO GRAFO:
	public void creaGrafo (String category, Integer month) {
		
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(grafo,dao.getVertici(category, month));
	
		for(Adiacenza a : dao.getAdiacenze(category, month))
			Graphs.addEdgeWithVertices(grafo, a.getV1(), a.getV2(), a.getPeso());
	
	}

	public int getNumVertici() {
		return grafo.vertexSet().size();
	}

	public int getNumArchi() {
		return grafo.edgeSet().size();
	}
	

	//METODO CHE RESTITUISCE LA LISTA DELLE ADIACENZE (ARCHI)
	//AVENTI PESO MAGGIORE DEL PESO MEDIO DEL GRAFO:
	public List<Adiacenza> getArchiPesoMaggiore(){
		
		int pesiTot = 0; 
		
		for(DefaultWeightedEdge e : grafo.edgeSet())
			pesiTot += grafo.getEdgeWeight(e);
		
		int numArchi = grafo.edgeSet().size();
		int pesoMedio = pesiTot/numArchi;
		
		
		List<Adiacenza> result = new ArrayList<>();
		
		for(DefaultWeightedEdge e : grafo.edgeSet()) 
			if(grafo.getEdgeWeight(e) > pesoMedio) {
				
				Adiacenza a = new Adiacenza(grafo.getEdgeSource(e),
											grafo.getEdgeTarget(e), 
											(int)grafo.getEdgeWeight(e));
				result.add(a);
		}
				
		
		return result; 
		
	}

	
}
