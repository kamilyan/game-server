package com.hit.driver;

import com.hit.controller.Controller;
import com.hit.controller.GamesController;
import com.hit.model.GamesModel;
import com.hit.model.Model;
import com.hit.view.GamesView;
import com.hit.view.View;

public class GamesClientDriver {
	public GamesClientDriver() {
		
	}
	public static void main(String[] args){  
		Model model = new GamesModel();  
		View view = new GamesView(); 
		Controller controller = new GamesController(model, view); 
		((GamesModel)model).addPropertyChangeListener(controller); 
		((GamesView)view).addPropertyChangeListener(controller); 
		view.start();
		}
}
