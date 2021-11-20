package Practice1;

public interface Grade {

    public static Grade of(int score){
        if(score >= 80){
            return new Advanced();
        }else if(score >= 60){
            return new Intermediate();
        }else{
            return new Basic();
        }
    }

    public void showGrade();

    static class Advanced implements Grade{
        @Override
        public void showGrade() {
            System.out.println("Advanced");
        }
    }

    static class Intermediate implements Grade{
        @Override
        public void showGrade() {
            System.out.println("Intermediate");
        }
    }

    static class Basic implements Grade{
        @Override
        public void showGrade() {
            System.out.println("Basic");
        }
    }

    public static void main(String[] args) {
        Grade.of(100).showGrade();
        Grade.of(70).showGrade();
        Grade.of(50).showGrade();

    }
}
