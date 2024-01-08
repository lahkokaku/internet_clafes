package stores;

import models.User;
import util.StageManager;
import views.LoginView;
import views.ViewAllPCView;

public class UserStore {

	// Ini untuk menyimpan data user yang diautentikasi -> menggunakan pattern Singleton
	private static UserStore instance;
	private User user = null;
	
	private UserStore() {
		
	}
	
	public static UserStore getInstance() {
		synchronized (UserStore.class) {
			if(instance == null) {
				instance = new UserStore();
			}
		}
		
		return instance;
	}
	
	// Mengecek apakah user sudah login atau belum
	public boolean isAuth() {
		if(user == null) return false;
		return true;
	}
	
	public void setUser(User user) {
		// Mengset data user
		this.user = user;
	}

	public void removeUser() {
		// Menghapus data user
		this.user = null;
	}
	
	public String getUserRole() {
		return user.getUserRole();
	}
	
	public Integer getUserID() {
		return user.getUserID();
	}
	
	public String getUserName() {
		return user.getUserName();
	}
	
	public String getUserPassword() {
		return user.getUserPassword();
	}
	
	public Integer getUserAge() {
		return user.getUserAge();
	}

}
