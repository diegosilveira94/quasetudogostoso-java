package com.quasetudogostoso.model;

public class User {

    private int id;
    private String name;
    private String email;
    private String birthDate;
    private int cep;
    private int gender;
    private String password;
    private String salt;
    private String registrationDate;
    private String uuId;

    // Constructor for INSERT
    public User(String name, String email, String birthDate, int cep, int gender, String password) {
        this.name = name;
        this.email = email;
        this.birthDate = birthDate;
        this.cep = cep;
        this.gender = gender;
        this.password = password;
        this.salt = "XxX";
        this.uuId = "zZz";
    }

    // Constructor for SELECT
    public User(int id, String name, String email, String birthDate, int cep, int gender, String password, String salt, String registrationDate, String uuId) {
        this.name = name;
        this.email = email;
        this.birthDate = birthDate;
        this.cep = cep;
        this.gender = gender;
        this.password = password;
        this.salt = salt;
        this.registrationDate = registrationDate;
        this.uuId = uuId;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public int getCep() {
        return cep;
    }

    public void setCep(int cep) {
        this.cep = cep;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getUuId() {
        return uuId;
    }

    public void setUuId(String uuId) {
        this.uuId = uuId;
    }

    @Override
    public String toString() {
        return ""
                + "Id:" + this.getId()
                + "Nome:" + this.getName()
                + "Email:" + this.getEmail()
                + "Data Nascimento:" + this.getBirthDate()
                + "CEP:" + this.getCep()
                + "Gênero:" + this.getGender()
                + "Senha:" + this.getPassword()
                + "Salt:" + this.getSalt()
                + "Data Inscrição:" + this.getRegistrationDate()
                + "UUID:" + this.getUuId();
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || !(object instanceof User)) {
            return false;
        }
        final User other = (User) object;

        return this.id == other.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
