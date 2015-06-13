
import java.io.IOException;
import java.util.Locale;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import au.com.bytecode.opencsv.CSVParser;

public class HBaseKVMapper extends
    Mapper<LongWritable, Text, ImmutableBytesWritable, KeyValue> {

  final static byte[] SRV_COL_FAM = "srv".getBytes();
  final static int NUM_FIELDS = 2;

  CSVParser csvParser = new CSVParser();
  int tipOffSeconds = 0;
  String tableName = "";



  ImmutableBytesWritable hKey = new ImmutableBytesWritable();
  KeyValue kv;

  /** {@inheritDoc} */
  @Override
  protected void setup(Context context) throws IOException,
      InterruptedException {
    Configuration c = context.getConfiguration();

    tipOffSeconds = c.getInt("epoch.seconds.tipoff", 0);
    tableName = c.get("hbase.table.name");
  }

  /** {@inheritDoc} */
  @Override
  protected void map(LongWritable key, Text value, Context context)
      throws IOException, InterruptedException {

    String[] fields = null;

    try {
      fields = csvParser.parseLine(value.toString());
    } catch (Exception ex) {
      context.getCounter("HBaseKVMapper", "PARSE_ERRORS").increment(1);
      return;
    }

    if (fields.length != NUM_FIELDS) {
      context.getCounter("HBaseKVMapper", "INVALID_FIELD_LEN").increment(1);
      return;
    }

    // Get game offset in seconds from tip-off
    



    String pageRank = fields[0];
    String pageName = fields[1];
    

    // Key: e.g. "1200:twitter:jrkinley"
    hKey.set(String.format("%s:",pageName)
        .getBytes());

    // Service columns
  

 

    if (!fields[2].equals("")) {
      kv = new KeyValue(hKey.get(), SRV_COL_FAM,
          HColumnEnum.SRV_COL_PAGERANK.getColumnName(), pageRank.getBytes());
      context.write(hKey, kv);
    }

    context.getCounter("HBaseKVMapper", "NUM_MSGS").increment(1);

    /*
     * Output number of messages per quarter and before/after game. This should
     * correspond to the number of messages per region in HBase
     */
    

  }
}