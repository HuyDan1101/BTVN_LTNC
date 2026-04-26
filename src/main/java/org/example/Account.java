package org.example;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Lớp trừu tượng đại diện cho một tài khoản ngân hàng.
 * Cung cấp các phương thức cơ bản như gửi tiền, rút tiền và lấy lịch sử giao dịch.
 */
public abstract class Account {

  // Logger theo chuẩn SLF4J
  private static final Logger logger = LoggerFactory.getLogger(Account.class);

  // Sửa lỗi: Đặt tên hằng số theo chuẩn UPPER_SNAKE_CASE
  public static final String CHECKING_TYPE = "CHECKING";
  public static final String SAVINGS_TYPE = "SAVINGS";

  // Sửa lỗi: Đặt tên biến camelCase rõ nghĩa, bỏ dấu gạch dưới
  private long accountNumber;
  private double balance;
  protected List<Transaction> transactionList;

  /**
   * Khởi tạo tài khoản với số tài khoản và số dư ban đầu.
   *
   * @param accountNumber số tài khoản duy nhất
   * @param balance       số dư ban đầu
   */
  public Account(long accountNumber, double balance) {
    this.accountNumber = accountNumber;
    this.balance = balance;
    this.transactionList = new ArrayList<>();
  }

  public long getAccountNumber() {
    return accountNumber;
  }

  public void setAccountNumber(long accountNumber) {
    this.accountNumber = accountNumber;
  }

  public double getBalance() {
    return balance;
  }

  protected void setBalance(double balance) {
    this.balance = balance;
  }

  public List<Transaction> getTransactionList() {
    return transactionList;
  }

  /**
   * Thiết lập danh sách giao dịch cho tài khoản.
   *
   * @param transactionList danh sách các giao dịch mới
   */
  public void setTransactionList(List<Transaction> transactionList) {
    if (transactionList == null) {
      this.transactionList = new ArrayList<>();
    } else {
      this.transactionList = transactionList;
    }
  }

  /**
   * Phương thức trừu tượng thực hiện việc nạp tiền.
   *
   * @param amount số tiền cần nạp
   */
  public abstract void deposit(double amount);

  /**
   * Phương thức trừu tượng thực hiện việc rút tiền.
   *
   * @param amount số tiền cần rút
   */
  public abstract void withdraw(double amount) throws BankException;

  /**
   * Thực hiện logic nạp tiền vào số dư.
   *
   * @param amount số tiền nạp
   * @throws InvalidFundingAmountException nếu số tiền nhỏ hơn hoặc bằng 0
   */
  protected void doDepositing(double amount) throws InvalidFundingAmountException {
    if (amount <= 0) {
      throw new InvalidFundingAmountException(amount);
    }
    balance += amount;
  }

  /**
   * Thực hiện logic rút tiền từ số dư.
   *
   * @param amount số tiền rút
   * @throws BankException nếu số tiền không hợp lệ hoặc số dư không đủ
   */
  protected void doWithdrawing(double amount) throws BankException {
    if (amount <= 0) {
      throw new InvalidFundingAmountException(amount);
    }
    if (amount > balance) {
      throw new InsufficientFundsException(amount);
    }
    balance -= amount;
  }

  /**
   * Thêm một giao dịch mới vào lịch sử.
   *
   * @param transaction đối tượng giao dịch
   */
  public void addTransaction(Transaction transaction) {
    if (transaction != null) {
      transactionList.add(transaction);
    }
  }

  /**
   * Truy xuất toàn bộ lịch sử giao dịch dưới dạng chuỗi.
   *
   * @return chuỗi lịch sử giao dịch
   */
  public String getTransactionHistory() {
    // Sửa lỗi: Sử dụng StringBuilder để tối ưu hiệu năng cộng chuỗi
    StringBuilder historyBuilder = new StringBuilder();
    historyBuilder.append("Lịch sử giao dịch của tài khoản ")
        .append(accountNumber)
        .append(":\n");

    for (int i = 0; i < transactionList.size(); i++) {
      historyBuilder.append(transactionList.get(i).getTransactionSummary());
      if (i < transactionList.size() - 1) {
        historyBuilder.append("\n");
      }
    }

    // Sửa lỗi: Sử dụng Logger thay vì System.out.println
    logger.info("Đã trích xuất lịch sử giao dịch cho STK: {}", accountNumber);
    return historyBuilder.toString();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof Account)) {
      return false;
    }
    Account other = (Account) obj;
    return this.accountNumber == other.accountNumber;
  }

  @Override
  public int hashCode() {
    return Long.hashCode(accountNumber);
  }
}