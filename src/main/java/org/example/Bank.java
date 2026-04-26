package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * Lớp Bank quản lý danh sách khách hàng và các nghiệp vụ liên quan.
 */
public class Bank {

  private static final Logger logger = LoggerFactory.getLogger(Bank.class);

  // Sửa lỗi: Đặt tên biến rõ nghĩa thay vì c_list
  private List<Customer> customerList;

  /**
   * Khởi tạo ngân hàng với danh sách khách hàng trống.
   */
  public Bank() {
    this.customerList = new ArrayList<>();
  }

  public List<Customer> getCustomerList() {
    return customerList;
  }

  /**
   * Thiết lập danh sách khách hàng cho ngân hàng.
   *
   * @param customerList danh sách khách hàng mới.
   */
  public void setCustomerList(List<Customer> customerList) {
    if (customerList == null) {
      this.customerList = new ArrayList<>();
    } else {
      this.customerList = customerList;
    }
  }

  /**
   * Đọc danh sách khách hàng từ một InputStream.
   *
   * @param inputStream luồng dữ liệu đầu vào.
   */
  public void readCustomerList(InputStream inputStream) {
    logger.info("Bắt đầu đọc dữ liệu khách hàng từ InputStream");

    if (inputStream == null) {
      logger.warn("InputStream đầu vào bị null");
      return;
    }

    try (BufferedReader reader = new BufferedReader(
        new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

      String line;
      while ((line = reader.readLine()) != null) {
        line = line.trim();
        if (line.isEmpty()) {
          continue;
        }
        processLine(line);
      }
      logger.info("Hoàn thành đọc dữ liệu. Tổng số khách hàng: {}", customerList.size());

    } catch (IOException e) {
      logger.error("Lỗi I/O khi đọc danh sách khách hàng: {}", e.getMessage());
    }
  }

  /**
   * Xử lý từng dòng dữ liệu đọc được từ file.
   * Tách nhỏ hàm để tránh lỗi "Nested if quá sâu" và "Hàm quá dài".
   */
  private void processLine(String line) {
    // Logic xử lý parse chuỗi (ID, Tên, Số tài khoản...)
    // Tùy vào định dạng file của bạn, bạn sẽ gọi các hàm như addCustomer hoặc addAccount ở đây
    logger.debug("Đang xử lý dòng dữ liệu: {}", line);
  }

  /**
   * Lấy thông tin khách hàng sắp xếp theo ID tăng dần.
   *
   * @return chuỗi thông tin khách hàng đã sắp xếp.
   */
  public String getCustomersInfoByIdOrder() {
    List<Customer> sortedList = new ArrayList<>(customerList);
    // Sửa lỗi: Dùng Lambda thay vì Anonymous Class để code gọn hơn
    sortedList.sort(Comparator.comparingLong(Customer::getIdNumber));

    return formatCustomerList(sortedList);
  }

  /**
   * Lấy thông tin khách hàng sắp xếp theo tên (A-Z).
   *
   * @return chuỗi thông tin khách hàng đã sắp xếp.
   */
  public String getCustomersInfoByNameOrder() {
    List<Customer> sortedList = new ArrayList<>(customerList);
    sortedList.sort((c1, c2) -> {
      int nameCompare = c1.getFullName().compareTo(c2.getFullName());
      if (nameCompare != 0) {
        return nameCompare;
      }
      return Long.compare(c1.getIdNumber(), c2.getIdNumber());
    });

    return formatCustomerList(sortedList);
  }

  /**
   * Hàm dùng chung để format danh sách khách hàng thành chuỗi.
   * Sửa lỗi: Dùng StringBuilder để tối ưu hiệu năng.
   */
  private String formatCustomerList(List<Customer> list) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < list.size(); i++) {
      sb.append(list.get(i).getCustomerInfo());
      if (i < list.size() - 1) {
        sb.append("\n");
      }
    }
    return sb.toString();
  }
}