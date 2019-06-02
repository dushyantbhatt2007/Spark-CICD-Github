package org.demo

import org.apache.spark.sql.SparkSession
import org.scalatest._

class WordCountTest extends FlatSpec with Matchers with SparkSessionSetup {
  "WordCount for 'abc def' 'hij klm abc'" should "count abc,2 def,1 hij,1 klm,1" in withSparkSession {
    sparkSession =>
      import sparkSession.implicits._
      val df = List("abc def", "hij klm abc").toDF()
      val wc = new WordCount(sparkSession)
      val result = wc.countWords(df)
      result.filter($"value" === "abc").first().getLong(1) should be(2)
      result.filter($"value" === "klm").first().getLong(1) should be(1)
  }
}

trait SparkSessionSetup {
  def withSparkSession(testMethod: SparkSession => Any) {
    val hostname = "localhost"
    val spark = SparkSession
      .builder()
      .config("spark.jars", "target/sparkcicd-0.0.1-SNAPSHOT.jar")
      .master("local[4]")
      .appName("basic spark test")
      .getOrCreate()
    try {
      testMethod(spark)
    }
    finally spark.stop()
  }
}