package Chapter3.MinimizeAccessAuth;

public class PlayerManager {

    private String gameStatus;

    public static Player createHitter(){
        return new Hitter();
    }

    public static Player createPitcher(){
        return new Pitcher();
    }
}
