package src.xupt.se.ttms.model;


public class User {
    private int    type;
    private int    UserId;
	private String Username;
	private String UserPasswd;
	private int    UserLeftMoney;
	private int    UserStatus;
    /*
     * UserStatus :   1:成功登录   0:登录失败
     *
     * */

	public User () {}
	public User(String username,String userPasswd,int UserLeftMoney,int UserStatus) {
		this.Username = username;
		this.UserPasswd = userPasswd;
        this.UserLeftMoney = UserLeftMoney;
        this.UserStatus = UserStatus;
	}

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUsername() {
		return Username;
	}

	public String getUserPasswd() {
		return UserPasswd;
	}

	public int getUserLeftMoney() {
		return UserLeftMoney;
	}

	public int getUserStatus() {
		return UserStatus;
	}


	public void setUsername(String username) {
		Username = username;
	}

	public void setUserPasswd(String userPasswd) {
		UserPasswd = userPasswd;
	}

	public void setUserLeftMoney(int userLeftMoney) {
		UserLeftMoney = userLeftMoney;
	}

	public void setUserStatus(int userStatus) {
		UserStatus = userStatus;
	}


    public void setUserId(int userId) {
        UserId = userId;
    }

    public int getUserId() {

        return UserId;
    }
}
