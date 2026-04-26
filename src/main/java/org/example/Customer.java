package org.example;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Lớp Customer đại diện cho một khách hàng của ngân hàng.
 * Một khách hàng có thể sở hữu nhiều tài khoản khác nhau (Vãng lai hoặc Tiết kiệm).
 */
public class Customer {

  private static final Logger logger = LoggerFactory.getLogger(Customer.class);

  private long idNumber;
  private String fullName;
  private List<Account> accountList;

  /**
   * Constructor mặc định không tham số.
   */
  public Customer() {
    this(0L, "");
  }

  /**
   * Khởi tạo khách hàng với mã số định danh và họ tên.
   *
   * @param idNumber số CMND/CCCD hoặc mã định danh của khách hàng.
   * @param fullName họ và tên đầy đủ.
   */
  public Customer(long idNumber, String fullName) {
    this.idNumber = idNumber;
    this.fullName = fullName;
    this.accountList = new ArrayList<>();
  }

  public long getIdNumber() {
    return idNumber;
  }

  public void setIdNumber(long idNumber) {
    this.idNumber = idNumber;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public List<Account> getAccountList() {
    return accountList;
  }

  /**
   * Thiết lập danh sách tài khoản cho khách hàng.
   *
   * @param accountList danh sách các tài khoản mới.
   */
  public void setAccountList(List<Account> accountList) {
    if (accountList == null) {
      this.accountList = new ArrayList<>();
    } else {
      this.accountList = accountList;
    }
  }

  /**
   * Thêm một tài khoản mới vào danh sách nếu tài khoản đó chưa tồn tại.
   *
   * @param account đối tượng tài khoản cần thêm.
   */
  public void addAccount(Account account) {
    if (account == null) {
      logger.warn("Cảnh báo: Thử thêm tài khoản null cho khách hàng ID: {}", idNumber);
      return;
    }

    // Phương thức contains dựa trên hàm equals() đã sửa ở lớp Account
    if (!accountList.contains(account)) {
      accountList.add(account);
      logger.info("Đã thêm tài khoản {} cho khách hàng {}",
          account.getAccountNumber(), fullName);
    } else {
      logger.debug("Tài khoản {} đã tồn tại, không thêm mới.", account.getAccountNumber());
    }
  }

  /**
   * Xóa tài khoản khỏi danh sách của khách hàng.
   *
   * @param account đối tượng tài khoản cần xóa.
   */
  public void removeAccount(Account account) {
    if (account != null && accountList.remove(account)) {
      logger.info("Đã xóa tài khoản {} khỏi khách hàng {}",
          account.getAccountNumber(), fullName);
    }
  }

  /**
   * Trả về thông tin tóm tắt của khách hàng và danh sách tài khoản.
   *
   * @return chuỗi thông tin khách hàng.
   */
  public String getCustomerInfo() {
    // Sửa lỗi: Dùng StringBuilder thay vì cộng chuỗi trực tiếp
    StringBuilder info = new StringBuilder();
    info.append("Số CMND: ").append(idNumber)
        .append(". Họ tên: ").append(fullName).append(".");

    // Ghi log mức debug để không làm đầy file log chính
    logger.debug("Đang truy xuất thông tin khách hàng: {}", idNumber);
    return info.toString();
  }
}