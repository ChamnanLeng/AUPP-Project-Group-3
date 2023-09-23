package com.aupp.expensetracker.Entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DateRange {
    private String fromDate;
    private String toDate;

    public DateRange(){}

    public DateRange(String fromDate, String toDate) {
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }
}
