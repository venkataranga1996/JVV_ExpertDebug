package com.hellohasan.sqlite_project.Features.StudentCRUD.CreateStudent;

public class Patient {
    private long id;
    private String name;
    private long registrationNumber;
    private String disease;
    private String village;
    private int  contact;
    private String tablets;
    private String gender;



    public Patient(String name, long registrationNumber, String disease, String village, int contact, String tablets,String gender) {

        this.name = name;
        this.registrationNumber = registrationNumber;
        this.disease = disease;
        this.village = village;
        this.contact = contact;
        this.tablets = tablets;
        this.gender = gender;
    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(long registrationNumber) {
        this.registrationNumber = registrationNumber;}


    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;}


    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }


    public int getContact() {
        return contact;
    }

    public void setContact(int contact) {
        this.contact = contact;
    }


    public String getTablets() {
        return tablets;
    }

    public void setTablets(String tablets) {
        this.tablets = tablets;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }}










