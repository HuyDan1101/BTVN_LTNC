package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit Test cho lớp Account.
 */
public class AccountTest {
  private CheckingAccount account;

  @BeforeEach
  void setUp() {
    // Khởi tạo một tài khoản vãng lai với số dư ban đầu là 1000
    account = new CheckingAccount(1, 1000.0);
  }

  @Test
  void testDeposit() {
    account.deposit(500.0);
    assertEquals(1500.0, account.getBalance(), "Số dư sau khi nạp tiền phải là 1500");
  }

  @Test
  void testWithdrawSuccess() throws BankException {
    account.withdraw(400.0);
    assertEquals(600.0, account.getBalance(), "Số dư sau khi rút tiền phải là 600");
  }

  @Test
  void testWithdrawInsufficientFunds() {
    // Kiểm tra xem có ném ra ngoại lệ khi rút quá số tiền đang có không
    assertThrows(InsufficientFundsException.class, () -> {
      account.withdraw(2000.0);
    }, "Phải ném ra lỗi InsufficientFundsException");
  }
}