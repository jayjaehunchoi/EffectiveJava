package Chapter3.InterfaceBetterThanAbstract.interfacepackage;

public class SingerSongWriter implements Singer, SongWriter{
    @Override
    public void sing() {
        System.out.println("노래한다");
    }

    @Override
    public void compose() {
        System.out.println("작곡한다");
    }
}
