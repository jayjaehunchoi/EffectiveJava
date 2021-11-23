package Chapter2.OverrideToStringComparable;

public class BaseBallGameManager {

    private int strike = 0;
    private int ball = 0;

    public void doStrike(){
        strike++;
    }

    public void doBall(){
        ball++;
    }

    @Override
    public String toString() {
        return strike + " 스트라이크 " + ball + " 볼";
    }

    public static void main(String[] args) {
        BaseBallGameManager game = new BaseBallGameManager();

        game.doBall();
        game.doStrike();
        System.out.println(game);

        game.doBall();
        game.doStrike();
        System.out.println(game);
    }
}
