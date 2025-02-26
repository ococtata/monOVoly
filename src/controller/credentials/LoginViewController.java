package controller.credentials;

import java.util.List;

import config.CredentialsConfig;
import config.DataConfig;
import config.StatDataConfig;
import manager.GameManager;
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
        if (lines == null) return false;

        for (String line : lines) {
            String[] credentials = line.split("#");
            String decryptedPassword = Decryption.passwordDecrypt(credentials[CredentialsConfig.PASSWORD_INDEX]);
            if (credentials[CredentialsConfig.EMAIL_INDEX].equals(email) &&
                    decryptedPassword.equals(password.trim())) {
            	loadPlayerFromGameManager(credentials[CredentialsConfig.ID_INDEX]);
                System.out.println(" Successfully logged in!");
                return true;
            }
        }

        return false;
    }

    private void loadPlayerFromGameManager(String id) {
        List<Player> players = GameManager.getInstance().getPlayers();
        if (players != null) {
            for (Player player : players) {
                if (player.getId().equals(id)) {
                    GameManager.getInstance().setPlayer(player);
                    return;
                }
            }
        }

    }

    public boolean thereIsUserRegistered() {
        List<String> lines = FileUtil.readFile(DataConfig.FILE_DATA_PLAYER_STAT);
        return lines != null && !lines.isEmpty();
    }
}
