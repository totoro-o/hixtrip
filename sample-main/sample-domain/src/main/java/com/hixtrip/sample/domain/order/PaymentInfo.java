package com.hixtrip.sample.domain.order;

/**
 * hixtrip <br/>
 *
 * @author sushuaihao 2023/5/30
 * @since
 */
public class PaymentInfo {
  protected static final PaymentInfo UNPAID_INFO = new PaymentInfo(Status.UNPAID, null);
  private Status status;
  private String number;


  public PaymentInfo(Status status, String number) {
    this.status = status;
    this.number = number;
  }

  public enum Status {
    UNPAID(0),
    SUCCESS(1),
    FAIL(2);
    int sign;

    Status(int sign) {
      this.sign = sign;
    }
  }


  public Status getStatus() {
    return status;
  }

  public String getNumber() {
    return number;
  }
}
