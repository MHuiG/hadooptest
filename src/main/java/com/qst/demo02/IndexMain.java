package com.qst.demo02;

import org.apache.hadoop.conf.Configuration;
        import org.apache.hadoop.conf.Configured;
        import org.apache.hadoop.fs.Path;
        import org.apache.hadoop.io.IntWritable;
        import org.apache.hadoop.io.Text;
        import org.apache.hadoop.mapreduce.Job;
        import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
        import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
        import org.apache.hadoop.util.Tool;
        import org.apache.hadoop.util.ToolRunner;


public class IndexMain  extends Configured implements Tool {

    @Override
    public int run(String[] args) throws Exception {
        Job job = Job.getInstance(super.getConf(), "indexCreate");
        job.setJarByClass(IndexMain.class);
        job.setInputFormatClass(TextInputFormat.class);
//        TextInputFormat.addInputPath(job,new Path("file:///F:\\index\\input"));
        TextInputFormat.addInputPath(job,new Path(args[0]));

        job.setMapperClass(IndexMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        job.setReducerClass(IndexReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        job.setOutputFormatClass(TextOutputFormat.class);
//        TextOutputFormat.setOutputPath(job,new Path("file:///F:\\index\\out_index"));
        TextOutputFormat.setOutputPath(job,new Path(args[1]));

        boolean b = job.waitForCompletion(true);


        return b?0:1;
    }

    public static void main(String[] args) throws Exception {
        int run = ToolRunner.run(new Configuration(), new IndexMain(), args);
        System.exit(run);


    }

}
