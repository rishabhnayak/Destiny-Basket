package nmdl.express.ecomm.response;

public class User {
    String name,mobile,email,id,adress,city;

    public User(String name, String mobile, String email, String id, String adress) {
        this.name = name;
        this.mobile = mobile;
        this.email = email;
        this.id = id;
        this.adress = adress;
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public String getName() {
        return name;
    }

    public String getMobile() {
        return mobile;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }

    public String getAdress() {
        return adress;
    }
}
