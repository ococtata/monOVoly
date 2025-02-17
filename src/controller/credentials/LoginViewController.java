package controller.credentials;

import java.util.List;

import config.CredentialsConfig;
import config.DataConfig;
import config.StatDataConfig;
import manager.GameManager;
import model.Position;
import model.entity.Player;
import security.Decryption;
import utility.FileUtil;
import view.credentials.LoginView;

public class LoginViewController {
	private LoginView loginView;
	
	public LoginViewController(LoginView loginView) {
		this.loginView = loginView;
	}
	
	public boolean loginUser(String email, String password) {
		List<String> lines = FileUtil.readFile(DataConfig.FILE_DATA_USER);
		for(String line : lines) {
			String[] credentials = line.split("#");
			String decryptedPassword = Decryption.
					passwordDecrypt(credentials[CredentialsConfig.PASSWORD_INDEX]);
			if(credentials[CredentialsConfig.EMAIL_INDEX].equals(email) && 
					decryptedPassword.equals(password)) {
				System.out.println(" Sucessfully logged in!");
				createPlayer(credentials[CredentialsConfig.ID_INDEX]);
				return true;
			}
		}
		
		return false;
	}
	
	public void createPlayer(String id) {
		String name = null;
		int level = 0, coins = 0, gems = 0, barrUpgrade = 0, currEnergy = 0;
		
		List<String> stat = FileUtil.readFile(DataConfig.FILE_DATA_PLAYER_STAT);
		List<String> cred = FileUtil.readFile(DataConfig.FILE_DATA_USER);
		for(String line : stat) {
			String[] credentials = line.split("#");
			if(credentials[CredentialsConfig.ID_INDEX].equals(id)) {
				name = credentials[CredentialsConfig.USERNAME_INDEX];
				level = Integer.parseInt(credentials[StatDataConfig.LEVEL_INDEX]);
				coins = Integer.parseInt(credentials[StatDataConfig.COINS_INDEX]);
				gems = Integer.parseInt(credentials[StatDataConfig.GEMS_INDEX]);
				barrUpgrade = Integer.parseInt(credentials[StatDataConfig.BARRACKS_UPGRADE_INDEX]);
				currEnergy = Integer.parseInt(credentials[StatDataConfig.CURRENT_ENERGY_INDEX]);
			}
		}
		
		for(String line : cred) {
			String[] credentials = line.split("#");
			if(credentials[CredentialsConfig.ID_INDEX].equals(id)) {
				name = credentials[CredentialsConfig.USERNAME_INDEX];
			}
		}
		
		Player newPlayer = new Player(name, 0, id, level, coins, 
				gems, barrUpgrade,currEnergy);
		GameManager.getInstance().setPlayer(newPlayer);
	}
	
	public boolean thereIsUserRegistered() {
		List<String> lines = FileUtil.readFile(DataConfig.FILE_DATA_PLAYER_STAT);
		return !(lines.isEmpty());
	}
}
