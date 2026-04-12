package com.excellia.wallet.scheduled;

import com.excellia.wallet.service.RecurringPaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class RecurringPaymentDetector {

    private static final Logger logger = LoggerFactory.getLogger(RecurringPaymentDetector.class);
    private final RecurringPaymentService recurringPaymentService;

    public RecurringPaymentDetector(RecurringPaymentService recurringPaymentService) {
        this.recurringPaymentService = recurringPaymentService;
    }

    // Exécution tous les jours à 2h du matin
    @Scheduled(cron = "0 0 2 * * ?")
    public void detect() {
        recurringPaymentService.detectAndSaveRecurringPayments();
    }
}
