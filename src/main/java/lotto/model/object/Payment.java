package lotto.model.object;

public class Payment {

        private final int number;

        public Payment(int number) {
                this.number = number;
        }

        public int getNumber() {
                return number;
        }

        public double calculateYield(int totalRevenue) {
                return (double) totalRevenue / number;
        }
}
