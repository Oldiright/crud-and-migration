package db.dto;

public class Client {
    private String name;
    private long id;

    public String getName() {
        return name;
    }

    public Client(String name) {
        this.name = name;
    }
    public Client(long id, String name) {
        this.name = name;
        this.id = id;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id='" + id + '\'' +
                ", name=" + name +
                '}';
    }
}

