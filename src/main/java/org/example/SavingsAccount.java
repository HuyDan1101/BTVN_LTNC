package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Tài khoản tiết kiệm - Thực thi các quy định về rút tiền và nạp tiền riêng biệt.
 * Ví dụ: Giới hạn số tiền rút tối đa và số dư tối thiểu.
 */
public class SavingsAccount extends Account {

  private static final Logger logger = LoggerFactory.getLogger(SavingsAccount.class);

  // Sửa lỗi: Khai báo hằng số để thay thế Magic Numbers
  public static final double MAX_WITHDRAW_AMOUNT = 1000.0;
  public static final double MIN_BALANCE_REQUIRED = 5000.0;

  /**
   * Khởi tạo tài khoản tiết kiệm.
   *
   * @param accountNumber số tài khoản
   * @param balance       số dư ban đầu
   */
  public SavingsAccount(long accountNumber, double balance) {
    super(accountNumber, balance);
  }

  @Override
  public void deposit(double amount) {
    logger.info("Bắt đầu giao dịch nạp tiền vào tài khoản tiết kiệm: {}", getAccountNumber());
    double initialBalance = getBalance();
    try {
      doDepositing(amount);
      double finalBalance = getBalance();

      // Sửa lỗi: Dùng hằng số từ lớp Transaction thay vì số 3
      Transaction t = new Transaction(
          Transaction.TYPE_DEPOSIT_SAVINGS,
          amount,
          initialBalance,
          finalBalance
      );
      addTransaction(t);
      logger.info("Nạp tiền thành công. Số dư mới: {}", finalBalance);
    } catch (BankException e) {
      logger.error("Lỗi nạp tiền tại tài khoản {}: {}", getAccountNumber(), e.getMessage());
    }
  }

  @Override
  public void withdraw(double amount) {
    logger.info("Yêu cầu rút tiền từ tài khoản tiết kiệm: {}, số tiền: {}",
        getAccountNumber(), amount);
    double initialBalance = getBalance();
    try {
      // Sửa lỗi: Thay Magic Number 1000.0 bằng hằng số
      if (amount > MAX_WITHDRAW_AMOUNT) {
        throw new InvalidFundingAmountException(amount);
      }
      // Sửa lỗi: Thay Magic Number 5000.0 bằng hằng số
      if (initialBalance - amount < MIN_BALANCE_REQUIRED) {
        throw new InsufficientFundsException(amount);
      }

      doWithdrawing(amount);
      double finalBalance = getBalance();

      // Sửa lỗi: Dùng hằng số thay vì số 4
      Transaction t = new Transaction(
          Transaction.TYPE_WITHDRAW_SAVINGS,
          amount,
          initialBalance,
          finalBalance
      );
      addTransaction(t);
      logger.info("Rút tiền thành công. Số dư hiện tại: {}", finalBalance);
    } catch (BankException e) {
      logger.warn("Giao dịch rút tiền thất bại: {}", e.getMessage());
    }
  }
}