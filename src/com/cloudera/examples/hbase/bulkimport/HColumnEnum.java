package com.cloudera.examples.hbase.bulkimport;

/**
 * HBase table columns for the 'srv' column family
 */
public enum HColumnEnum {
  SRV_COL_ID ("Id".getBytes()),
  SRV_COL_JOB ("Job".getBytes()),
  SRV_COL_USERNAME ("username".getBytes()),
  SRV_COL_NAME ("name".getBytes()),
  SRV_COL_HOBY ("hobby".getBytes()),
  SRV_COL_TIME ("pdt".getBytes());
 
  private final byte[] columnName;
  
  HColumnEnum (byte[] column) {
    this.columnName = column;
  }

  public byte[] getColumnName() {
    return this.columnName;
  }
}
