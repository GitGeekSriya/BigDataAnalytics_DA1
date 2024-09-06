import java.io.*;
import java.util.*;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.*;
import org.apache.hadoop.mapreduce.lib.output.*;

public class ro22mis1148{
	public static class stud extends Mapper<LongWritable,Text,Text,Text>
	 {
	  private String year;
	  public void map(LongWritable key, Text value, Context context)
	  throws IOException, InterruptedException
	  {
	   year=context.getConfiguration().get("year");
	   String[] line=value.toString().split(" "); 
	   if(year.equals(line[4]))
	   context.write(new Text(line[0]), new Text("stud"+","+line[1]));
	  }
	 }
	 public static class cat1 extends Mapper<LongWritable,Text,Text,Text>
	 {
	  public void map(LongWritable key, Text value, Context context)
	  throws IOException, InterruptedException
	  {
	   String[] line=value.toString().split(" ");
	   context.write(new Text(line[0]), new Text("cat1"+","+line[1]));
	  }
	 } 
	 public static class cat2 extends Mapper<LongWritable,Text,Text,Text>
	 {
	  public void map(LongWritable key, Text value, Context context)
	  throws IOException, InterruptedException
	  {
	   String[] line=value.toString().split(" ");
	   context.write(new Text(line[0]), new Text("cat2"+","+line[1]));
	  }
	 }
	 public static class jreducer extends Reducer<Text,Text,Text,Text>
	 { 
	  public void reduce(Text key, Iterable<Text> values, Context context ) 
	  throws IOException, InterruptedException
	  {
		  int c1=0,c2=0,max=0; 
		  String k = key.toString();
		  String st1="";
		  boolean flag = false;
	  for(Text val:values)
	   { 
		   String[] line = val.toString().split(","); 
		   if (line[0].equals("stud")) 
		   {
		    st1 = line[1];
		    flag = true;
		   } 
		   else if (line[0].equals("cat1")) 
		   {
		    c1 = Integer.parseInt(line[1]);
		   }
		   else if (line[0].equals("cat2")) 
		   {
		    c2 = Integer.parseInt(line[1]);
		   }
	   }
	   if(c1<c2) max=c2;
	   else max=c1;
		   //String str = String.format("%s\t%s", c,amtst,st1);
		   if(flag)
		   context.write(new Text(k+" "+st1), new Text(Integer.toString(max)));
		   }
		   }
  public static void main(String[] args) throws Exception{ 
	  Configuration conf = new Configuration();
	  conf.set("year",args[4]);
	  Job job = new Job(conf, "redjoin");
	  job.setJarByClass(ro22mis1084.class);
	  job.setReducerClass(jreducer.class);
	  MultipleInputs.addInputPath(job,new Path(args[0]),TextInputFormat.class,stud.class);
	  MultipleInputs.addInputPath(job,new Path(args[1]),TextInputFormat.class,cat1.class);
	  MultipleInputs.addInputPath(job,new Path(args[2]),TextInputFormat.class,cat2.class);
	  FileOutputFormat.setOutputPath(job, new Path(args[3]));
	  job.setReducerClass(jreducer.class);
	  job.setNumReduceTasks(1);
	  job.setOutputKeyClass(Text.class);
	  job.setOutputValueClass(Text.class);
	  job.waitForCompletion(true);
	   }   }