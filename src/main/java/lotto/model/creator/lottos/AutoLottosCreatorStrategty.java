package lotto.model.creator.lottos;

import lotto.model.Lotto;
import lotto.model.ManualPurchaseNumber;
import lotto.model.Payment;
import lotto.model.creator.lotto.LottoCreatorStrategy;

import java.util.ArrayList;
import java.util.List;

public class AutoLottosCreatorStrategty implements LottosCreatorStrategy {
        private final Payment payment;
        private final ManualPurchaseNumber manualPurchaseNumber;
        private final LottoCreatorStrategy lottoCreatorStrategy;

        public AutoLottosCreatorStrategty(final LottoCreatorStrategy lottoCreatorStrategy, final Payment payment, final ManualPurchaseNumber manualPurchaseNumber) {
                this.lottoCreatorStrategy = lottoCreatorStrategy;
                this.payment = payment;
                this.manualPurchaseNumber = manualPurchaseNumber;
        }

        @Override
        public List<Lotto> create() {
                List<Lotto> lottos = new ArrayList<>();
                for (int index = 0; index < payment.getAmount() / Payment.LOTTO_PRICE - manualPurchaseNumber.getNumber(); index++) {
                        lottos.add(lottoCreatorStrategy.create());
                }
                return lottos;
        }
}
