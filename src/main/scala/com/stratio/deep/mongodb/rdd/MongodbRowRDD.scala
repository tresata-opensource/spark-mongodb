package com.stratio.deep.mongodb.rdd

import com.stratio.deep.mongodb.Config
import com.stratio.deep.mongodb.schema.MongodbSchema
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{Row, SQLContext}
import org.apache.spark.{Partition, TaskContext}

/**
 * Created by rmorandeira on 29/01/15.
 */


case class MongodbRowRDD(sc: SQLContext,
                         schema: MongodbSchema,
                         config: Config)
  extends RDD[Row](sc.sparkContext, deps = Nil) {

  override def getPartitions: Array[Partition] = {
    val sparkPartitions = List(new MongodbPartition(id, 0))
    sparkPartitions.toArray
  }

  override def getPreferredLocations(split: Partition): Seq[String] = {
    //TODO: Implement proper getPreferredLocations
    super.getPreferredLocations(split)
  }

  override def compute(split: Partition, context: TaskContext): MongodbRowRDDIterator = {
    new MongodbRowRDDIterator(context, schema, split.asInstanceOf[MongodbPartition],config)
  }

}