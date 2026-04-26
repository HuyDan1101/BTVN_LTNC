package org.example;

import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Lớp đại diện cho một giao dịch ngân hàng.
 */
public class Transaction {
  // Logger cho lớp Transaction
  private static final Logger logger = LoggerFactory.getLogger(Transaction.class);

  public static final int TYPE_DEPOSIT_CHECKING = 1;
  public static final int TYPE_WITHDRAW_CHECKING = 2;
  public static final int TYPE_DEPOSIT_SAVINGS = 3;
  public static final int TYPE_WITHDRAW_SAVINGS = 4;

  private int type;
  private double amount;
  private double initialBalance;
  private double finalBalance;

  /**
   * Khởi tạo một giao dịch mới.
   *
   * @param type           kiểu giao dịch
   * @param amount         số tiền giao dịch
   * @param initialBalance số dư ban đầu
   * @param finalBalance   số dư sau giao dịch
   */
  public Transaction(int type, double amount, double initialBalance, double finalBalance) {
    this.type = type;
    this.amount = amount;
    this.initialBalance = initialBalance;
    this.finalBalance = finalBalance;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public double getAmount() {
    return amount;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }

  public double getInitialBalance() {
    return initialBalance;
  }

  public void setInitialBalance(double initialBalance) {
    this.initialBalance = initialBalance;
  }

  public double getFinalBalance() {
    return finalBalance;
  }

  public void setFinalBalance(double finalBalance) {
    this.finalBalance = finalBalance;
  }

  /**
   * Chuyển đổi mã kiểu giao dịch sang chuỗi mô tả.
   *
   * @param transactionType mã kiểu giao dịch
   * @return chuỗi mô tả kiểu giao dịch
   */
  public static String getTypeString(int transactionType) {
    switch (transactionType) {
      case TYPE_DEPOSIT_CHECKING:
        return "Nạp tiền vãng lai";
      case TYPE_WITHDRAW_CHECKING:
        return "Rút tiền vãng lai";
      case TYPE_DEPOSIT_SAVINGS:
        return "Nạp tiền tiết kiệm";
      case TYPE_WITHDRAW_SAVINGS:
        return "Rút tiền tiết kiệm";
      default:
        return "Không rõ";
    }
  }

  /**
   * Trả về tóm tắt thông tin giao dịch dưới dạng chuỗi.
   *
   * @return chuỗi tóm tắt giao dịch
   */
  public String getTransactionSummary() {
    logger.debug("Bắt đầu tạo tóm tắt giao dịch cho loại: {}", this.type);

    String typeStr = getTypeString(type);
    String initStr = String.format(Locale.US, "%.2f", initialBalance);
    String amountStr = String.format(Locale.US, "%.2f", amount);
    String finalStr = String.format(Locale.US, "%.2f", finalBalance);

    // Xuống dòng ở các đối số để tránh LineLength quá dài
    return String.format(
        "- Kiểu giao dịch: %s. Số dư ban đầu: $%s. Số tiền: $%s. Số dư cuối: $%s.",
        typeStr, initStr, amountStr, finalStr);
  }
}