package kauanfiolo_04.library_api.repository;

import kauanfiolo_04.library_api.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class TransactionsTest {

    @Autowired
    TransactionService transactionService;

    @Test
    void simpleTransaction() {
        transactionService.execute();
    }

    @Test
    void managedStateTransaction() {
        transactionService.updatingWithoutUpdate();
    }
}
