import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import java.lang.Math._

@SerialVersionUID(123L)
case class Vertex ( vid: Long, clusterid: Long,  adjacent:List[Long] )
      extends Serializable {}

object Partition {

  val depth = 6

  def main ( args: Array[ String ] ) {
  	val conf =new SparkConf().setAppName("MapOne")
  	val sc =new SparkContext(conf)
  	val count = 0
  	
  	val firstmap = sc.textFile(args(0)).map( line =>{
  		var input1 =line.split(",");
  		var vid =input1(0).toLong;
  		
  		var adj = input1.toList.tail;
  		if (count<10)
  		{
  		clusterid = vid	
  		}
  		else
  		{
  		clusterid = -1	
  		}
  		Vertex(vid,clusterid,adj)
        count =count+1


  		})
    


  }
}
