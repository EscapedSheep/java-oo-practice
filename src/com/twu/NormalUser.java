package com.twu;

public class NormalUser extends User{
    private int numOfVotes;

    public NormalUser(String name) {
        super(name);
        this.numOfVotes = 10;
    }

    public int getNumOfVotes() {

        return numOfVotes;
    }

    public void setNumOfVotes(int numOfVotes) {
        this.numOfVotes = numOfVotes;
    }

    public void voted(int numb) {
        this.numOfVotes -= numb;
    }
}
