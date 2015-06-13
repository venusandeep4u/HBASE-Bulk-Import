public enum HColumnEnum {
  SRV_COL_PAGERANK("PAGE_RANK".getBytes());
  private final byte[] columnName;
  
  HColumnEnum (byte[] column) {
    this.columnName = column;
  }

  public byte[] getColumnName() {
    return this.columnName;
  }
}