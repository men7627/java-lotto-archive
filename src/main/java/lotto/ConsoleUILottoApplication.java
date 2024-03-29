package lotto;

import lotto.model.*;
import lotto.model.creator.lotto.AutoLottoCreatorStrategy;
import lotto.model.creator.lotto.LottoCreatorStrategy;
import lotto.model.creator.lotto.ManualLottoCreatorStrategy;
import lotto.model.creator.lottos.AutoLottosCreatorStrategty;
import lotto.model.creator.lottos.LottosCreator;
import lotto.model.creator.lottos.ManualLottosCreatorStrategy;
import lotto.view.InputView;
import lotto.view.OutputView;

import java.util.ArrayList;
import java.util.List;

public class ConsoleUILottoApplication {
        public static void main(String[] args) {
                Payment payment = createPayment();

                ManualPurchaseNumber manualPurchaseNumber = createManualPaymentNumber(payment);

                List<Lotto> totalLottos = createTotalLottos(payment, manualPurchaseNumber);

                OutputView.printPurchaseHistory(payment, manualPurchaseNumber);
                OutputView.printPurchaseLottos(totalLottos);

                WinStats winStats = createWinStats(totalLottos);
                OutputView.printWinStats(winStats);

                Yield yield = new Yield(payment, winStats);
                OutputView.printYield(yield);
        }

        private static Payment createPayment() {
                try {
                        return new Payment(InputView.inputPayment());
                } catch (RuntimeException e) {
                        System.err.println(e.getMessage());
                        return createPayment();
                }
        }

        private static ManualPurchaseNumber createManualPaymentNumber(final Payment payment) {
                try {
                        return new ManualPurchaseNumber(InputView.inputManualPaymentNumber(), payment);
                } catch (RuntimeException e) {
                        System.err.println(e.getMessage());
                        return createManualPaymentNumber(payment);
                }
        }

        private static List<Lotto> createTotalLottos(final Payment payment, final ManualPurchaseNumber manualPurchaseNumber) {
                List<Lotto> manualLottos = createManualLottos(manualPurchaseNumber);
                List<Lotto> autoLottos = createAutoLottos(payment, manualPurchaseNumber);
                manualLottos.addAll(autoLottos);
                return manualLottos;
        }

        private static List<Lotto> createManualLottos(final ManualPurchaseNumber manualPurchaseNumber) {
                try {
                        List<String[]> inputs = InputView.inputManualPaymentLottosNumber(manualPurchaseNumber);
                        List<LottoCreatorStrategy> lottoCreatorStrategies = new ArrayList<>();
                        createManualLottoCreators(inputs, lottoCreatorStrategies);
                        return new LottosCreator(new ManualLottosCreatorStrategy(lottoCreatorStrategies)).create();
                } catch (RuntimeException e) {
                        System.err.println(e.getMessage());
                        return createManualLottos(manualPurchaseNumber);
                }
        }

        private static void createManualLottoCreators(List<String[]> inputs, List<LottoCreatorStrategy> lottoCreatorStrategies) {
                for (String[] input : inputs) {
                        lottoCreatorStrategies.add(new ManualLottoCreatorStrategy(input));
                }
        }

        private static List<Lotto> createAutoLottos(final Payment payment, final ManualPurchaseNumber manualPurchaseNumber) {
                try {
                        return new LottosCreator(new AutoLottosCreatorStrategty(AutoLottoCreatorStrategy.getInstance(), payment, manualPurchaseNumber)).create();
                } catch (RuntimeException e) {
                        System.err.println(e.getMessage());
                        return createAutoLottos(payment, manualPurchaseNumber);
                }
        }

        private static WinStats createWinStats(final List<Lotto> purchasedLottos) {
                try {
                        Lotto winningLotto = createWinningLotto();
                        BonusBall bonusBall = createBonusBall();
                        WinningInfo winningInfo = new WinningInfo(winningLotto, bonusBall);
                        return new WinStats(purchasedLottos, winningInfo);
                } catch (RuntimeException e) {
                        System.err.println(e.getMessage());
                        return createWinStats(purchasedLottos);
                }
        }

        private static Lotto createWinningLotto() {
                try {
                        return new Lotto(InputView.inputWinningLottoNumber());
                } catch (RuntimeException e) {
                        System.err.println(e.getMessage());
                        return createWinningLotto();
                }
        }

        private static BonusBall createBonusBall() {
                try {
                        return new BonusBall(InputView.inputBonusBall());
                } catch (RuntimeException e) {
                        System.err.println(e.getMessage());
                        return createBonusBall();
                }
        }
}
