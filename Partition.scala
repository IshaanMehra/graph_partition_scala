import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import java.lang.Math._

@SerialVersionUID(123L)
case class Vertex ( vid: Long, clusterid: Long,adjacent:List[String] )
      extends Serializable {}

object Partition {

  val depth = 6

  def main ( args: Array[ String ] ) {
  	val conf =new SparkConf().setAppName("MapOne").setMaster("local[2]")
  	val sc =new SparkContext(conf)
  	var count:Int = 0
  	
  	val firstmap = sc.textFile(args(0)).map( line =>{
  		var input1 =line.split(",");
  		var vid =input1(0).toLong;
  		var clusterid = 0.toLong
  		var adj = input1.toList.tail;
  		if (count<=5)
  		{
  		clusterid = vid	
  		}
  		else
  		{
  		clusterid = -1	
  		}
  		count =count+1
  		Vertex(vid,clusterid,adj)
        


  		})
  	 //firstmap.foreach(println);
      for (i <- 1 to depth)
        {
  	 var firstr = firstmap.map( firstmap => (firstmap.vid,firstmap) )
  	  .map { case (k,firstmap) =>
    
    	var f_vid = firstmap.vid;
    	var f_clusterid = firstmap.clusterid;
    	var f_ad = firstmap.adjacent;
    	(f_vid,Vertex(f_vid,f_clusterid,f_ad)) }

       	var left = firstr.values.map( firstr => (firstr.vid,firstr))
       	//left.foreach(println);
   	    var right = firstr.flatMap(firstr => 
   	    	for (j <- firstr._2.adjacent)
   	    	yield{
   		(j.toLong,new Vertex(1.toLong,firstr._2.clusterid,List(0.toString)))
   		})
   	    //right.foreach(println);
       
        	var secondmap = left.union(right).groupByKey()
        	//secondm.foreach(println);
        }
   		
   

     def reducer(id:Long,s: Iterable[Either[(Long,List[Long]),Long]] )
     {
      var adj1 : List[Long] = List();
      var cluster = -1.toLong

      
      for(i <- s)
      {
      	
      	if(i.isLeft && i._1>0)
      	{
      		return(id,i._1,i._2)
      	}
      	if(i.isRight)
      	{
      	cluster=i._1;	
      	}
      	if(i.isLeft && i._1==-1)
      	{
      		adj1=i._2
      	}

      return(id,cluster,adj1)

      }

     }
   		


        
  	
         


  }
}
