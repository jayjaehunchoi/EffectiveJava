package CreateConstructor;

public interface Dictionary {
    public void findWord();

    static class KoreanDictionary implements Dictionary{
        @Override
        public void findWord() {
            System.out.println("한국어 사전 탐색");
        }
    }

    static class EnglishDictionary implements Dictionary{
        @Override
        public void findWord() {
            System.out.println("영어 사전 탐색");
        }
    }
}
