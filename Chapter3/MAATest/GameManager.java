package Chapter3.MAATest;

import Chapter3.MinimizeAccessAuth.Player;
import Chapter3.MinimizeAccessAuth.PlayerManager;

public class GameManager {
    public static void main(String[] args) {
        Player hitter = PlayerManager.createHitter();
        Player pitcher = PlayerManager.createPitcher();

        pitcher.play();
        hitter.play();
    }
}
