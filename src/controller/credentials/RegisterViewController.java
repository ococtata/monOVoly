package controller.credentials;

import java.util.ArrayList;
import java.util.List;

import config.CredentialsConfig;
import config.DataConfig;
import config.SecurityConfig;
import manager.GameManager;
import manager.SaveLoadManager;
import model.entity.Player;
import security.Encryption;
import utility.FileUtil;
import utility.TextUtil;
import view.credentials.RegisterView;

public class RegisterViewController {
	private RegisterView registerView;
	
	public RegisterViewController(RegisterView registerView) {
		this.registerView = registerView;
	}
	
	public boolean registerUser(String username, String email, String password) {
		String id = generateUserId();
		String ecryptedPassword = Encryption.encryptPassword(password);
		
		String userData = String.format("%s#%s#%s#%s", id, username, email, ecryptedPassword);
		FileUtil.appendToFile(DataConfig.FILE_DATA_USER, userData);
		
		String playerStat = String.format("%s#1#0#0#0#0#0", id);	
		FileUtil.appendToFile(DataConfig.FILE_DATA_PLAYER_STAT, playerStat);
		
		SaveLoadManager.getInstance().loadAll();
		System.out.println(" User successfully registered!");
		TextUtil.pressEnter();
		return true;
	}
	
	public boolean isEmailRegistered(String email) {
		List<String> lines = FileUtil.readFile(DataConfig.FILE_DATA_USER);
		for(String line : lines) {
			String[] credentials = line.split("#");
			if(credentials[CredentialsConfig.EMAIL_INDEX].equals(email)) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean isUsernameRegistered(String username) {
		List<String> lines = FileUtil.readFile(DataConfig.FILE_DATA_USER);
		for(String line : lines) {
			String[] credentials = line.split("#");
			if(credentials[CredentialsConfig.USERNAME_INDEX].equals(username)) {
				return true;
			}
		}
		
		return false;
	}
	
	private String generateUserId() {
		List<String> lines = FileUtil.readFile(DataConfig.FILE_DATA_USER);
		int userCount = lines.size() + 1;
		
		return String.format("OV%03d", userCount);
	}
	
	public boolean isEmailFormatValid(String email) {
		if(email.contains(".com") && email.contains("@")) return true;
		return false;
	}
	
	private boolean isStringLengthValid(String string, int min) {
		if(string.length() < min) return true;
		return false;
	}
}
