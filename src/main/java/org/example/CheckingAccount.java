package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Lớp đại diện cho tài khoản vãng lai.
 * Cho phép thực hiện các giao dịch nạp và rút tiền thông thường.
 */
public class CheckingAccount extends Account {

  // Khai báo Logger cho CheckingAccount
  private static final Logger logger = LoggerFactory.getLogger(CheckingAccount.class);

  /**
   * Khởi tạo tài khoản vãng lai.
   *
   * @param accountNumber số tài khoản
   * @param balance       số dư ban đầu
   */
  public CheckingAccount(long accountNumber, double balance) {
    super(accountNumber, balance);
  }

  @Override
  public void deposit(double amount) {
    logger.info("Yêu cầu nạp tiền vào TK vãng lai {}: ${}", getAccountNumber(), amount);
    double initialBalance = getBalance();
    try {
      doDepositing(amount);
      double finalBalance = getBalance();

      // Sửa lỗi: Sử dụng hằng số TYPE_DEPOSIT_CHECKING từ lớp Transaction thay vì số 1
      Transaction t = new Transaction(
          Transaction.TYPE_DEPOSIT_CHECKING,
          amount,
          initialBalance,
          finalBalance
      );
      addTransaction(t);
      logger.info("Nạp tiền thành công vào TK {}. Số dư hiện tại: ${}",
          getAccountNumber(), finalBalance);
    } catch (BankException e) {
      logger.error("Lỗi khi nạp tiền vào TK {}: {}", getAccountNumber(), e.getMessage());
    }
  }

  @Override
  public void withdraw(double amount) throws BankException { // Thêm throws ở đây
    logger.info("Yêu cầu rút tiền từ TK vãng lai {}: ${}", getAccountNumber(), amount);
    double initialBalance = getBalance();
    try {
      doWithdrawing(amount);
      double finalBalance = getBalance();

      Transaction t = new Transaction(
          Transaction.TYPE_WITHDRAW_CHECKING,
          amount,
          initialBalance,
          finalBalance
      );
      addTransaction(t);
      logger.info("Rút tiền thành công từ TK {}. Số dư còn lại: ${}",
          getAccountNumber(), finalBalance);
    } catch (BankException e) {
      logger.warn("Giao dịch rút tiền từ TK {} bị từ chối: {}",
          getAccountNumber(), e.getMessage());
      // QUAN TRỌNG: Ném ngoại lệ ra ngoài sau khi đã log
      throw e;
    }
  }
}