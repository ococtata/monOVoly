package view.game.monovoly;

import controller.game.monovoly.MonovolyMapController;
import view.BaseView;

public class MonovolyMap extends BaseView{
	private MonovolyMapController monovolyMapController;
	
	public MonovolyMap() {
		this.monovolyMapController = new MonovolyMapController(this);
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

}
