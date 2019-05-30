package lotto.model.creator;

import lotto.model.exception.InvalidManualPaymentNumberException;
import lotto.model.object.ManualPaymentNumber;
import lotto.model.object.Payment;

public class ManualPaymentNumberCreator {
        private static final int LOTTO_PRICE = 1000;

        public static ManualPaymentNumber create(int number, Payment payment) {
                checkValidManualPaymentNumber(number, payment);
                return new ManualPaymentNumber(number);
        }

        private static void checkValidManualPaymentNumber(int input, Payment payment) {
                if (input < 0 || input > payment.getNumber() / LOTTO_PRICE) {
                        throw new InvalidManualPaymentNumberException();
                }
        }
}
