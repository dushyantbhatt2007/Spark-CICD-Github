package org.demo

import org.apache.spark.sql.SparkSession

object SparkCICD {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .master("local[4]")
      .appName("WordCount")
      .getOrCreate()

    val fileLocation = args(0)
    val df = spark.read.text(fileLocation)
    val wc = new WordCount(spark).countWords(df)
    wc.show()
  }

}
