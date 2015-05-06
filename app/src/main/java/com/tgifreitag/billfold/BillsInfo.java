package com.tgifreitag.billfold;

public class BillsInfo {
  //  int id;
    String billName;
    String monthName;
    String dayNum;
    String amount;
 //   String dueDate;

    BillsInfo(String billName, String monthName, String dayNum, String amount) {
       // this.id = id;
        this.billName = billName;
        this.monthName = monthName;
        this.dayNum = dayNum;
        this.amount = amount;
       // this.dueDate = dueDate;
    }
}