import java.io.File;

public class T4 {

    public static void main(String[] args) {
        String path = "C:\\Users\\Administrator\\Desktop\\html静态模板\\医疗保健";

        File file = new File(path);

        File files[] = file.listFiles();
        int count = 0;
        for (File f : files) {
            if (f.isDirectory()) {
                count++;
                File newFile = new File(path + "\\" + count);
                boolean flag=f.renameTo(newFile);
                if(!flag){
                    System.out.println(newFile.getPath());
                }
            }
        }
    }
}
