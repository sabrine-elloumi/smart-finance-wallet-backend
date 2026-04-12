package com.excellia.wallet.repository;

import com.excellia.wallet.entity.RecurringPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface RecurringPaymentRepository extends JpaRepository<RecurringPayment, UUID> {
}
