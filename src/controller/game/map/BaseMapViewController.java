package controller.game.map;

import manager.GameManager;
import model.Position;
import view.BaseView;

public abstract class BaseMapViewController {
	protected void transitionToView(BaseView newView) {
        newView.setPreviousView(newView);;
        GameManager.getInstance().setCurrentView(newView);
        newView.show(); 
    }
	
	public abstract boolean checkCollision(int newX, int newY);
}
