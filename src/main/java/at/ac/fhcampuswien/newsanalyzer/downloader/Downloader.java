package at.ac.fhcampuswien.newsanalyzer.downloader;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class Downloader {

    public static final String HTML_EXTENTION = ".html";
    public static final String DIRECTORY_DOWNLOAD = "./download/";

    public abstract int process(List<String> urls);

    public String saveUrl2File(String urlString) {
        InputStream is = null;
        OutputStream os = null;
        String fileName = "";
        try {
            URL url4download = new URL(urlString);
            is = url4download.openStream();

            fileName = urlString.substring(urlString.lastIndexOf('/') + 1);
            if (fileName.isEmpty()) {
                fileName = url4download.getHost() + HTML_EXTENTION;
            }
            List<Character> chars=fileName
                    .chars()
                    .mapToObj(e->(char) e)
                    .collect(Collectors.toList());
            if (chars.contains('?')){
                os = new FileOutputStream(DIRECTORY_DOWNLOAD + "InvalidFileName");
                return  fileName=null;
            }
            else {
                os = new FileOutputStream(DIRECTORY_DOWNLOAD + fileName);
            }

            //os = new FileOutputStream(DIRECTORY_DOWNLOAD + fileName);

            byte[] b = new byte[2048];
            int length;
            while ((length = is.read(b)) != -1) {
                os.write(b, 0, length);
            }
        } catch (IOException e) {
            System.out.println("Invalid filename: "+fileName+". URL was not added");
        } finally {
            try {
                Objects.requireNonNull(is).close();
                Objects.requireNonNull(os).close();
            } catch (IOException e) {
                System.out.println("Moved to the next URL in the list");
            }
        }
        return fileName;
    }
}
