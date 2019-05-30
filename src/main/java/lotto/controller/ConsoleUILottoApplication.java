package lotto.controller;

import lotto.model.creator.*;
import lotto.model.object.*;
import lotto.view.InputView;
import lotto.view.OutputView;

import java.util.ArrayList;
import java.util.List;

public class ConsoleUILottoApplication {
        private static final int LOTTO_PRICE = 1000;

        public static void main(String[] args) {
                Payment payment = createPayment();
                OutputView.printPurchaseNumber(payment);



                List<Lotto> purchasedLottos = createPurchasedLottos(payment);
                OutputView.printPurchaseLottos(purchasedLottos);

                WinStats winStats = createWinStats(purchasedLottos);
                OutputView.printWinStats(winStats);

                Yield yield = YieldCreator.create(payment, winStats);
                OutputView.printYield(yield);
        }

        private static WinStats createWinStats(List<Lotto> purchasedLottos) {
                try {
                        Lotto winningLotto = createWinningLotto();
                        BonusBall bonusBall = createBonusBall();
                        WinningInfo winningInfo = WinningInfoCreator.create(winningLotto, bonusBall);
                        return WinStatsCreator.create(purchasedLottos, winningInfo);
                } catch (RuntimeException e) {
                        System.err.println(e.getMessage());
                        return createWinStats(purchasedLottos);
                }
        }

        private static Payment createPayment() {
                try {
                        return PaymentCreator.create(InputView.inputPayment());
                } catch (RuntimeException e) {
                        System.err.println(e.getMessage());
                        return createPayment();
                }
        }

        private static List<Lotto> createPurchasedLottos(Payment payment) {
                try {
                        List<Lotto> purchasedLottos = new ArrayList<>();
                        addPurchasedLotto(payment, purchasedLottos);
                        return purchasedLottos;
                } catch (RuntimeException e) {
                        System.err.println(e.getMessage());
                        return createPurchasedLottos(payment);
                }
        }

        private static void addPurchasedLotto(Payment payment, List<Lotto> purchasedLottos) {
                List<Integer> autoCreatedLottoNumbers;
                for (int i = 0; i < payment.getNumber() / LOTTO_PRICE; i++) {
                        autoCreatedLottoNumbers = AutoLottoNumberCreator.create();
                        purchasedLottos.add(PurchasedLottoCreator.create(autoCreatedLottoNumbers));
                }
        }

        private static Lotto createWinningLotto() {
                try {
                        return WinningLottoCreator.create(InputView.inputWinningNumbers());
                } catch (RuntimeException e) {
                        System.err.println(e.getMessage());
                        return createWinningLotto();
                }
        }

        private static BonusBall createBonusBall() {
                try {
                        return BonusBallCreator.create(InputView.inputBonusBall());
                } catch (RuntimeException e) {
                        System.err.println(e.getMessage());
                        return createBonusBall();
                }
        }
}
