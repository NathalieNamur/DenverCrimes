/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.crimes;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.crimes.model.Adiacenza;
import it.polito.tdp.crimes.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class FXMLController {
	
	private Model model;

	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> boxCategoria;

    @FXML
    private ComboBox<Integer> boxMese;

    @FXML
    private Button btnAnalisi;

    @FXML
    private ComboBox<?> boxArco;

    @FXML
    private Button btnPercorso;

    @FXML
    private TextArea txtResult;

    
    
    @FXML
    void doCalcolaPercorso(ActionEvent event) {

    }

    
    @FXML
    void doCreaGrafo(ActionEvent event) {

    	txtResult.clear();
    	
    	
    	String category = boxCategoria.getValue();
    	Integer month = boxMese.getValue();
    	
    	if ((category == null) || (month == null)) {
    		txtResult.setText("Selezionare categoria e mese.");
    		return;
    	}
    	
    	model.creaGrafo(category, month);
    	
    	txtResult.setText("Grafo creato.");
    	txtResult.appendText("\nNumero di vertici grafo: "+model.getNumVertici());
    	txtResult.appendText("\nNumero di archi grafo: "+model.getNumArchi());
    	
    	
    	txtResult.appendText("\n\nArchi con peso maggiore del peso medio:\n");
    	for(Adiacenza a : model.getArchiPesoMaggiore())
    		txtResult.appendText(a.toString()+"\n");
    }

    
    
    @FXML
    void initialize() {
        assert boxCategoria != null : "fx:id=\"boxCategoria\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxMese != null : "fx:id=\"boxMese\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxArco != null : "fx:id=\"boxArco\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnPercorso != null : "fx:id=\"btnPercorso\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    
    public void setModel(Model model) {
    	
    	this.model = model;
    	
    	boxCategoria.getItems().addAll(model.getCategories());
    	boxMese.getItems().addAll(model.getMonths());
    }
}
