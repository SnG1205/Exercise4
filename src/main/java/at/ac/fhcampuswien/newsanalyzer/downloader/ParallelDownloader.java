package at.ac.fhcampuswien.newsanalyzer.downloader;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ParallelDownloader extends Downloader {
    // Note: Sadly, I did not find any useful usage of Future in my code, that is why this part was omitted.
    int numOfThreads=12;
    ExecutorService pool= Executors.newFixedThreadPool(12); //int numOfThreads=Runtime.getRuntime().availableProcessors() == 12
    List<Future<String>> futures = new ArrayList<>();


    @Override
    public int process(List<String> urls) {
        int count=0;
        int listParts=urls.size()/numOfThreads+1;
        for(int i=0; i<listParts;i++){
            int index=i;
            for(int j=0; j<12;j++){
                int index2=j;
                Callable<String> task = () -> saveUrl2File(urls.get(index*numOfThreads +index2));
                pool.submit(task);
            }
        }
        pool.shutdown();
        return count;
    }
}
