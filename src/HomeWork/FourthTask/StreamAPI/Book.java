package HomeWork.FourthTask.StreamAPI;

import java.util.List;

class Book {
    String title;
    int publicationYear;
    List<Author> authors;

    public Book(String title, int publicationYear, List<Author> authors) {
        this.title = title;
        this.publicationYear = publicationYear;
        this.authors = authors;
    }

    public String getTitle() {
        return title;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public List<Author> getAuthors() {
        return authors;
    }
}
