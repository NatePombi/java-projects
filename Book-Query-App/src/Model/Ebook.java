package Model;

import java.util.Comparator;

public class Ebook extends Book{

    private double fileSizeMB;

    public Ebook() {
        super();
        this.fileSizeMB = 1 + Book.random.nextDouble() * (10-3) ;
    }

    public double getFileSizeMB(){
        return fileSizeMB;
    }

    @Override
    public boolean matchFieldValue(String field, String value) {
        if(field.contains("file".toLowerCase())){
            try {
                return fileSizeMB <= Double.parseDouble(value);
            } catch (NumberFormatException e) {
                return false;
            }
        }

        return super.matchFieldValue(field, value);
    }

    Comparator<Ebook> com = Comparator.comparing(Ebook::getFileSizeMB);

    @Override
    public String toString() {
        return super.toString() + "File Size: %.2f Mb ".formatted(fileSizeMB);
    }
}