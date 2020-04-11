import java.io.*;
import java.util.*;

import static java.util.Collections.enumeration;

public class Main {
    private static final String PATH = "src/main/java/lesson3/src/main/java/";
        public static void main(String[] args) throws FileNotFoundException {
            String path = PATH + "demo.txt";
            readMyFile(path);
            ArrayList<InputStream> al = new ArrayList<>();
            for (int i=1; i<=5;i++)
                al.add(inputStreams(new FileInputStream(PATH + i + ".txt")));
            mergeFiles(al);
        }

        private static InputStream inputStreams(FileInputStream fis)  {
            return new BufferedInputStream(fis);
        }

        private static void mergeFiles(ArrayList<InputStream> ins) {
            Enumeration<InputStream> enm = enumeration(ins);
            try (SequenceInputStream seq = new SequenceInputStream(enm);
                 FileOutputStream out = new FileOutputStream(PATH + "out.txt")){
                int x;
                while ((x = seq.read()) != -1) {
                    out.write(x);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private static void readMyFile(String path) {
            byte[] buf = new byte[73];
            try (FileInputStream in = new FileInputStream(path)) {
                int count;
                while ((count = in.read(buf)) > 0) {
                    for (int i = 0; i < count; i++) {
                        System.out.print(buf[i] + " ");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

