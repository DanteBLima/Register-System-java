import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.*;

public class Form {
    public static void main(String[] args) throws IOException {
        ArrayList<String> form = new ArrayList<>();

       FileReader fr = new FileReader("formulario.txt");
       BufferedReader br = new BufferedReader(fr);

      String line = br.readLine();

      while(line != null)
      {
          form.add(line);
          line = br.readLine();
      }

      br.close();

      form.forEach(System.out::println);

    }
}
