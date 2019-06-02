package org.demo

import org.apache.spark.sql.{DataFrame, SparkSession}

class WordCount(sparkSession: SparkSession) {
  def countWords(df: DataFrame): DataFrame = {
    import sparkSession.implicits._
    val ds = df.as[String]
    ds.flatMap(w => w.split(' ')).groupBy($"value").count
  }
}
