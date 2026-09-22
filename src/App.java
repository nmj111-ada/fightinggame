import com.itheima.domain.VerificationCode;
import com.itheima.ui.FightingGame;
import com.itheima.ui.Login;

public class App {
    //启动类
    //启动，登录，注册页面
    public static void main(String[] args) {
        Login login = new Login();
        login.start();


        FightingGame fg = new FightingGame();
        fg.gameStart("username");
    }
}
