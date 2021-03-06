package se.uu.farmbio.cp

import org.apache.spark.mllib.linalg.Vector
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.rdd.RDD

abstract class UnderlyingAlgorithm(
  protected var model: (Vector => Double)) extends Serializable {
  
  def this(input: RDD[LabeledPoint]) = {
    this(null.asInstanceOf[(Vector => Double)])
    model = trainingProcedure(input)
  }
  def predictor = model //model getter
  //To be implemented by the user
  protected def trainingProcedure(input: RDD[LabeledPoint]): (Vector => Double)
  def nonConformityMeasure(newSample: LabeledPoint): Double
}

abstract class UnderlyingAlgorithmSerializer[T <: UnderlyingAlgorithm] {
  //To be implemented by the user
  def serialize(alg: T): String
  def deserialize(alg: String): T
}
